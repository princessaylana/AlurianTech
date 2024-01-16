
package za.lana.aluriantech.client.render.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.util.Identifier;
import za.lana.aluriantech.AlurianTech;
import za.lana.aluriantech.client.model.DrillRigEntityModel;
import za.lana.aluriantech.entity.machine.DrillRigEntity;

@Environment(value=EnvType.CLIENT)
public class DrillRigLaserFeatureRenderer<T extends DrillRigEntity>
        extends EyesFeatureRenderer<T, DrillRigEntityModel<T>> {
    private static final RenderLayer SKIN = RenderLayer.getEyes(new Identifier(AlurianTech.MOD_ID, "textures/entity/transport/drill_rig_laser.png"));

    public DrillRigLaserFeatureRenderer(FeatureRendererContext<T, DrillRigEntityModel<T>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public RenderLayer getEyesTexture() {
        return SKIN;
    }
}

