package za.lana.aluriantech.entity.transport;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.*;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import za.lana.aluriantech.sound.AlurianTechSounds;
import za.lana.aluriantech.util.ImplementedInventory;

import java.util.Iterator;

public class JeepEntity extends AnimalEntity implements Mount, ImplementedInventory {
    public final AnimationState idleAniState= new AnimationState();
    public final AnimationState drillAniState= new AnimationState();
    private int idleAniTimeout = 0;
    private int drillAniTimeout = 0;
    private float yawVelocity;
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(8, ItemStack.EMPTY);
    public JeepEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
    // ANIMATIONS
    private void setupAnimationStates() {
        // IDLE
        if (this.idleAniTimeout <= 0) {
            this.idleAniTimeout = this.random.nextInt(40) + 80;
            this.idleAniState.start(this.age);
        } else {
            --this.idleAniTimeout;
        }
    }

    protected void updateLimbs(float posDelta) {
        float f;
        if (this.getPose() == EntityPose.STANDING) {
            f = Math.min(posDelta * 6.0F, 1.0F);
        } else {
            f = 0.0F;
        }
        this.limbAnimator.updateLimbs(f, 0.2F);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.getWorld().isClient()) {
            setupAnimationStates();
        }
        if (getWorld() instanceof ServerWorld world && canBeOperatedByPlayer())
            for (ServerPlayerEntity player : PlayerLookup.tracking(world, getBlockPos())) {
                //System.out.println("DrillRig:Sending Player Packet");
                //ControlInputSyncS2CPacket.send(player, this);
                //Vec3SyncS2CPacket.send(player, this);
            }
    }
    //
    public static DefaultAttributeContainer setAttributes() {
        return PathAwareEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.31F)
                .add(EntityAttributes.GENERIC_ARMOR, 0.5F)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 128)
                .build();
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }
    // NBT
    @Override
    public void writeCustomDataToNbt(NbtCompound nbtData) {
        super.writeCustomDataToNbt(nbtData);
        Inventories.writeNbt(nbtData, inventory);
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        Inventories.readNbt(nbt, inventory);
    }
    // INVENTORY
    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    // VEHICLE
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getStackInHand(hand);
        if(hand == Hand.MAIN_HAND && !isBreedingItem(itemstack)) {
            if(!player.isSneaking() && this.getAttacker() == null) {
                startRiding(player);
            }
            return ActionResult.SUCCESS;
        }
        return super.interactMob(player, hand);
    }

    public boolean canBeOperatedByPlayer() {
        return getControllingPassenger() instanceof PlayerEntity;
    }
    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return (LivingEntity) this.getFirstPassenger();
    }

    private void startRiding(PlayerEntity player) {
        player.setYaw(this.getYaw());
        player.setPitch(this.getPitch());
        player.startRiding(this, false);
    }

    @Override
    public void travel(Vec3d movementInput) {
        if(this.hasPassengers() && getControllingPassenger() instanceof PlayerEntity) {
            //this.playSound(AlurianTechSounds.DRILLRIG_IDLE, 1.2f, 1.0f);
            LivingEntity livingentity = this.getControllingPassenger();
            this.setYaw(livingentity.getYaw());
            this.prevYaw = this.getYaw();
            this.setPitch(livingentity.getPitch() * 0.5F);
            this.setRotation(this.getYaw(), this.getPitch());
            this.bodyYaw = this.getYaw();
            this.headYaw = this.bodyYaw;
            float f = livingentity.sidewaysSpeed * 0.5F;
            float f1 = livingentity.forwardSpeed;
            if (f1 <= 0.0F) {
                f1 *= 0.25F;
            }
            if (this.isLogicalSideForUpdatingMovement()) {
                float newSpeed = (float) this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);
                if (MinecraftClient.getInstance().options.sprintKey.isPressed()) {
                    newSpeed *= 2; // Change this to ~1.5 or so
                }
                this.setMovementSpeed(newSpeed);
                //
                super.travel(new Vec3d(f, movementInput.y, f1));
            }

        } else {
            super.travel(movementInput);
        }
    }

    @Override
    public boolean collidesWith(Entity other) {
        return JeepEntity.canCollide(this, other);
    }
    @Override
    protected void onBlockCollision(BlockState state) {
        if(this.horizontalCollision) {
            scanAndBreakBlocks();
        }
    }
    // BREAKING WORKS
    private void scanAndBreakBlocks(){
        World level = this.getWorld();
        if (!level.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            return;
        }
        //
        if (this.horizontalCollision && level.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            boolean breakTheseBlocks = false;
            Box box = this.getBoundingBox().expand(0.2);
            Iterator scanPos = BlockPos.iterate(
                    MathHelper.floor(box.minX),
                    MathHelper.floor(box.minY),
                    MathHelper.floor(box.minZ),
                    MathHelper.floor(box.maxX),
                    MathHelper.floor(box.maxY),
                    MathHelper.floor(box.maxZ))
                    .iterator();

            label60:
            while(true) {
                BlockPos blockPos;
                Block block;
                BlockState blockState;
                do {
                    if (!scanPos.hasNext()) {
                        if (!breakTheseBlocks && this.isOnGround()) {
                            this.jump();
                            return;
                        }
                        break label60;
                    }
                    blockPos = (BlockPos) scanPos.next();
                    blockState = this.getWorld().getBlockState(blockPos);
                    block = blockState.getBlock();

                } while (!(block instanceof GrassBlock) && !blockState.isOf(Blocks.AIR));
                //
                breakTheseBlocks = level.breakBlock(blockPos, true, this) || breakTheseBlocks;
                level.syncWorldEvent(2001, blockPos, Block.getRawIdFromState(level.getBlockState(blockPos)));
            }
        }
    }

    public static boolean canCollide(Entity entity, Entity other) {
        if (other.isCollidable() || other.isPushable()){
            other.pushAwayFrom(entity);
            other.velocityModified = true;
            if ((other instanceof LivingEntity)) {
                other.damage(entity.getDamageSources().mobAttack((LivingEntity) entity), 6.0F);
            }
        }
        return (other.isCollidable() || other.isPushable()) && !entity.isConnectedThroughVehicle(other);
    }
    @Override
    public boolean isPushable() {
        return false;
    }
    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengerList().size() < this.getMaxPassengers() && !this.isSubmergedIn(FluidTags.WATER);
    }

    protected int getMaxPassengers() {
        return 2;
    }
    @Override
    protected void updatePassengerPosition(Entity passenger, Entity.PositionUpdater positionUpdater) {
        super.updatePassengerPosition(passenger, positionUpdater);

        if (passenger instanceof LivingEntity) {
            //Vec3d vec3d = this.getPassengerRidingPos(passenger);
            // [-0.6, 0.3, -0.2]
            ((LivingEntity)passenger).bodyYaw = this.bodyYaw;
            passenger.setYaw(passenger.getYaw());
            this.clampPassengerYaw(passenger);
            passenger.setHeadYaw(passenger.getHeadYaw());
            //
            this.prevPitch = passenger.prevPitch;
            positionUpdater.accept(passenger, getX() -0.6, getY() +0.3, getZ() -0.2);
        }
    }

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        Direction facing = this.getMovementDirection();
        if (facing.getAxis() == Direction.Axis.Y) {
            return super.updatePassengerForDismount(passenger);
        }
        int[][] is = Dismounting.getDismountOffsets(facing);
        BlockPos dismountPos = this.getBlockPos();
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for (EntityPose entityPose : passenger.getPoses()) {
            Box box = passenger.getBoundingBox(entityPose);
            for (int[] js : is) {
                mutable.set(dismountPos.getX() + js[0], dismountPos.getY(), dismountPos.getZ() + js[1]);
                double d = this.getWorld().getDismountHeight(mutable);
                if (!Dismounting.canDismountInBlock(d)) continue;
                Vec3d vec3d = Vec3d.ofCenter(mutable, d);
                if (!Dismounting.canPlaceEntityAt(this.getWorld(), passenger, box.offset(vec3d))) continue;
                passenger.setPose(entityPose);
                return vec3d;
            }
        }
        return super.updatePassengerForDismount(passenger);
    }

    protected void clampPassengerYaw(Entity passenger) {
        passenger.setBodyYaw(this.getYaw());
        float f = MathHelper.wrapDegrees(passenger.getYaw() - this.getYaw());
        float g = MathHelper.clamp(f, -105.0F, 105.0F);
        passenger.prevYaw += g - f;
        passenger.setYaw(passenger.getYaw() + g - f);
        passenger.setHeadYaw(passenger.getYaw());
    }
    public void onPassengerLookAround(Entity passenger) {
        this.clampPassengerYaw(passenger);
    }
    // SOUND
    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(this.getStepSound(), 0.15f, 1.1f + this.getVelocityMultiplier());
    }
    SoundEvent getStepSound() {
        return AlurianTechSounds.DRILLRIG_MOVE;
    }
}
