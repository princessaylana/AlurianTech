package za.lana.aluriantech.client.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import za.lana.aluriantech.entity.machine.DrillRigEntity;
import za.lana.aluriantech.networking.packets.ControlInputSyncS2CPacket;

//KeyInputSyncPacket
public class ControlInputSyncPacket {
    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(ControlInputSyncS2CPacket.KEY_INPUT_SYNC_PACKET, (client, handler, buffer, sender) -> {
            boolean isDrillKeyPressed = buffer.readBoolean();
            DrillRigEntity drillRig = client.world != null ? (DrillRigEntity) client.world.getEntityById(buffer.readInt()) : null;
            client.execute(() ->{
                if (drillRig != null && drillRig.getControllingPassenger() != client.player) {
                    drillRig.isDrillKeyPressed = isDrillKeyPressed;
                    //System.out.println("DrillRig:KEY_INPUT_SYNC_PACKET");
                }
            });
        });
    }
}
