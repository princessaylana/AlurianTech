package za.lana.aluriantech.networking.packets;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import za.lana.aluriantech.AlurianTech;
import za.lana.aluriantech.entity.machine.DrillRigEntity;

public class Vec3SyncS2CPacket {
    public static final Identifier POS_SYNC_PACKET = new Identifier(AlurianTech.MOD_ID, "pos_sync_packet");

    public static void send(ServerPlayerEntity player, DrillRigEntity drillRig) {
        if (drillRig.canBeOperatedByPlayer()) {
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeVector3f(drillRig.getPos().toVector3f());
            buf.writeInt(drillRig.getId());
            ServerPlayNetworking.send(player, POS_SYNC_PACKET, buf);
        }
    }
}
