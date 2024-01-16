/**
 * Lana 2024
 */
package za.lana.aluriantech.client.networking;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import za.lana.aluriantech.entity.AlurianTechEntities;
import za.lana.aluriantech.entity.drones.CargoDroneEntity;

public class SpawnCargoDroneC2SPacket {
    public static CargoDroneEntity cargoDrone;
    public static void recieve(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        // ONLY SERVER SIDE:
        BlockPos location = player.getBlockPos();
        ServerWorld level = player.getServerWorld();
        BlockEntity blockEntity = level.getBlockEntity(location);
        BlockState state = level.getBlockState(location);
        //
        AlurianTechEntities.CARGODRONE.spawn(level, location, SpawnReason.TRIGGERED);
        //
        /**
        Box box = new Box(location.getX(), location.getY(), location.getZ(), location.getX() + 1, location.getY() + 1, location.getZ() + 1);
        List<Entity> scan = level.getEntitiesByClass(Entity.class, box, entity -> !(entity instanceof CargoDroneEntity));
        //scan.forEach(Entity::discard);
        //cargoDrone.setSyncedWithDroneBox(true);
         **/

        /**
        if (blockEntity instanceof DroneBoxBlockEntity) {
            level.setBlockState(location, state.cycle(LIT), 2);
        }
        DBxEntities.CARGODRONE.spawn(level, location, SpawnReason.TRIGGERED);
        if (mob instanceof CargoDroneEntity cargodrone) {
            cargodrone.setSyncedWithDroneBox(true);
        }
         **/
        System.out.println("Destination Packet Recieved");
        // PING BACK TO CLIENT ?
    }
}