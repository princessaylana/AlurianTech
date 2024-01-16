package za.lana.aluriantech.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import za.lana.aluriantech.client.animation.CargoDroneAnimations;
import za.lana.aluriantech.client.animation.DrillRigAnimations;
import za.lana.aluriantech.entity.drones.CargoDroneEntity;
import za.lana.aluriantech.entity.machine.DrillRigEntity;

// Made with Blockbench 4.9.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class DrillRigEntityModel <T extends DrillRigEntity> extends SinglePartEntityModel<T> {
	private final ModelPart drillRig;
	public DrillRigEntityModel(ModelPart root) {
		this.drillRig = root.getChild("mainBody");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData mainBody = modelPartData.addChild("mainBody", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData machine = mainBody.addChild("machine", ModelPartBuilder.create().uv(0, 76).cuboid(-14.0F, -15.0F, -19.0F, 29.0F, 8.0F, 7.0F, new Dilation(0.0F))
		.uv(126, 0).cuboid(-15.0F, -36.0F, 13.0F, 30.0F, 29.0F, 39.0F, new Dilation(0.0F))
		.uv(0, 41).cuboid(-15.0F, -34.0F, 52.0F, 30.0F, 21.0F, 13.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(-8.0F, -7.0F, -47.0F, 16.0F, 3.0F, 93.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData controlpanel_r1 = machine.addChild("controlpanel_r1", ModelPartBuilder.create().uv(4, 277).cuboid(-9.0F, -29.0F, 1.0F, 18.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		ModelPartData haudralics = machine.addChild("haudralics", ModelPartBuilder.create(), ModelTransform.pivot(-28.0F, -7.0F, -26.0F));

		ModelPartData stand01 = haudralics.addChild("stand01", ModelPartBuilder.create().uv(106, 168).cuboid(7.0F, -16.0F, -26.0F, 8.0F, 11.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData foot = stand01.addChild("foot", ModelPartBuilder.create().uv(95, 217).cuboid(-3.0F, -11.5F, -3.0F, 6.0F, 16.0F, 6.0F, new Dilation(0.0F))
		.uv(226, 17).cuboid(-4.0F, 1.5F, -4.0F, 8.0F, 4.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(11.0F, -5.5F, -22.0F));

		ModelPartData stand2 = haudralics.addChild("stand2", ModelPartBuilder.create().uv(41, 168).cuboid(-15.0F, -16.0F, -26.0F, 8.0F, 11.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(56.0F, 0.0F, 0.0F));

		ModelPartData foot2 = stand2.addChild("foot2", ModelPartBuilder.create().uv(77, 97).cuboid(-3.0F, -11.5F, -3.0F, 6.0F, 16.0F, 6.0F, new Dilation(0.0F))
		.uv(216, 138).cuboid(-4.0F, 1.5F, -4.0F, 8.0F, 4.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(-11.0F, -5.5F, -22.0F));

		ModelPartData stand3 = haudralics.addChild("stand3", ModelPartBuilder.create().uv(126, 0).cuboid(-15.0F, -16.0F, -26.0F, 8.0F, 11.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(56.0F, 0.0F, 106.0F));

		ModelPartData foot3 = stand3.addChild("foot3", ModelPartBuilder.create().uv(37, 97).cuboid(-3.0F, -11.5F, -3.0F, 6.0F, 16.0F, 6.0F, new Dilation(0.0F))
		.uv(0, 146).cuboid(-4.0F, 1.5F, -4.0F, 8.0F, 4.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(-11.0F, -5.5F, -22.0F));

		ModelPartData stand4 = haudralics.addChild("stand4", ModelPartBuilder.create().uv(53, 0).cuboid(7.0F, -16.0F, -26.0F, 8.0F, 11.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 106.0F));

		ModelPartData foot4 = stand4.addChild("foot4", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -11.5F, -3.0F, 6.0F, 16.0F, 6.0F, new Dilation(0.0F))
		.uv(126, 20).cuboid(-4.0F, 1.5F, -4.0F, 8.0F, 4.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(11.0F, -5.5F, -22.0F));

		ModelPartData seat = machine.addChild("seat", ModelPartBuilder.create().uv(164, 97).cuboid(-19.0F, -25.0F, 8.0F, 38.0F, 18.0F, 5.0F, new Dilation(0.0F))
		.uv(61, 217).cuboid(14.0F, -18.0F, -19.0F, 1.0F, 9.0F, 31.0F, new Dilation(0.0F))
		.uv(182, 121).cuboid(-15.0F, -18.0F, -19.0F, 1.0F, 9.0F, 31.0F, new Dilation(0.0F))
		.uv(126, 69).cuboid(-14.0F, -9.0F, -12.0F, 28.0F, 2.0F, 20.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData axle = machine.addChild("axle", ModelPartBuilder.create().uv(226, 0).cuboid(4.0F, -16.0F, -36.0F, 20.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.5F, 0.0F, 67.0F));

		ModelPartData wheel = axle.addChild("wheel", ModelPartBuilder.create().uv(182, 164).cuboid(-4.0F, -11.9896F, -11.9853F, 8.0F, 24.0F, 24.0F, new Dilation(0.0F)), ModelTransform.pivot(21.0F, -12.0104F, -32.0147F));

		ModelPartData cube_r1 = wheel.addChild("cube_r1", ModelPartBuilder.create().uv(0, 217).cuboid(18.0F, -32.5F, -8.5F, 6.0F, 24.0F, 24.0F, new Dilation(0.0F)), ModelTransform.of(-21.0F, 12.0104F, -16.9853F, -0.7854F, 0.0F, 0.0F));

		ModelPartData axle2 = machine.addChild("axle2", ModelPartBuilder.create().uv(223, 162).cuboid(-24.0F, -16.0F, -36.0F, 20.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(1.5F, 0.0F, 67.0F));

		ModelPartData wheel2 = axle2.addChild("wheel2", ModelPartBuilder.create().uv(65, 168).cuboid(-4.0F, -11.9896F, -11.9853F, 8.0F, 24.0F, 24.0F, new Dilation(0.0F)), ModelTransform.pivot(-21.0F, -12.0104F, -32.0147F));

		ModelPartData cube_r2 = wheel2.addChild("cube_r2", ModelPartBuilder.create().uv(191, 213).cuboid(-24.0F, -32.5F, -8.5F, 6.0F, 24.0F, 24.0F, new Dilation(0.0F)), ModelTransform.of(21.0F, 12.0104F, -16.9853F, -0.7854F, 0.0F, 0.0F));

		ModelPartData axle3 = machine.addChild("axle3", ModelPartBuilder.create().uv(216, 121).cuboid(4.0F, -16.0F, -36.0F, 20.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.5F, 0.0F, 7.0F));

		ModelPartData wheel3 = axle3.addChild("wheel3", ModelPartBuilder.create().uv(0, 168).cuboid(-4.0F, -11.9896F, -11.9853F, 8.0F, 24.0F, 24.0F, new Dilation(0.0F)), ModelTransform.pivot(21.0F, -12.0104F, -32.0147F));

		ModelPartData cube_r3 = wheel3.addChild("cube_r3", ModelPartBuilder.create().uv(130, 189).cuboid(18.0F, -32.5F, -8.5F, 6.0F, 24.0F, 24.0F, new Dilation(0.0F)), ModelTransform.of(-21.0F, 12.0104F, -16.9853F, -0.7854F, 0.0F, 0.0F));

		ModelPartData axle4 = machine.addChild("axle4", ModelPartBuilder.create().uv(203, 69).cuboid(-24.0F, -16.0F, -36.0F, 20.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(1.5F, 0.0F, 7.0F));

		ModelPartData wheel4 = axle4.addChild("wheel4", ModelPartBuilder.create().uv(141, 139).cuboid(-4.0F, -11.9896F, -11.9853F, 8.0F, 24.0F, 24.0F, new Dilation(0.0F)), ModelTransform.pivot(-21.0F, -12.0104F, -32.0147F));

		ModelPartData cube_r4 = wheel4.addChild("cube_r4", ModelPartBuilder.create().uv(0, 97).cuboid(-24.0F, -32.5F, -8.5F, 6.0F, 24.0F, 24.0F, new Dilation(0.0F)), ModelTransform.of(21.0F, 12.0104F, -16.9853F, -0.7854F, 0.0F, 0.0F));

		ModelPartData front = machine.addChild("front", ModelPartBuilder.create().uv(77, 97).cuboid(-14.0F, -20.0F, -47.0F, 29.0F, 13.0F, 28.0F, new Dilation(0.0F))
		.uv(77, 139).cuboid(-14.0F, -20.0F, -51.0F, 29.0F, 11.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData drillArm = front.addChild("drillArm", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -5.0F, -31.5F, 10.0F, 8.0F, 32.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -9.0F, -46.5F, -0.2182F, 0.0F, 0.0F));

		ModelPartData bearing = drillArm.addChild("bearing", ModelPartBuilder.create().uv(53, 20).cuboid(-2.0F, -10.0F, -29.5F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F))
		.uv(37, 217).cuboid(-6.0F, -10.0F, -33.5F, 12.0F, 6.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -5.8578F, -1.2986F, 0.2182F, 0.0F, 0.0F));

		ModelPartData drill = bearing.addChild("drill", ModelPartBuilder.create().uv(0, 97).cuboid(-3.0F, -14.0F, -40.5F, 6.0F, 6.0F, 64.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData laser = drill.addChild("laser", ModelPartBuilder.create().uv(79, 243).cuboid(-1.5F, -1.5F, -28.5F, 3.0F, 3.0F, 57.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -11.0F, -11.0F));
		return TexturedModelData.of(modelData, 512, 512);
	}
	@Override
	public void setAngles(DrillRigEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.animateMovement(DrillRigAnimations.DRILLRIG_MOVE, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.updateAnimation(entity.idleAniState, DrillRigAnimations.DRILLRIG_IDLE_BURNING, ageInTicks, 1f);
		this.updateAnimation(entity.drillAniState, DrillRigAnimations.DRILLRIG_QUICK_DRILL, ageInTicks, 1f);
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		drillRig.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return this.drillRig;
	}
}