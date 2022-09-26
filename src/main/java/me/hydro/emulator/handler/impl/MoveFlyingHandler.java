package me.hydro.emulator.handler.impl;

import me.hydro.emulator.handler.MovementHandler;
import me.hydro.emulator.object.iteration.IterationHolder;
import me.hydro.emulator.util.mcp.MathHelper;

public class MoveFlyingHandler implements MovementHandler {

    @Override
    public IterationHolder handle(IterationHolder iteration) {
        float strafe = iteration.getMotion().getStrafing();
        float forward = iteration.getMotion().getForward();

        float combined = strafe * strafe + forward * forward;

        if (combined >= 1.0E-4F) {
            combined = MathHelper.sqrt_float(combined);

            if (combined < 1.0F) {
                combined = 1.0F;
            }

            combined = iteration.getFriction() / combined;

            strafe *= combined;
            forward *= combined;

            final float yaw = iteration.getInput().getYaw();

            float sin = MathHelper.sin(yaw * (float) Math.PI / 180.0F);
            float cos = MathHelper.cos(yaw * (float) Math.PI / 180.0F);

            iteration.getMotion().addX(strafe * cos - forward * sin);
            iteration.getMotion().addZ(forward * cos + strafe * sin);
        }

        return iteration;
    }
}
