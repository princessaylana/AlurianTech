/**
 * Lana 2024
 */
package za.lana.aluriantech.Item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import za.lana.aluriantech.AlurianTech;
import za.lana.aluriantech.block.AlurianTechBlocks;

import static za.lana.aluriantech.block.AlurianTechBlocks.ATCORE_BLOCK;
import static za.lana.aluriantech.block.AlurianTechBlocks.DRONEBOX_BLOCK;

public class AlurianTechItemGroup {
    public static final ItemGroup ALURIANTECH_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(AlurianTech.MOD_ID, "aluriantech_group"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.aluriantech"))
                    .icon(() -> new ItemStack(AlurianTechBlocks.DRONEBOX_BLOCK)).entries((displayContext, entries) -> {
                        //
                        entries.add(AluriantechItems.DRILLRIG_SPAWN_EGG);
                        entries.add(AluriantechItems.CARGODRONE_SPAWN_EGG);
                        entries.add(AluriantechItems.JEEP_SPAWN_EGG);
                        entries.add(AluriantechItems.BUILD_TEST_ITEM);
                        entries.add(DRONEBOX_BLOCK);
                        entries.add(ATCORE_BLOCK);

                    }).build());
    public static void registerAlurianTechItemGroup(){
        AlurianTech.LOGGER.info("Registering ItemGroup for " + AlurianTech.MOD_ID);
    }
}

