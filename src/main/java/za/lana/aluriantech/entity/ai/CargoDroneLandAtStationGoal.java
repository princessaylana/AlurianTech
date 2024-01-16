/**
 * Lana 2024
 */
package za.lana.aluriantech.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import za.lana.aluriantech.block.AlurianTechBlocks;
import za.lana.aluriantech.entity.drones.CargoDroneEntity;

import java.util.EnumSet;

import static za.lana.aluriantech.entity.drones.CargoDroneEntity.UNIVERSAL_DRONE_RANGE;

public class CargoDroneLandAtStationGoal
        extends MoveToTargetPosGoal {
    private final BlockState BLOCK_TO_PLACE = AlurianTechBlocks.ATCORE_BLOCK.getDefaultState();
    private final CargoDroneEntity mob;

    public CargoDroneLandAtStationGoal(MobEntity mob, double speed, int range) {
        super((PathAwareEntity) mob, speed, range, UNIVERSAL_DRONE_RANGE);
        this.mob = (CargoDroneEntity) mob;
        this.lowestY = -1;
        this.setControls(EnumSet.of(Control.TARGET, Control.MOVE));
    }

    @Override
    public boolean canStart() {
        World level = this.mob.getWorld();
        if (!level.isDay() && !this.mob.isInLandingPose() && super.canStart()) {
            System.out.println("CDrone:Starting Landing Mode");
            //this.mob.playSound(ModSounds.CARGODRONE_FLYING_TO_LANDING, 1.0f,1.0f);
            return true;
        }
        //System.out.println("CDrone:Flying Mode");
        return false;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    protected int getInterval(PathAwareEntity mob) {
        int udr = UNIVERSAL_DRONE_RANGE;
        return udr / 2; // 128/2=64
        //return 40;
    }

    @Override
    public void stop() {
        World level = this.mob.getWorld();
        if (level.isDay() && this.mob.isInLandingPose()) {
            System.out.println("CDrone:Landing Mode Off");
            this.mob.setInLandingPose(false);
            this.mob.setInPlacingMode(false);
            this.mob.velocityDirty = false;
            this.mob.setNoGravity(true);
            System.out.println("CDrone:Landing Mode Stopped - Success");
            //this.mob.playSound(ModSounds.CARGODRONE_LANDED, 1.0f,1.0f);
            super.stop();
        }
    }

    @Override
    public void tick() {
        super.tick();
        World level = this.mob.getWorld();
        if (!level.isDay()) {
            if (!this.hasReached()) {
                //System.out.println("CDrone:Flying to Pos");
                //
            } else if (!this.mob.isInLandingPose() && !this.mob.isInPlacingMode() && level.isNight()) {
                this.mob.getNavigation().startMovingTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1.2f);
                this.tryToLand(targetPos);
                //
            } else if (!this.mob.isInLandingPose() && this.mob.isInPlacingMode()) {
                this.mob.getNavigation().startMovingTo(targetPos.getX(), targetPos.getY() + 2, targetPos.getZ() + 1, 1.2f);
                this.placeBlock();
                //
            } else if (this.mob.isInLandingPose() && !this.mob.isInPlacingMode()) {

                /**
                this.mob.setVelocity(Vec3d.ZERO);
                this.mob.velocityDirty = true;
                this.mob.setNoGravity(false);
                 **/
                //
                //System.out.println("CDrone: Landing Success");
            }
        }
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        World level = this.mob.getWorld();
        if (!world.isAir(pos.up())) {
            return false;
        }
        if (level.isDay()) {
            return false;
        }
        BlockState blockState = world.getBlockState(pos);
        if (blockState.isOf(AlurianTechBlocks.ATCORE_BLOCK)) {
            //System.out.println("CDrone: Found Station");
            //System.out.println("CDrone:Landing Mode On");
            this.mob.setInPlacingMode(false);
            return true;
        }
        //TODO DELAY TIMER NEEDED
        //System.out.println("CDrone:Placing Mode On");
        return scanForLandBlocks(world, pos);
    }

    protected boolean scanForLandBlocks(WorldView world, BlockPos pos) {
        World level = this.mob.getWorld();
        if (!world.isAir(pos.up())) {
            return false;
        }
        if (level.isDay()) {
            return false;
        }
        // LIST OF NON PLACEABLES
        BlockState blockState = world.getBlockState(pos);
        if (!blockState.isOf(Blocks.AIR)
                && !blockState.isOf(Blocks.LAVA)
                && !blockState.isOf(Blocks.WATER)
                && !blockState.isOf(Blocks.FIRE)
                && !blockState.isOf(Blocks.POWDER_SNOW)
                && !blockState.isOf(Blocks.TORCH)
                && !blockState.isOf(Blocks.CHEST)
                && !blockState.isOf(Blocks.LADDER)) {
            this.mob.setInPlacingMode(true);
            return true;
        }
        return false;
    }

    //
    public void tryToLand(BlockPos pos) {
        World level = this.mob.getWorld();
        boolean aboveSurface = !level.isAir(pos.down());
        if (aboveSurface) {
            //System.out.println("CDrone:Trying to Land");
            this.mob.onLanding();
            if (this.mob.isOnGround() && level.isNight()) {
                this.mob.fallDistance *= 0.0f;
                this.mob.setInLandingPose(true);
                System.out.println("CDrone:Grounded");
                //
                this.mob.setVelocity(Vec3d.ZERO);
                this.mob.velocityDirty = true;
                this.mob.setNoGravity(false);
                // LANDED
                //this.mob.playSound(ModSounds.CARGODRONE_LANDED, 1.0f,1.0f);
            } else if (this.mob.isOnGround() && level.isDay()) {
                this.mob.setInLandingPose(false);
                System.out.println("CDrone: Moving ...");
            }
        }
    }

    public void placeBlock() {
        if (this.mob.isInPlacingMode()) {
            //System.out.println("CDrone:Block Placed");
            World world = this.mob.getWorld();
            //
            BlockPos placeTargetPos = new BlockPos(targetPos.getX(), targetPos.getY() + 1, targetPos.getZ() + 1);
            BlockState targetPosBlockState = world.getBlockState(placeTargetPos);
            BlockPos placeTargetPosBelow = placeTargetPos.down();
            BlockState blockState2 = world.getBlockState(placeTargetPosBelow);
            // add custom block to place
            BlockState blockToPlace = BLOCK_TO_PLACE;
            if (blockToPlace != null) {
                blockToPlace = Block.postProcessState(blockToPlace, this.mob.getWorld(), placeTargetPos);
                if (this.canPlaceOn(world, placeTargetPos, blockToPlace, targetPosBlockState, blockState2, placeTargetPosBelow)) {
                    world.setBlockState(placeTargetPos, blockToPlace, 3);
                    world.emitGameEvent(GameEvent.BLOCK_PLACE, placeTargetPos, GameEvent.Emitter.of(this.mob, blockToPlace));
                    this.spawnPlaceParticles();
                    System.out.println("CDrone:Block Placed Successfully");
                    //
                    //this.resetScanTime();
                    this.mob.setInPlacingMode(false);
                    System.out.println("CDrone:Placing Mode Off");
                }
            }
        }
    }

    private boolean canPlaceOn(World world, BlockPos posAbove, BlockState placingState, BlockState stateAbove, BlockState state, BlockPos pos) {
        //System.out.println("CDrone:Checking state again");
        if (this.mob.isInPlacingMode()){
            return stateAbove.isAir() && !state.isAir() && !state.isOf(Blocks.BEDROCK) &&
                    state.isFullCube(world, pos)
                    && placingState.canPlaceAt(world, posAbove)
                    && world.getOtherEntities(this.mob, Box.from(Vec3d.of(posAbove))).isEmpty();
        }
        return false;
    }

    public void spawnPlaceParticles() {
        World level = this.mob.getWorld();
        if (level.isClient) {
            for (int j = 0; j < 2; ++j) {

                level.addParticle(ParticleTypes.CLOUD,
                        targetPos.getX(), targetPos.getY() - 0.50, targetPos.getZ(),
                        (this.mob.getRandom().nextDouble() - 0.5) * 2.0, -this.mob.getRandom().nextDouble(), (this.mob.getRandom().nextDouble() - 0.5) * 2.0);
            }
        }
    }
}