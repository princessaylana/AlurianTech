package za.lana.aluriantech.entity.machine;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.*;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import za.lana.aluriantech.AlurianTech;
import za.lana.aluriantech.client.input.KeyInputHandler;
import za.lana.aluriantech.client.networking.ControlInputSyncPacket;
import za.lana.aluriantech.client.networking.Vec3SyncPacket;
import za.lana.aluriantech.networking.packets.ControlInputSyncS2CPacket;
import za.lana.aluriantech.networking.packets.Vec3SyncS2CPacket;
import za.lana.aluriantech.sound.AlurianTechSounds;
import za.lana.aluriantech.util.ImplementedInventory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static net.minecraft.util.math.Direction.getFacing;

//
// TODO ...
// COOLDOWN NEEDED
// NEEDS TO RENDER THE BLOCK BREAKING
// BREAK BLOCKS
// NEEDS TO COLLECT ITEMS
// NEEDS TO HAVE AUTOMINE MODE, MOVE ONE BLOCK FORWARD AND REPEAT

public class DrillRigEntity extends AnimalEntity implements Mount, ImplementedInventory {
    public final AnimationState idleAniState= new AnimationState();
    public final AnimationState drillAniState= new AnimationState();
    private int idleAniTimeout = 0;
    private int drillAniTimeout = 0;
    //private int drillTick = 0;
    private int drillCooldown = 0;
    
    private final Direction facing;
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(8, ItemStack.EMPTY);
    private static final TrackedData<Boolean> CONSUMING_FUEL = DataTracker.registerData(DrillRigEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
   // private static final TrackedData<Boolean> DRILLING = DataTracker.registerData(DrillRigEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private boolean isFlyDownPressed;
    public boolean isDrillKeyPressed = false;

    // MAIN CONSTRUCTOR
    public DrillRigEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 0;
        this.setCanPickUpLoot(true);
        this.facing = this.getHorizontalFacing();
    }
    //  ATTRIBUTES
    public static DefaultAttributeContainer setAttributes() {
        return PathAwareEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25F)
                .add(EntityAttributes.GENERIC_ARMOR, 0.5F)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 128)
                .build();
    }
    // ANIMATIONS
    private void setupAnimationStates() {
        // IDLE
        if (this.idleAniTimeout <= 0 && this.isConsumingFuel()) {
            this.idleAniTimeout = this.random.nextInt(40) + 80;
            this.idleAniState.start(this.age);
        } else {
            --this.idleAniTimeout;
        }
        // DRILLING ANIMATION
        if(isDrillKeyPressed && drillAniTimeout <= 0) {
            drillAniTimeout = 40;
            drillAniState.start(this.age);
        } else {
            --this.drillAniTimeout;
        }
        if(!this.isDrillKeyPressed) {
            drillAniState.stop();
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
        World level = this.getWorld();
        if(level.isClient()) {
            setupAnimationStates();
         }
        if (getWorld().isClient() && getControllingPassenger() == MinecraftClient.getInstance().player) {
            isDrillKeyPressed = KeyInputHandler.drillKey.isPressed();
        }
        if (getWorld() instanceof ServerWorld world && canBeOperatedByPlayer())
            for (ServerPlayerEntity player : PlayerLookup.tracking(world, getBlockPos())) {
                ControlInputSyncS2CPacket.send(player, this);
                Vec3SyncS2CPacket.send(player, this);
            }
        boolean isDrillKeyPressed = this.isDrillKeyPressed;
        if (isDrillKeyPressed){
            scanAndBreakBlocks();
        }
    }
    // DATA
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(CONSUMING_FUEL, false);
    }
    // DATA - BOOLEANS
    public void setConsumingFuel(boolean consumingFuel) {
        this.dataTracker.set(CONSUMING_FUEL, consumingFuel);
    }
    public boolean isConsumingFuel() {
        return this.dataTracker.get(CONSUMING_FUEL);
    }

    // NBT
    @Override
    public void writeCustomDataToNbt(NbtCompound nbtData) {
        super.writeCustomDataToNbt(nbtData);
        Inventories.writeNbt(nbtData, inventory);
        nbtData.putInt("drillCooldown", this.drillCooldown);
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        this.drillCooldown = nbt.getInt("drillCooldown");
    }
    // VEHICLE
    public Direction getFacing() {
        return this.facing;
    }
    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getStackInHand(hand);
        if(hand == Hand.MAIN_HAND && !isBreedingItem(itemstack)) {
            if(!player.isSneaking() && this.getAttacker() == null) {
                setRiding(player);
            }
            return ActionResult.SUCCESS;
        }
        return super.interactMob(player, hand);
    }
    public boolean collidesWith(Entity other) {
        return DrillRigEntity.canCollide(this, other);
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
    public boolean isPushable() {
        return false;
    }

    // VEHICLE TRAVEL
    public boolean canBeOperatedByPlayer() {
        return getControllingPassenger() instanceof PlayerEntity;
    }
    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return (LivingEntity) this.getFirstPassenger();
    }
    private void setRiding(PlayerEntity passenger) {
        passenger.setYaw(this.getYaw());
        passenger.setPitch(this.getPitch());
        passenger.startRiding(this);
    }
    @Override
    public void travel(Vec3d movementInput) {
        World level = this.getWorld();
        if(this.hasPassengers() && getControllingPassenger() instanceof PlayerEntity) {
            this.playSound(AlurianTechSounds.DRILLRIG_IDLE, 1.2f, 1.0f);
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
                super.travel(new Vec3d(f, movementInput.y, f1));
            }

        } else {
            super.travel(movementInput);
        }
    }
    @Override
    protected void updatePassengerPosition(Entity passenger, PositionUpdater positionUpdater) {
        super.updatePassengerPosition(passenger, positionUpdater);
        passenger.setYaw(passenger.getYaw());
        passenger.setHeadYaw(passenger.getHeadYaw());
        //
        this.prevPitch = passenger.prevPitch;
        positionUpdater.accept(passenger, getX(), getY() + 0.5f, getZ());
    }
    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        Direction direction = this.getMovementDirection();
        if (direction.getAxis() == Direction.Axis.Y) {
            return super.updatePassengerForDismount(passenger);
        }
        int[][] is = Dismounting.getDismountOffsets(direction);
        BlockPos blockPos = this.getBlockPos();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (EntityPose entityPose : passenger.getPoses()) {
            Box box = passenger.getBoundingBox(entityPose);
            for (int[] js : is) {
                mutable.set(blockPos.getX() + js[0], blockPos.getY(), blockPos.getZ() + js[1]);
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
    // SOUND
    @Override
    protected SoundEvent getAmbientSound() {
        //return AlurianTechSounds.DRILLRIG_IDLE;
        return null;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(this.getStepSound(), 1.0f, 1.0f + (this.getVelocityMultiplier() /2.0f));
    }
    SoundEvent getStepSound() {
        return AlurianTechSounds.DRILLRIG_MOVE;
    }
    //
    //TODO INVENTORY && SCREENS
    @Override
    public DefaultedList<ItemStack> getItems() {
        return null;
    }
    // TODO FUELSYSTEM

    // TODO WIP MINING
    private void setYaw() {

        float yaw = switch (this.getHorizontalFacing()) {
            case NORTH -> 180;
            case EAST -> 270;
            case SOUTH -> 0;
            case WEST -> 90;
            default -> 0;
        };
        setRotation(yaw, getPitch());
    }

    public void scanAndBreakBlocks(){
        // onhorizontalcollision
        World level = this.getWorld();
        if (!level.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            return;
        }
        if (!level.isClient) {
            if (level.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                if (this.isAlive()) {
                    boolean breakTheseBlocks = false;
                    // double maxDistance, float tickDelta, boolean includeFluids
                    Vec3d rayCast = this.raycast(8, 10, false).getPos();
                    Box box = new Box(rayCast, new Vec3d(rayCast.getX(), rayCast.getY() - 1, rayCast.getZ())).expand(1.0, 0.75, 0.0);
                    //Box box = this.getBoundingBox().expand(0.2).offset(0.0,0.0, zZ);
                    Iterator<BlockPos> scanPos = BlockPos.iterate(
                                    MathHelper.floor(box.minX),
                                    MathHelper.floor(box.minY),
                                    MathHelper.floor(box.minZ),
                                    MathHelper.floor(box.maxX),
                                    MathHelper.floor(box.maxY),
                                    MathHelper.floor(box.maxZ))
                            .iterator();
                    //
                    label60:
                    while (true) {
                        BlockPos blockPos;
                        Block block;
                        BlockState blockState;
                        //
                        do {
                            if (!scanPos.hasNext()) {
                                if (!breakTheseBlocks && this.isOnGround()) {
                                    this.jump();
                                    return;
                                }
                                break label60;
                            }
                            blockPos = scanPos.next();
                            blockState = this.getWorld().getBlockState(blockPos);
                            block = blockState.getBlock();

                        } //while (!(blockState.isOf(Blocks.GRASS_BLOCK)));
                        while (!(blockState.isOf(Blocks.GRASS_BLOCK))
                                && !(blockState.isOf(Blocks.DIRT))
                                && !(blockState.isOf(Blocks.STONE))
                                && !(blockState.isIn(BlockTags.PICKAXE_MINEABLE))
                                && !(blockState.isIn(BlockTags.SHOVEL_MINEABLE))
                                && !(blockState.isIn(BlockTags.NEEDS_STONE_TOOL))
                                && !(blockState.isIn(BlockTags.NEEDS_IRON_TOOL))
                                && !(blockState.isIn(BlockTags.NEEDS_DIAMOND_TOOL))
                        );
                        // bl = this.getWorld().breakBlock(blockPos, true, this) || bl;
                        //breakTheseBlocks = level.breakBlock(blockPos, true, this, 64) || breakTheseBlocks;
                        breakTheseBlocks = level.breakBlock(blockPos, true, this) || breakTheseBlocks;
                        level.syncWorldEvent(2001, blockPos, Block.getRawIdFromState(level.getBlockState(blockPos)));

                        //breakTheseBlocks = getWorld().setBlockState(blockPos, Blocks.AIR.getDefaultState()) || breakTheseBlocks;
                        //getWorld().setBlockState(blockPos, Blocks.AIR.getDefaultState());
                        //Block.dropStacks(blockState, getWorld(), blockPos, null, this, ItemStack.EMPTY);
                        //level.setBlockState(blockPos, Blocks.AIR.getDefaultState());
                        // needs drops
                    }
                }
            }
        }
        PlayerEntity player = (PlayerEntity) this.getControllingPassenger();
        if (level.isClient) {
            player.sendMessage(Text.literal("Success").fillStyle(
                    Style.EMPTY.withColor(Formatting.GREEN)), true);
        }
        playDrillEffects();
    }
    public void playDrillEffects() {
        if (this.getWorld().isClient) {
            World level = this.getWorld();
            for (int i = 0; i < 2; ++i) {
                level.addParticle(
                        ParticleTypes.ASH,
                        this.getBoundingBox().getLengthX(),
                        this.getBoundingBox().getLengthY(),
                        this.getBoundingBox().getLengthZ() -8,
                        (this.random.nextDouble() - 0.5) * 2.0, -this.random.nextDouble(),
                        (this.random.nextDouble() - 0.5) * 2.0);
            }
        }
    }
}
