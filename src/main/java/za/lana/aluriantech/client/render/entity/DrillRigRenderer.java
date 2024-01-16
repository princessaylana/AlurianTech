/**
 * Lana 2024
 */
package za.lana.aluriantech.client.render.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import za.lana.aluriantech.AlurianTech;
import za.lana.aluriantech.client.layer.AlurianTechModelLayers;
import za.lana.aluriantech.client.model.CargoDroneModel;
import za.lana.aluriantech.client.model.DrillRigEntityModel;
import za.lana.aluriantech.client.render.feature.DrillRigLaserFeatureRenderer;
import za.lana.aluriantech.entity.drones.CargoDroneEntity;
import za.lana.aluriantech.entity.machine.DrillRigEntity;

public class DrillRigRenderer extends MobEntityRenderer<DrillRigEntity, DrillRigEntityModel<DrillRigEntity>> {
	private final Identifier TEXTURE = new Identifier(AlurianTech.MOD_ID, "textures/entity/transport/drill_rig_texture.png");

	public DrillRigRenderer(EntityRendererFactory.Context context) {
		super(context, new DrillRigEntityModel<>(context.getPart(AlurianTechModelLayers.DRILL_RIG)), 1.7f); //entity shadow
		this.addFeature(new DrillRigLaserFeatureRenderer<>(this));
	}

	@Override
	public Identifier getTexture(DrillRigEntity entity) {
		return TEXTURE;
	}

	@Override
	public void render(DrillRigEntity mobEntity, float f, float g, MatrixStack matrixStack,
					   VertexConsumerProvider vertexConsumerProvider, int i) {
		if(mobEntity.isBaby()){
			matrixStack.scale(0.5f, 0.5f, 0.5f);
		} else {
			matrixStack.scale(1.0f, 1.0f ,1.0f);
		}
		super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);

	}
}
