/**
 * Lana 2024
 */
package za.lana.aluriantech.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import za.lana.aluriantech.AlurianTech;
import za.lana.aluriantech.block.custom.ATCoreBlock;
import za.lana.aluriantech.block.custom.DroneBoxBlock;


public class AlurianTechBlocks {

    public static final Block DRONEBOX_BLOCK = registerBlock("dronebox_block",
            new DroneBoxBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)));
    public static final Block ATCORE_BLOCK = registerBlock("atcore_block",
            new ATCoreBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)));

    //
    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(AlurianTech.MOD_ID, name), block);
    }
    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(AlurianTech.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }
    public static void registerAlurianTechBlocks() {
        AlurianTech.LOGGER.info("Registering Blocks for " + AlurianTech.MOD_ID);
    }
}
