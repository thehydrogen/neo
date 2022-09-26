package me.hydro.emulator.object.iteration;

import lombok.Data;
import me.hydro.emulator.Emulator;
import me.hydro.emulator.object.input.DataSupplier;
import me.hydro.emulator.object.input.IterationInput;
import me.hydro.emulator.util.Vector;

import java.util.ArrayList;
import java.util.List;

@Data
public class IterationHolder {

    private Motion motion;

    private final Emulator emulator;

    private final IterationInput input;
    private final DataSupplier dataSupplier;

    private List<String> tags = new ArrayList<>();

    private float friction;

    private double offset;
    private Vector predicted;
}
