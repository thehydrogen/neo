package me.hydro.emulator.handler.impl;

import me.hydro.emulator.handler.MovementHandler;
import me.hydro.emulator.object.input.IterationInput;
import me.hydro.emulator.object.iteration.IterationHolder;
import me.hydro.emulator.util.MojangCocaine;

public class MoveEntityWithHeadingHandler implements MovementHandler {

    @Override
    public IterationHolder handle(IterationHolder iteration) {
        final IterationInput input = iteration.getInput();
        final boolean onGround = input.isGround();

        // Here we'll get the friction of the block below
        final float friction = onGround
                ? 0.6F * 0.91F // Blocks can have different friction :)
                : 0.91F;

        // Variable (currently unassigned) where we'll put our moveSpeed
        float moveSpeed;

        if (onGround) {
            // Here we'll calculate AI move speed
            // drag = 0.16277136 * friction^3
            //
            // EntityLivingBase#moveEntityWithHeading
            final double aiMoveSpeed = getAiMoveSpeed(input.isSprinting());
            final float drag = MojangCocaine.LAND_MOVEMENT_FACTOR_LEGACY / (friction * friction * friction);

            // Set moveSpeed to aiMoveSpeed * drag
            moveSpeed = (float) (aiMoveSpeed * drag);
            iteration.getTags().add("ground");
        } else {
            // Found in EntityPlayer#onLivingUpdate (jumpMovementFactor of EntityLivingBase)
            // Set moveSpeed depending on sprint status
            // This isn't completely accurate :)
            moveSpeed = input.isSprinting()
                    ? (float) (MojangCocaine.SPEED_AIR + (MojangCocaine.SPEED_AIR * 0.3D))
                    : MojangCocaine.SPEED_AIR;
        }

        // Set friction to moveSpeed temporarily
        // This is how our move flying handler will access moveSpeed
        iteration.setFriction(moveSpeed);

        // Run Entity#moveFlying
        iteration = iteration.getEmulator().getMoveFlyingHandler().handle(iteration);

        // Set friction back to the actual friction
        iteration.setFriction(friction);

        // Run Entity#moveEntity
        iteration = iteration.getEmulator().getMoveEntityHandler().handle(iteration);

        iteration.addPostAction(emulator -> {
            if (emulator.getMotion() == null) return;

            // gravity and friction shiz
            emulator.getMotion().subtractY(0.08D);

            emulator.getMotion().multiplyY(MojangCocaine.GRAVITY);
            emulator.getMotion().multiplyX(friction);
            emulator.getMotion().multiplyZ(friction);
        });

        return iteration;
    }

    private double getAiMoveSpeed(final boolean sprinting) {
        double aiMoveSpeed = 0.1F;

        if (sprinting) aiMoveSpeed += aiMoveSpeed * MojangCocaine.SPRINT_MULTIPLIER;

        // Speed & slowness potions aren't handled
        // You'll need to figure that out yourself :)

        return aiMoveSpeed;
    }
}
