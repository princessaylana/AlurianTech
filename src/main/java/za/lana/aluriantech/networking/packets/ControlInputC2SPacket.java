package za.lana.aluriantech.networking.packets;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import za.lana.aluriantech.AlurianTech;
import za.lana.aluriantech.entity.machine.DrillRigEntity;

import static za.lana.aluriantech.AlurianTech.MOD_ID;


public class ControlInputC2SPacket {
    public static final Identifier AT_KEY_INPUT_PACKET = new Identifier(AlurianTech.MOD_ID, "at_key_input");
    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(AT_KEY_INPUT_PACKET, (server, player, handler, buffer, sender) -> {
            boolean isDrillKeyPressed = buffer.readBoolean();

            LivingEntity entity = (LivingEntity) player.getServerWorld().getEntityById(buffer.readInt());
            if (entity instanceof DrillRigEntity drillRig) {
                drillRig.isDrillKeyPressed = isDrillKeyPressed;
            }
        });
    }
}
