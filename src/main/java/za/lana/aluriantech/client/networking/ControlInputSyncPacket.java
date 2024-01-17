package za.lana.aluriantech.client.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import za.lana.aluriantech.entity.machine.DrillRigEntity;
import za.lana.aluriantech.networking.packets.ControlInputSyncS2CPacket;

public class ControlInputSyncPacket {
    public static void recieve() {
        ClientPlayNetworking.registerGlobalReceiver(ControlInputSyncS2CPacket.AT_KEY_INPUT_SYNC_PACKET, (client, handler, buffer, sender) -> {
            boolean isDrillKeyPressed = buffer.readBoolean();
            DrillRigEntity drillRig = client.world != null ? (DrillRigEntity) client.world.getEntityById(buffer.readInt()) : null;
            client.execute(() ->{
                if (drillRig != null && drillRig.getControllingPassenger() != client.player) {
                    drillRig.isDrillKeyPressed = isDrillKeyPressed;
                }
            });
        });
    }
}
