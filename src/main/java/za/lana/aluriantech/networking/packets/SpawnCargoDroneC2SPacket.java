/**
 * Lana 2024
 */
package za.lana.aluriantech.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import za.lana.aluriantech.entity.AlurianTechEntities;

public class SpawnCargoDroneC2SPacket {

    public static void recieve(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        // ONLY SERVER SIDE:
        BlockPos location = player.getBlockPos();
        ServerWorld level = player.getServerWorld();
        BlockEntity blockEntity = level.getBlockEntity(location);
        BlockState state = level.getBlockState(location);
        //
        AlurianTechEntities.CARGODRONE.spawn(level, location, SpawnReason.TRIGGERED);
        //
        System.out.println("Destination Packet Recieved");
        // PING BACK TO CLIENT ?
    }
}