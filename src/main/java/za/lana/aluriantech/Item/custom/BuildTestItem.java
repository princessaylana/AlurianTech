package za.lana.aluriantech.Item.custom;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import za.lana.aluriantech.AlurianTech;
import za.lana.aluriantech.networking.AlurianTechPackets;
import za.lana.aluriantech.util.StructurePlacerAPI;

public class BuildTestItem extends Item {
    private ServerWorld level;
    public BuildTestItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        //needs to send below in a packet to server
        BlockPos positionClicked = context.getBlockPos();
        PlayerEntity player = context.getPlayer();
        assert player != null;
        if(player.getWorld().isClient)
            ClientPlayNetworking.send(AlurianTechPackets.BUILD_STRUCTURE_CS_SYNCPACKET, PacketByteBufs.create());
        //context.getStack().damage(1, context.getPlayer(), playerEntity -> playerEntity.sendToolBreakStatus(playerEntity.getActiveHand()));
        return ActionResult.SUCCESS;
    }
}
