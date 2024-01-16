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
import za.lana.aluriantech.client.model.JeepEntityModel;
import za.lana.aluriantech.entity.transport.JeepEntity;

public class JeepRenderer extends MobEntityRenderer<JeepEntity, JeepEntityModel<JeepEntity>> {
	private final Identifier TEXTURE = new Identifier(AlurianTech.MOD_ID, "textures/entity/transport/jeep_entity_texture.png");

	public JeepRenderer(EntityRendererFactory.Context context) {
		super(context, new JeepEntityModel<>(context.getPart(AlurianTechModelLayers.JEEP)), 1.7f); //entity shadow
	}

	@Override
	public Identifier getTexture(JeepEntity entity) {
		return TEXTURE;
	}

	@Override
	public void render(JeepEntity mobEntity, float f, float g, MatrixStack matrixStack,
					   VertexConsumerProvider vertexConsumerProvider, int i) {
		if(mobEntity.isBaby()){
			matrixStack.scale(0.5f, 0.5f, 0.5f);
		} else {
			matrixStack.scale(1.0f, 1.0f ,1.0f);
		}
		super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);

	}
}
