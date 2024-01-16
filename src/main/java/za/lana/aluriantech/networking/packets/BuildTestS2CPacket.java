/**
 * Lana 2024
 */
package za.lana.aluriantech.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import za.lana.aluriantech.block.blockentity.DroneBoxBlockEntity;

// SERVER TO CLIENT
public class BuildTestS2CPacket {
    public static void recieve(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        BlockPos position = buf.readBlockPos();
        assert client.world != null;
        if(client.world.getBlockEntity(position) instanceof DroneBoxBlockEntity blockEntity) {
            System.out.println("Destination Packet Saved");
        }
    }
}
