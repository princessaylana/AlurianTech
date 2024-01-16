/**
 * Lana 2024
 */
package za.lana.aluriantech.tag;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import za.lana.aluriantech.AlurianTech;

public final class ModBlockTags {
    public static final TagKey<Block> CARGODRONE_LANDING_BLOCKS = ModBlockTags.of("cargodrone_landing_blocks");
    public static final TagKey<Block> DRILLRIG_BLOCKS = ModBlockTags.of("drillrig_blocks");


    private ModBlockTags() {
    }

    private static TagKey<Block> of(String id) {
        return TagKey.of(RegistryKeys.BLOCK, new Identifier(AlurianTech.MOD_ID));
    }
}
