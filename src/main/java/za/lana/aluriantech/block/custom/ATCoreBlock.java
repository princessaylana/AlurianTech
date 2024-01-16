/**
 * Lana 2024
 */
package za.lana.aluriantech.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import za.lana.aluriantech.block.blockentity.DroneBoxBlockEntity;
import za.lana.aluriantech.entity.drones.CargoDroneEntity;

import java.util.List;

// TODO WORK IN PROGRESS
public class ATCoreBlock extends Block {

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 16, 16);
    public static final BooleanProperty LIT = Properties.LIT;
    public Entity other;
    public static BlockState state;
    public ATCoreBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(Properties.LIT, false));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    // SHAPE AND PLACEMENT
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }
    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }
    //
    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof DroneBoxBlockEntity) {
                ItemScatterer.spawn(world, pos, (DroneBoxBlockEntity)blockEntity);
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    // ADD BLOCKSTATES BELOW
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(LIT);
    }

    // INTERACT
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        if (!world.isClient() && hand == Hand.MAIN_HAND) {
            world.setBlockState(pos, state.cycle (LIT));
            //set so only server can set block states

        }
        return super.onUse(state, world, pos, player, hand, hit);
    }
    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20, 2));
            world.setBlockState(pos, state.with(LIT, false));
        }
        if (entity instanceof CargoDroneEntity cargoDrone) {
            cargoDrone.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 20, 2));
            world.setBlockState(pos, state.with(LIT, true));
        }
        super.onSteppedOn(world, pos, state, entity);
    }
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random){
        if (world.isDay()){
            if (random.nextInt(5) == 0) {
                world.setBlockState(pos, state.with(LIT, false));
            }
        }
    }


    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("block.aluriantech.dronebox.tooltip")
                    .fillStyle(Style.EMPTY.withColor(Formatting.AQUA).withBold(true)));
            tooltip.add(Text.translatable("block.aluriantech.dronebox.tooltip2")
                    .fillStyle(Style.EMPTY.withColor(Formatting.AQUA).withBold(true)));
        }else {
            tooltip.add(Text.translatable("key.dronebox.shift")
                    .fillStyle(Style.EMPTY.withColor(Formatting.GOLD)));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }


}

