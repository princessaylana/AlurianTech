/**
 * Lana 2024
 */
package za.lana.aluriantech.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import za.lana.aluriantech.AlurianTech;
import za.lana.aluriantech.entity.drones.CargoDroneEntity;
import za.lana.aluriantech.util.StructurePlacerAPI;

public class BuildStructureC2SPacket {
    private static ItemUsageContext context;
    public static void recieve(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        // ONLY SERVER SIDE:
        BlockPos location = player.getBlockPos();
        ServerWorld level = player.getServerWorld();
        BlockState state = level.getBlockState(location);
        //BlockPos positionClicked = context.getBlockPos();
        //BlockEntity blockEntity = level.getBlockEntity(location);

        // Lets Test CustomStructureAPI
        // Just to make sure
        if (!level.isClient) {
            StructurePlacerAPI placer = new StructurePlacerAPI((ServerWorld) player.getWorld(),
                    new Identifier(AlurianTech.MOD_ID, "core_structure_00"),
                    player.getBlockPos(),
                    BlockMirror.NONE,
                    BlockRotation.CLOCKWISE_90,
                    true, 1.0f,
                    new BlockPos(location.getX(), location.getY(), location.getZ()));

            placer.loadStructure();
            placer.place(new StructureTemplate());
            placer.unloadStructure();
        }
        System.out.println("BuildItem:Building Success!");
    }
}