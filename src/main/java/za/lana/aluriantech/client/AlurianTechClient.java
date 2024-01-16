/**
 * Lana 2024
 */
package za.lana.aluriantech.client;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import za.lana.aluriantech.AlurianTech;
import za.lana.aluriantech.client.input.KeyInputHandler;
import za.lana.aluriantech.client.layer.AlurianTechModelLayers;
import za.lana.aluriantech.client.model.CargoDroneModel;
import za.lana.aluriantech.client.model.DrillRigEntityModel;
import za.lana.aluriantech.client.model.JeepEntityModel;
import za.lana.aluriantech.client.networking.ControlInputPacket;
import za.lana.aluriantech.client.networking.ControlInputSyncPacket;
import za.lana.aluriantech.client.networking.Vec3SyncPacket;
import za.lana.aluriantech.client.render.entity.CargoDroneRenderer;
import za.lana.aluriantech.client.render.entity.DrillRigRenderer;
import za.lana.aluriantech.client.render.entity.JeepRenderer;
import za.lana.aluriantech.client.screen.gui.AlurianTechScreens;
import za.lana.aluriantech.client.screen.gui.DroneBoxBlockScreen;
import za.lana.aluriantech.client.screen.gui.DroneBoxDescription;
import za.lana.aluriantech.entity.AlurianTechEntities;
import za.lana.aluriantech.networking.AlurianTechPackets;

import static za.lana.aluriantech.AlurianTech.LOGGER;

public class AlurianTechClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AlurianTechPackets.registerS2CPackets();
        KeyInputHandler.register();
        ControlInputPacket.init();
        ControlInputSyncPacket.init();
        Vec3SyncPacket.init();
        //
        EntityModelLayerRegistry.registerModelLayer(AlurianTechModelLayers.CARGODRONE, CargoDroneModel::getTexturedModelData);
        EntityRendererRegistry.register(AlurianTechEntities.CARGODRONE, CargoDroneRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(AlurianTechModelLayers.DRILL_RIG, DrillRigEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(AlurianTechEntities.DRILL_RIG, DrillRigRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(AlurianTechModelLayers.JEEP, JeepEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(AlurianTechEntities.JEEP, JeepRenderer::new);
        //
        HandledScreens.<DroneBoxDescription, CottonInventoryScreen<DroneBoxDescription>>register(
                AlurianTechScreens.DRONEBOX_GUI, DroneBoxBlockScreen::new);

        LOGGER.info(AlurianTech.MOD_ID + "Client Initialized");
    }
}
