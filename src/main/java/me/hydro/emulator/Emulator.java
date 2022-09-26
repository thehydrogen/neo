package me.hydro.emulator;

import lombok.Data;
import me.hydro.emulator.handler.impl.JumpHandler;
import me.hydro.emulator.handler.impl.MoveEntityHandler;
import me.hydro.emulator.handler.impl.MoveEntityWithHeadingHandler;
import me.hydro.emulator.handler.impl.MoveFlyingHandler;
import me.hydro.emulator.object.input.DataSupplier;
import me.hydro.emulator.object.input.IterationInput;
import me.hydro.emulator.object.iteration.IterationHolder;
import me.hydro.emulator.object.iteration.Motion;
import me.hydro.emulator.object.result.IterationResult;
import me.hydro.emulator.util.MojangCocaine;

import java.util.ArrayList;
import java.util.List;

@Data
public class Emulator {

    private final DataSupplier dataSupplier;

    private final JumpHandler jumpHandler = new JumpHandler();
    private final MoveFlyingHandler moveFlyingHandler = new MoveFlyingHandler();
    private final MoveEntityHandler moveEntityHandler = new MoveEntityHandler();
    private final MoveEntityWithHeadingHandler moveEntityWithHeadingHandler = new MoveEntityWithHeadingHandler();

    public IterationResult runIteration(final IterationInput input) {
        final Motion motion = input.getPreviousMotion().clone();
        final List<String> tags = new ArrayList<>();

        float forward = input.getForward();
        float strafing = input.getStrafing();

        // Are they sneaking? Slow them down some
        if (input.isSneaking()) {
            // these values aren't quite right,
            // try and find out what's wrong :)
            forward *= 0.3F;
            strafing *= 0.3F;

            tags.add("sneaking");
        }

        // Are they using an item? Slow them down a little more
        if (input.isUsingItem()) {
            forward *= 0.2F;
            strafing *= 0.2F;

            tags.add("using");
        }

        // Mojang multiplies by 0.98F, so do we
        forward *= 0.98F;
        strafing *= 0.98F;

        motion.setForward(forward);
        motion.setStrafing(strafing);

        // Create the new iteration holder
        IterationHolder iteration = new IterationHolder(this, input, dataSupplier);

        iteration.setMotion(motion);
        iteration.setTags(tags);

        // Hit slowdown modifies motion and not forward/strafing input
        // Multiply by 0.6D
        if (input.isHitSlowdown()) {
            motion.multiplyX(0.6D);
            motion.multiplyZ(0.6D);

            tags.add("slowdown");
        }

        if (Math.abs(motion.getMotionX()) < MojangCocaine.RESET) motion.setMotionX(0);
        if (Math.abs(motion.getMotionY()) < MojangCocaine.RESET) motion.setMotionY(0);
        if (Math.abs(motion.getMotionZ()) < MojangCocaine.RESET) motion.setMotionZ(0);

        if (input.isJumping()) {
            iteration = jumpHandler.handle(iteration);
        }

        iteration = moveEntityWithHeadingHandler.handle(iteration);

        return new IterationResult(iteration.getOffset(), iteration.getPredicted(), iteration.getMotion(),
                iteration.getTags());
    }
}
