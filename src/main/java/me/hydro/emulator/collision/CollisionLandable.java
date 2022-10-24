package me.hydro.emulator.collision;

import me.hydro.emulator.Emulator;
import me.hydro.emulator.object.iteration.IterationHolder;

public interface CollisionLandable {

    void onLand(final Emulator iteration);
}
