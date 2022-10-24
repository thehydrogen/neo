package com.example;

import me.hydro.emulator.Emulator;
import me.hydro.emulator.collision.Block;
import me.hydro.emulator.object.input.DataSupplier;
import me.hydro.emulator.object.input.IterationInput;
import me.hydro.emulator.object.result.IterationResult;
import me.hydro.emulator.util.Vector;
import me.hydro.emulator.util.mcp.AxisAlignedBB;
import me.hydro.emulator.util.mcp.BlockPos;

import java.util.Collections;
import java.util.List;

public class ExamplePredictions {

    // This emulator class is to be created per-player
    private final Emulator emulator = new Emulator(new DataSupplier() {

        @Override
        public List<AxisAlignedBB> getCollidingBoxes(AxisAlignedBB bb) {
            return Collections.emptyList();
        }

        @Override
        public Block getBlockAt(BlockPos blockPos) {
            return null;
        }
    });

    public void runEmulation() {
        // Here we'll build the iteration input object we'll feed into the emulator
        final IterationInput input = IterationInput.builder()
                .to(new Vector(1, 2, 3)) // location from the flying packet
                .yaw(5F) // current yaw
                .ground(false)
                .jumping(false) // you'll want to bruteforce this
                .forward(0) // you'll want to bruteforce this
                .strafing(0) // you'll want to bruteforce this
                .sprinting(false) // you'll want to bruteforce this
                .usingItem(false) // you'll want to bruteforce this
                .hitSlowdown(false) // you'll want to bruteforce this
                .sneaking(false)
                .lastReportedBoundingBox(new AxisAlignedBB(0, 0, 0, 0, 0, 0)) // from location, as a bounding box
                .build();

        // Run the emulation and get the result
        final IterationResult result = emulator.runIteration(input);

        // Once we've found our best candidate (in the case of a bruteforce),
        // confirm it to run post actions.
        emulator.confirm(result.getIteration());
    }
}
