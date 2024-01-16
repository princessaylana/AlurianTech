/**
 * Lana 2024
 */
package za.lana.aluriantech.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import za.lana.aluriantech.AlurianTech;
import za.lana.aluriantech.entity.drones.CargoDroneEntity;
import za.lana.aluriantech.entity.machine.DrillRigEntity;
import za.lana.aluriantech.entity.transport.JeepEntity;

public class AlurianTechEntities {

    public static final EntityType<CargoDroneEntity> CARGODRONE = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(AlurianTech.MOD_ID, "cargodrone"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, CargoDroneEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());
    public static final EntityType<DrillRigEntity> DRILL_RIG = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(AlurianTech.MOD_ID, "drill_rig"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, DrillRigEntity::new)
                    .dimensions(EntityDimensions.fixed(3.0f, 3.0f)).build());
    public static final EntityType<JeepEntity> JEEP = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(AlurianTech.MOD_ID, "jeep"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, JeepEntity::new)
                    .dimensions(EntityDimensions.fixed(3.0f, 3.0f)).build());

    public static void registerModEntities() {
        AlurianTech.LOGGER.info("Registering LivingEntities" + AlurianTech.MOD_ID);
    }

}
