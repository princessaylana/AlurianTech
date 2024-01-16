package za.lana.aluriantech.client.networking;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;
import za.lana.aluriantech.entity.machine.DrillRigEntity;
import za.lana.aluriantech.networking.packets.ControlInputC2SPacket;

// KeyInputPacket
@Environment(EnvType.CLIENT)
public class ControlInputPacket {
    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client ->{
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            if (player != null)
                if (player.getVehicle() instanceof DrillRigEntity drillRig) {
                    PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
                    buf.writeBoolean(drillRig.isDrillKeyPressed);
                    buf.writeInt(drillRig.getId());
                    ClientPlayNetworking.send(ControlInputC2SPacket.KEY_INPUT_PACKET, buf);
                    //System.out.println("DrillRig:KEY_INPUT_PACKET");
                }
        });
    }
}
