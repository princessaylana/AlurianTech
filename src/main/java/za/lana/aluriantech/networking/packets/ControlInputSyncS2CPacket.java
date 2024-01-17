package za.lana.aluriantech.networking.packets;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import za.lana.aluriantech.AlurianTech;
import za.lana.aluriantech.entity.machine.DrillRigEntity;


public class ControlInputSyncS2CPacket {
    public static final Identifier AT_KEY_INPUT_SYNC_PACKET = new Identifier(AlurianTech.MOD_ID, "at_key_input_sync_packet");
    public static void send(ServerPlayerEntity player, DrillRigEntity drillRig) {
        if (drillRig.hasControllingPassenger()) {
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeBoolean(drillRig.isDrillKeyPressed);
            buf.writeInt(drillRig.getId());
            ServerPlayNetworking.send(player, AT_KEY_INPUT_SYNC_PACKET, buf);

        }
    }
}
