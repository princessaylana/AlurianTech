/**
 * Lana 2024
 */
package za.lana.aluriantech.entity.control;

import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import za.lana.aluriantech.entity.drones.CargoDroneEntity;

public class CargoDroneControl
        extends MoveControl {
    private final CargoDroneEntity cdrone;

    private int hoverDuration;

    public CargoDroneControl(CargoDroneEntity cdrone, int maxPitchChange, boolean noGravity) {
        super(cdrone);
        this.cdrone = cdrone;
    }

    @Override
    public void tick() {
        if (this.state != State.MOVE_TO) {
            return;
        }
        if (this.hoverDuration-- <= 0) {
            this.hoverDuration += this.cdrone.getRandom().nextInt(5) + 2;
            Vec3d route = new Vec3d(this.targetX - this.cdrone.getX(), this.targetY - this.cdrone.getY(), this.targetZ - this.cdrone.getZ());
            double distance = route.length();

            if (this.willBump(route = route.normalize(), MathHelper.ceil(distance))) {
                this.cdrone.setVelocity(this.cdrone.getVelocity().add(route.multiply(0.1)));

            } else {
                this.state = State.WAIT;

            }
        }
    }
    private boolean willBump(Vec3d direction, int steps) {
        Box body = this.cdrone.getBoundingBox();
        for (int move = 1; move < steps; ++move) {
            body = body.offset(direction);
            if (this.cdrone.getWorld().isSpaceEmpty(this.cdrone, body)) continue;
            return false;
        }
        return !this.cdrone.isInLandingPose();
    }
}
