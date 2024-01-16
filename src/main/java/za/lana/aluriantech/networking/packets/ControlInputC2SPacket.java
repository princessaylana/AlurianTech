package za.lana.aluriantech.networking.packets;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import za.lana.aluriantech.AlurianTech;
import za.lana.aluriantech.entity.machine.DrillRigEntity;

//KeyInputC2SPacket
public class ControlInputC2SPacket {
    public static final Identifier KEY_INPUT_PACKET = new Identifier(AlurianTech.MOD_ID, "key_input");
    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(KEY_INPUT_PACKET, (server, player, handler, buffer, sender) -> {
            boolean isDrillKeyPressed = buffer.readBoolean();
            LivingEntity entity = (LivingEntity) player.getServerWorld().getEntityById(buffer.readInt());
            if (entity instanceof DrillRigEntity drillRig) {
                drillRig.isDrillKeyPressed = isDrillKeyPressed;
                //System.out.println("DrillRig:KEY_C2S_INPUT_PACKET");
            }
        });
    }
}
