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
import za.lana.aluriantech.entity.drones.CargoDroneEntity;

public class CargoDroneRenderer extends MobEntityRenderer<CargoDroneEntity, CargoDroneModel<CargoDroneEntity>> {
	private final Identifier TEXTURE = new Identifier(AlurianTech.MOD_ID, "textures/entity/transport/cargo_drone_texture.png");

	public CargoDroneRenderer(EntityRendererFactory.Context context) {
		super(context, new CargoDroneModel<>(context.getPart(AlurianTechModelLayers.CARGODRONE)), 0.25f); //entity shadow
	}

	@Override
	public Identifier getTexture(CargoDroneEntity entity) {
		return TEXTURE;
	}

	@Override
	public void render(CargoDroneEntity mobEntity, float f, float g, MatrixStack matrixStack,
					   VertexConsumerProvider vertexConsumerProvider, int i) {
		if(mobEntity.isBaby()){
			matrixStack.scale(0.5f, 0.5f, 0.5f);
		} else {
			matrixStack.scale(1.0f, 1.0f ,1.0f);
		}
		super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);

	}
}
