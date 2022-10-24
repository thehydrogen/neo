package me.hydro.emulator.object.result;

import lombok.Data;
import me.hydro.emulator.object.iteration.IterationHolder;
import me.hydro.emulator.object.iteration.Motion;
import me.hydro.emulator.util.Vector;

import java.util.List;

@Data
public class IterationResult {

    private final double offset;

    private final IterationHolder iteration;

    private final Vector predicted;
    private final Motion motion;

    private final List<String> tags;
}
