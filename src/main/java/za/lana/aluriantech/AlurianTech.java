/**
 * Lana 2024
 */
package za.lana.aluriantech;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.lana.aluriantech.Item.AlurianTechItemGroup;
import za.lana.aluriantech.Item.AluriantechItems;
import za.lana.aluriantech.block.AlurianTechBlockEntities;
import za.lana.aluriantech.block.AlurianTechBlocks;
import za.lana.aluriantech.client.screen.gui.AlurianTechScreens;
import za.lana.aluriantech.entity.AlurianTechEntities;
import za.lana.aluriantech.entity.drones.CargoDroneEntity;
import za.lana.aluriantech.entity.machine.DrillRigEntity;
import za.lana.aluriantech.entity.transport.JeepEntity;
import za.lana.aluriantech.networking.AlurianTechPackets;
import za.lana.aluriantech.networking.packets.ControlInputC2SPacket;
import za.lana.aluriantech.sound.AlurianTechSounds;

public class AlurianTech implements ModInitializer {

	public static final String MOD_ID = "aluriantech";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		AlurianTechBlocks.registerAlurianTechBlocks();
		AlurianTechBlockEntities.registerBlockEntities();
		AlurianTechBlockEntities.registerLibGuiBlockEntities();

		AluriantechItems.registerAlurianTechItems();
		AlurianTechItemGroup.registerAlurianTechItemGroup();

		AlurianTechScreens.registerGuiScreens();
		AlurianTechPackets.registerC2SPackets();

		AlurianTechSounds.registerModSounds();

		FabricDefaultAttributeRegistry.register(AlurianTechEntities.CARGODRONE, CargoDroneEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(AlurianTechEntities.DRILL_RIG, DrillRigEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(AlurianTechEntities.JEEP, JeepEntity.setAttributes());

		ControlInputC2SPacket.init();

		LOGGER.info("Main Init Success " + MOD_ID);
	}
}