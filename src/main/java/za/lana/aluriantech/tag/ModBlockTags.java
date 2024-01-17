/**
 * Lana 2024
 */
package za.lana.aluriantech.tag;

import net.minecraft.block.Block;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import za.lana.aluriantech.AlurianTech;

public class ModBlockTags {
    public static final TagKey<Block> CARGODRONE_LANDING_BLOCKS = register(RegistryKeys.BLOCK,"cargodrone_landing_blocks");
    public static final TagKey<Block> DRILLRIG_BLOCKS = register(RegistryKeys.BLOCK,"drillrig_blocks");

    private static<T> TagKey<T> register(RegistryKey<? extends Registry<T>> registryKey, String id) {
        return TagKey.of(registryKey, new Identifier(AlurianTech.MOD_ID ,id));
    }
    public static void registerModBlockTags() {
        AlurianTech.LOGGER.info("Registering Tags for  " + AlurianTech.MOD_ID);
    }
}
