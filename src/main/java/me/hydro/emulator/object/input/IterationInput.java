package me.hydro.emulator.object.input;

import lombok.Builder;
import lombok.Data;
import me.hydro.emulator.object.iteration.Motion;
import me.hydro.emulator.util.Vector;
import me.hydro.emulator.util.mcp.AxisAlignedBB;

@Data
@Builder
public class IterationInput implements Cloneable {

    private final boolean ground, jumping, sprinting, usingItem, hitSlowdown, sneaking;
    private final int forward, strafing;
    private final float yaw;

    private final Motion previousMotion;

    private final AxisAlignedBB lastReportedBoundingBox;
    private final Vector velocity;
    private final Vector to;

    @Override
    public IterationInput clone() {
        return IterationInput.builder()
                .ground(ground)
                .jumping(jumping)
                .sprinting(sprinting)
                .usingItem(usingItem)
                .hitSlowdown(hitSlowdown)
                .sneaking(sneaking)
                .forward(forward)
                .strafing(strafing)
                .yaw(yaw)
                .lastReportedBoundingBox(lastReportedBoundingBox)
                .velocity(velocity)
                .to(to)
                .previousMotion(previousMotion)
                .build();
    }
}
