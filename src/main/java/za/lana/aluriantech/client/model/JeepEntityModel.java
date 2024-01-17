// Made with Blockbench 4.9.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
// Lana 2024
package za.lana.aluriantech.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import za.lana.aluriantech.client.animation.DrillRigAnimations;
import za.lana.aluriantech.client.animation.JeepAnimations;
import za.lana.aluriantech.entity.machine.DrillRigEntity;
import za.lana.aluriantech.entity.transport.JeepEntity;


public class JeepEntityModel<T extends JeepEntity> extends SinglePartEntityModel<T> {
	private final ModelPart jeep;
	public JeepEntityModel(ModelPart root) {
		this.jeep = root.getChild("mainBody");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData mainBody = modelPartData.addChild("mainBody", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 21.0F, 0.0F));

		ModelPartData body = mainBody.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-16.0F, -15.0F, 8.0F, 32.0F, 15.0F, 15.0F, new Dilation(0.0F))
				.uv(98, 0).cuboid(-7.0F, -19.0F, 23.0F, 14.0F, 4.0F, 17.0F, new Dilation(0.0F))
				.uv(102, 78).cuboid(-9.0F, -4.0F, -25.0F, 18.0F, 4.0F, 14.0F, new Dilation(0.0F))
				.uv(50, 92).cuboid(-9.0F, -4.0F, 23.0F, 18.0F, 4.0F, 15.0F, new Dilation(0.0F))
				.uv(56, 43).cuboid(-9.0F, -14.0F, -30.0F, 18.0F, 10.0F, 19.0F, new Dilation(0.0F))
				.uv(0, 31).cuboid(-9.0F, -15.0F, 23.0F, 18.0F, 11.0F, 19.0F, new Dilation(0.0F))
				.uv(34, 73).cuboid(-17.0F, -15.0F, -11.0F, 34.0F, 15.0F, 3.0F, new Dilation(0.0F))
				.uv(112, 38).cuboid(-16.0F, -7.25F, -32.0F, 32.0F, 6.0F, 2.0F, new Dilation(0.0F))
				.uv(98, 22).cuboid(-16.0F, -7.0F, 42.0F, 32.0F, 6.0F, 2.0F, new Dilation(0.0F))
				.uv(64, 0).cuboid(-17.0F, -10.0F, -8.0F, 1.0F, 6.0F, 31.0F, new Dilation(0.0F))
				.uv(0, 62).cuboid(16.0F, -10.0F, -8.0F, 1.0F, 6.0F, 31.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData seat = body.addChild("seat", ModelPartBuilder.create().uv(101, 97).cuboid(-9.0F, -1.0F, -8.0F, 16.0F, 1.0F, 16.0F, new Dilation(0.0F))
				.uv(0, 100).cuboid(-25.0F, -1.0F, -8.0F, 16.0F, 1.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(9.0F, 0.0F, 0.0F));

		ModelPartData mudguard2 = body.addChild("mudguard2", ModelPartBuilder.create().uv(150, 97).cuboid(9.0F, -21.0F, 27.0F, 9.0F, 1.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 7.0F, -52.0F));

		ModelPartData cube_r1 = mudguard2.addChild("cube_r1", ModelPartBuilder.create().uv(153, 81).cuboid(9.0F, -17.0F, 15.0F, 9.0F, 1.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -13.8F, 14.5F, -0.5236F, 0.0F, 0.0F));

		ModelPartData cube_r2 = mudguard2.addChild("cube_r2", ModelPartBuilder.create().uv(0, 83).cuboid(9.0F, -17.0F, -20.0F, 9.0F, 1.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -13.8F, 48.5F, 0.5236F, 0.0F, 0.0F));

		ModelPartData mudguard = body.addChild("mudguard", ModelPartBuilder.create().uv(112, 140).cuboid(-18.0F, -21.0F, 27.0F, 9.0F, 1.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 7.0F, -52.0F));

		ModelPartData cube_r3 = mudguard.addChild("cube_r3", ModelPartBuilder.create().uv(152, 47).cuboid(-18.0F, -17.0F, 15.0F, 9.0F, 1.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -13.8F, 14.5F, -0.5236F, 0.0F, 0.0F));

		ModelPartData cube_r4 = mudguard.addChild("cube_r4", ModelPartBuilder.create().uv(0, 62).cuboid(-18.0F, -17.0F, -20.0F, 9.0F, 1.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -13.8F, 48.5F, 0.5236F, 0.0F, 0.0F));

		ModelPartData mudguard3 = body.addChild("mudguard3", ModelPartBuilder.create().uv(144, 0).cuboid(9.0F, -21.0F, 27.0F, 9.0F, 1.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 7.0F, 1.0F));

		ModelPartData cube_r5 = mudguard3.addChild("cube_r5", ModelPartBuilder.create().uv(0, 76).cuboid(9.0F, -17.0F, 15.0F, 9.0F, 1.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -13.8F, 14.5F, -0.5236F, 0.0F, 0.0F));

		ModelPartData cube_r6 = mudguard3.addChild("cube_r6", ModelPartBuilder.create().uv(153, 72).cuboid(9.0F, -17.0F, -22.0F, 9.0F, 1.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -13.8F, 48.5F, 0.5236F, 0.0F, 0.0F));

		ModelPartData mudguard4 = body.addChild("mudguard4", ModelPartBuilder.create().uv(140, 142).cuboid(-18.0F, -21.0F, 27.0F, 9.0F, 1.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 7.0F, 1.0F));

		ModelPartData cube_r7 = mudguard4.addChild("cube_r7", ModelPartBuilder.create().uv(0, 69).cuboid(-18.0F, -17.0F, 15.0F, 9.0F, 1.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -13.8F, 14.5F, -0.5236F, 0.0F, 0.0F));

		ModelPartData cube_r8 = mudguard4.addChild("cube_r8", ModelPartBuilder.create().uv(152, 115).cuboid(-18.0F, -17.0F, -22.0F, 9.0F, 1.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -13.8F, 48.5F, 0.5236F, 0.0F, 0.0F));

		ModelPartData axle = body.addChild("axle", ModelPartBuilder.create().uv(7, 5).cuboid(-10.0F, -4.0F, -14.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, -7.0F));

		ModelPartData wheel = axle.addChild("wheel", ModelPartBuilder.create().uv(127, 115).cuboid(-4.0F, -6.0206F, -5.9907F, 6.0F, 12.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(-12.0F, -2.9794F, -13.0093F));

		ModelPartData cube_r9 = wheel.addChild("cube_r9", ModelPartBuilder.create().uv(91, 140).cuboid(-16.0F, -22.0F, -17.25F, 4.0F, 12.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(13.0F, 3.3794F, 19.2593F, 0.7854F, 0.0F, 0.0F));

		ModelPartData axle2 = body.addChild("axle2", ModelPartBuilder.create().uv(7, 0).cuboid(-10.0F, -4.0F, -14.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 45.5F));

		ModelPartData wheel2 = axle2.addChild("wheel2", ModelPartBuilder.create().uv(0, 118).cuboid(-4.0F, -6.0206F, -5.9907F, 6.0F, 12.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(-12.0F, -2.9794F, -13.0093F));

		ModelPartData cube_r10 = wheel2.addChild("cube_r10", ModelPartBuilder.create().uv(58, 137).cuboid(-16.0F, -22.0F, -17.25F, 4.0F, 12.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(13.0F, 3.3794F, 19.2593F, 0.7854F, 0.0F, 0.0F));

		ModelPartData axle3 = body.addChild("axle3", ModelPartBuilder.create().uv(0, 5).cuboid(9.0F, -4.0F, -14.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 45.5F));

		ModelPartData wheel3 = axle3.addChild("wheel3", ModelPartBuilder.create().uv(90, 115).cuboid(-2.0F, -6.0206F, -5.9907F, 6.0F, 12.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(12.0F, -2.9794F, -13.0093F));

		ModelPartData cube_r11 = wheel3.addChild("cube_r11", ModelPartBuilder.create().uv(131, 47).cuboid(12.0F, -22.0F, -17.25F, 4.0F, 12.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 3.3794F, 19.2593F, 0.7854F, 0.0F, 0.0F));

		ModelPartData axle4 = body.addChild("axle4", ModelPartBuilder.create().uv(0, 0).cuboid(9.0F, -4.0F, -14.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, -7.0F));

		ModelPartData wheel4 = axle4.addChild("wheel4", ModelPartBuilder.create().uv(53, 112).cuboid(-2.0F, -6.0206F, -5.9907F, 6.0F, 12.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(12.0F, -2.9794F, -13.0093F));

		ModelPartData cube_r12 = wheel4.addChild("cube_r12", ModelPartBuilder.create().uv(25, 131).cuboid(12.0F, -22.0F, -17.25F, 4.0F, 12.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(-13.0F, 3.3794F, 19.2593F, 0.7854F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 256, 256);
	}
	@Override
	public void setAngles(JeepEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.animateMovement(JeepAnimations.JEEPENTITY_MOVE, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.updateAnimation(entity.idleAniState, JeepAnimations.JEEPENTITY_IDLE, ageInTicks, 1f);
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		jeep.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return this.jeep;
	}
}