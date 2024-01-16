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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import za.lana.aluriantech.block.AlurianTechBlocks;
import za.lana.aluriantech.block.blockentity.DroneBoxBlockEntity;

import java.util.List;
import java.util.Objects;

// TODO WORK IN PROGRESS
public class DroneBoxBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final String NON_BREAKABLE = "message.aluriantech.dronebox_block.break";
    public static final String ACCESS_GRANTED = "message.aluriantech.dronebox_block.granted";
    public static final String ACCESS_DENIED = "message.aluriantech.dronebox_block.denied";
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 16, 16);
    //public static final BooleanProperty SYNCED = new BooleanProperty(String.valueOf(CargoDroneEntity.SYNCED));

    public static final BooleanProperty LIT = Properties.LIT;

    public Entity other;
    public static BlockState state;
    public DroneBoxBlock(Settings settings) {
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
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity owner, ItemStack itemStack) {
        PlayerEntity player = (PlayerEntity)owner;
        ((DroneBoxBlockEntity) Objects.requireNonNull(world.getBlockEntity(pos))).setOwnerUUID(player.getUuidAsString());
        ((DroneBoxBlockEntity) Objects.requireNonNull(world.getBlockEntity(pos))).setOwnerName(player.getDisplayName().getString());
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

    // BLOCK ENTITY
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        DroneBoxBlockEntity vault = ((DroneBoxBlockEntity) world.getBlockEntity(pos));
        assert vault != null;
        boolean isOwner = vault.getOwnerUUID().equals(player.getUuidAsString());
        if (player.isSneaking() && isOwner) {
            if (!player.isCreative())
                dropStack(world, pos, new ItemStack(AlurianTechBlocks.DRONEBOX_BLOCK));
            world.breakBlock(pos, true);
        }
        else if (player.isSneaking() && !isOwner) {
            if (world.isClient) {
                player.sendMessage(Text.translatable(NON_BREAKABLE).fillStyle(
                        Style.EMPTY.withColor(Formatting.RED)), true);
            }
        } else if (isOwner) {
            if (!world.isClient()) {
                NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
                if (screenHandlerFactory != null) {player.openHandledScreen(screenHandlerFactory);
                    //TODO CHANGE THE BLOCKSTATE FOR TESTING
                    player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
                    world.setBlockState(pos, state.cycle (LIT));
                    return ActionResult.SUCCESS;
                }
                return ActionResult.SUCCESS;
            }
            if (world.isClient) {
                player.sendMessage(Text.translatable(ACCESS_GRANTED).fillStyle(
                        Style.EMPTY.withColor(Formatting.GREEN)), true);
            }
        } else {
            if (world.isClient) {
                player.sendMessage(Text.translatable(ACCESS_DENIED + DroneBoxBlockEntity.getOwner).fillStyle(
                        Style.EMPTY.withColor(Formatting.RED)), true);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DroneBoxBlockEntity(pos, state);
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("block.aluriantech.dronebox.tooltip")
                    .fillStyle(Style.EMPTY.withColor(Formatting.GREEN).withBold(true)));
            tooltip.add(Text.translatable("block.aluriantech.dronebox.tooltip2")
                    .fillStyle(Style.EMPTY.withColor(Formatting.GREEN).withBold(true)));
        }else {
            tooltip.add(Text.translatable("key.dronebox.shift")
                    .fillStyle(Style.EMPTY.withColor(Formatting.GOLD)));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    public static void switchSync(World level, BlockPos pos){
        boolean synced = state.get(LIT);
        level.setBlockState(pos, state.cycle (LIT),2);
        System.out.println("dronebox:DroneBox:Connecting ...");
    }

}

