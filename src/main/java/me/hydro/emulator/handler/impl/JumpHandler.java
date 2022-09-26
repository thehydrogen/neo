package me.hydro.emulator.handler.impl;

import me.hydro.emulator.handler.MovementHandler;
import me.hydro.emulator.object.input.IterationInput;
import me.hydro.emulator.object.iteration.IterationHolder;
import me.hydro.emulator.object.iteration.Motion;
import me.hydro.emulator.util.MojangCocaine;
import me.hydro.emulator.util.mcp.MathHelper;

public class JumpHandler implements MovementHandler {

    /**
     * You can find the code referred to here in MCP's
     * Entity#jump method.
     */

    @Override
    public IterationHolder handle(IterationHolder holder) {
        final IterationInput input = holder.getInput();
        final Motion motion = holder.getMotion();

        // Set motion Y to 0.42F (and add our corresponding tag)
        motion.setMotionY(MojangCocaine.UPWARDS_MOTION);
        holder.getTags().add("jump");

        // Look at the client code to see how potion effects are handled :)

        // We sprinting? Let's do some based
        if (input.isSprinting()) {
            final float f = input.getYaw() * 0.017453292F;

            motion.subtractX(MathHelper.sin(f) * 0.2F);
            motion.addZ(MathHelper.cos(f) * 0.2F);
        }

        return holder;
    }
}
