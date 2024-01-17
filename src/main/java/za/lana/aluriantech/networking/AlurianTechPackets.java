/**
 * Lana 2024
 */
package za.lana.aluriantech.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import za.lana.aluriantech.AlurianTech;
import za.lana.aluriantech.networking.packets.BuildStructureC2SPacket;
import za.lana.aluriantech.networking.packets.SpawnCargoDroneC2SPacket;

public class AlurianTechPackets {

    public static final Identifier SPAWN_CARGODRONE_CS_SYNCPACKET = new Identifier(AlurianTech.MOD_ID, "spawn_cargodrone_cs_syncpacket");
    public static final Identifier BUILD_STRUCTURE_CS_SYNCPACKET = new Identifier(AlurianTech.MOD_ID, "build_structure_cs_syncpacket");

    // server recieve from client
    public static void registerC2SPackets(){
        ServerPlayNetworking.registerGlobalReceiver(BUILD_STRUCTURE_CS_SYNCPACKET, BuildStructureC2SPacket::recieve);
        ServerPlayNetworking.registerGlobalReceiver(SPAWN_CARGODRONE_CS_SYNCPACKET, SpawnCargoDroneC2SPacket::recieve);

    }
    // client recieve from server
    public static void registerS2CPackets() {

    }

}
