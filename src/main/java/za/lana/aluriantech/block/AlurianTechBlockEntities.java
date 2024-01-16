/**
 * Lana 2024
 */
package za.lana.aluriantech.block;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import za.lana.aluriantech.AlurianTech;
import za.lana.aluriantech.block.blockentity.DroneBoxBlockEntity;

public class AlurianTechBlockEntities {

    public static BlockEntityType<DroneBoxBlockEntity> DRONEBOX_BLOCK_ENTITY;

    public static void registerBlockEntities(){
        // VANILLA TYPES
    }
    public static void registerLibGuiBlockEntities(){
        DRONEBOX_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(DroneBoxBlockEntity::new, AlurianTechBlocks.DRONEBOX_BLOCK).build(null);
        Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(AlurianTech.MOD_ID, "dronebox_block_entity"), DRONEBOX_BLOCK_ENTITY);

    }
}
