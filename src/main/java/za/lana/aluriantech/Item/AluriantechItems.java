package za.lana.aluriantech.Item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import za.lana.aluriantech.AlurianTech;
import za.lana.aluriantech.Item.custom.BuildTestItem;
import za.lana.aluriantech.block.AlurianTechBlocks;
import za.lana.aluriantech.entity.AlurianTechEntities;

public class AluriantechItems {

    public static final Item BUILD_TEST_ITEM = registerItem("build_test_item",
            new BuildTestItem(new FabricItemSettings()));

    public static final Item CARGODRONE_SPAWN_EGG = registerItem("cargodrone_spawn_egg", new SpawnEggItem(
            AlurianTechEntities.CARGODRONE, 0xDCE8E8, 0xEEFC69, new FabricItemSettings()));
    public static final Item DRILLRIG_SPAWN_EGG = registerItem("drillrig_spawn_egg", new SpawnEggItem(
            AlurianTechEntities.DRILL_RIG, 0xDCE8E8, 0xEEFC69, new FabricItemSettings()));
    public static final Item JEEP_SPAWN_EGG = registerItem("jeep_spawn_egg", new SpawnEggItem(
            AlurianTechEntities.JEEP, 0xDCE8E8, 0xEEFC69, new FabricItemSettings()));

    public static void addItemsToBuildingBlocksGroup(FabricItemGroupEntries entries){
        entries.add(AlurianTechBlocks.ATCORE_BLOCK);
    }
    //
    public static void addItemsToFunctionalGroup(FabricItemGroupEntries entries){
        // BLOCKENTITIES
        entries.add(AlurianTechBlocks.DRONEBOX_BLOCK);

    }
    public static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(AlurianTech.MOD_ID, name), item);

    }
    public static void registerAlurianTechItems(){
        AlurianTech.LOGGER.info("Registering Items for " + AlurianTech.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(AluriantechItems::addItemsToBuildingBlocksGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(AluriantechItems::addItemsToFunctionalGroup);
    }
}
