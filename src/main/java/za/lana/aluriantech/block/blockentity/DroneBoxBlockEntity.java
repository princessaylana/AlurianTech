/**
 * Lana 2024
 */
package za.lana.aluriantech.block.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import za.lana.aluriantech.block.AlurianTechBlockEntities;
import za.lana.aluriantech.client.screen.gui.AlurianTechScreens;
import za.lana.aluriantech.client.screen.gui.DroneBoxDescription;
import za.lana.aluriantech.util.ImplementedInventory;

public class DroneBoxBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {

    public static String getOwner = getOwnerName();
    private final DefaultedList<ItemStack> inventory;
    private String ownerUUID;
    private static String ownerName;
    public DroneBoxBlockEntity(BlockPos pos, BlockState state) {
        super(AlurianTechBlockEntities.DRONEBOX_BLOCK_ENTITY, pos, state);
        this.inventory = DefaultedList.ofSize(8, ItemStack.EMPTY);
        //syncLocationData();
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.aluriantech.dronebox.screen" + getOwnerName());
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
        return new DroneBoxDescription(AlurianTechScreens.DRONEBOX_GUI, syncId, inventory, ScreenHandlerContext.create(world, pos));
    }

    // OWNERSHIP
    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, this.inventory);
        setOwnerUUID(nbt.getString("ownerUUID"));
        setOwnerName(nbt.getString("ownerName"));
        super.readNbt(nbt);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt,this.inventory);
        nbt.putString("ownerUUID", getOwnerUUID());
        nbt.putString("ownerName", getOwnerName());
        super.writeNbt(nbt);
    }
    public String getOwnerUUID() {
        return ownerUUID;
    }
    public void setOwnerUUID(String ownerUUID) {
        this.ownerUUID = ownerUUID;
    }
    public static String getOwnerName() {
        return ownerName;
    }
    public void setOwnerName(String ownerName) {

        DroneBoxBlockEntity.ownerName = ownerName;
    }
    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {

        return BlockEntityUpdateS2CPacket.create(this);
    }
    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    //
    /**
    public BlockPos getDroneBoxLocationFromCargoDrone(){
        int droneBoxX = CargoDroneEntity.droneBoxX;
        int droneBoxY = CargoDroneEntity.droneBoxY;
        int droneBoxZ = CargoDroneEntity.droneBoxZ;
        return new BlockPos(droneBoxX, droneBoxY, droneBoxZ);
    }
    public BlockPos getDroneBoxRealLocation(){
        return getPos();
    }
    public void syncLocationData(){
        World level = this.getWorld();
        assert level != null;
        BlockState state = this.getCachedState();
        boolean synced = state.get(LIT);

        if (getDroneBoxLocationFromCargoDrone().equals(getDroneBoxRealLocation())) {
            //
            level.setBlockState(pos, state.cycle (LIT), 2);
            System.out.println("dbx:DroneBox:Synced");
        }
        else {
            System.out.println("dbx:DroneBox:UnSynced");
            level.setBlockState(pos, state.cycle (LIT), 2);
        }
    }
     **/


}
