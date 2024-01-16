/**
 * Lana 2024
 */
package za.lana.aluriantech.client.layer;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import za.lana.aluriantech.AlurianTech;

public class AlurianTechModelLayers {
    public static final EntityModelLayer CARGODRONE =
            new EntityModelLayer(new Identifier(AlurianTech.MOD_ID, "cargodrone"), "mainBody");
    public static final EntityModelLayer JEEP =
            new EntityModelLayer(new Identifier(AlurianTech.MOD_ID, "jeep"), "mainBody");
    public static final EntityModelLayer DRILL_RIG =
            new EntityModelLayer(new Identifier(AlurianTech.MOD_ID, "drill_rig"), "mainBody");


}

