package za.lana.aluriantech.client.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.math.Vec3d;
import za.lana.aluriantech.entity.machine.DrillRigEntity;
import za.lana.aluriantech.networking.packets.Vec3SyncS2CPacket;

public class Vec3SyncPacket {
    public static void recieve() {
        ClientPlayNetworking.registerGlobalReceiver(Vec3SyncS2CPacket.POS_SYNC_PACKET, (client, handler, buffer, sender) -> {
            Vec3d pos = new Vec3d(buffer.readVector3f());
            assert client.world != null;
            DrillRigEntity drillRig = (DrillRigEntity) client.world.getEntityById(buffer.readInt());
            client.execute(() ->{
                if (drillRig != null && drillRig.getControllingPassenger() != client.player) drillRig.setPosition(pos);
            });
        });
    }
}
