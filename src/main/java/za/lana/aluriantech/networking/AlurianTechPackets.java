/**
 * Lana 2024
 */
package za.lana.aluriantech.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import za.lana.aluriantech.AlurianTech;
import za.lana.aluriantech.client.networking.BuildStructureC2SPacket;
import za.lana.aluriantech.client.networking.SpawnCargoDroneC2SPacket;

public class AlurianTechPackets {

    public static final Identifier SPAWN_CARGODRONE_CS_SYNCPACKET = new Identifier(AlurianTech.MOD_ID, "spawn_cargodrone_cs_syncpacket");
    public static final Identifier BUILD_STRUCTURE_CS_SYNCPACKET = new Identifier(AlurianTech.MOD_ID, "build_structure_cs_syncpacket");
    public static final Identifier MINING_KEY_PACKET = new Identifier(AlurianTech.MOD_ID, "mining_key_packet");

    // client to server
    public static void registerC2SPackets(){
        ServerPlayNetworking.registerGlobalReceiver(SPAWN_CARGODRONE_CS_SYNCPACKET, SpawnCargoDroneC2SPacket::recieve);
        ServerPlayNetworking.registerGlobalReceiver(BUILD_STRUCTURE_CS_SYNCPACKET, BuildStructureC2SPacket::recieve);
        ServerPlayNetworking.registerGlobalReceiver(MINING_KEY_PACKET, SpawnCargoDroneC2SPacket::recieve);
        //ServerPlayNetworking.registerGlobalReceiver(NBT_CS_SYNCPACKET, SpawnCargoDroneC2SPacket::recieve);

    }
    // server to client
    public static void registerS2CPackets() {

        //ClientPlayNetworking.registerGlobalReceiver(NBT_SC_SYNCPACKET, NBTSyncS2CPacket::recieve);
        //ClientPlayNetworking.registerGlobalReceiver(ITEM_SYNC, ItemStackSyncS2CPacket::receive);
    }

}
