package me.hydro.emulator.collision.impl;

import me.hydro.emulator.Emulator;
import me.hydro.emulator.collision.Block;
import me.hydro.emulator.collision.CollisionBlockState;

public class BlockSoulSand extends Block implements CollisionBlockState {

    @Override
    public void transform(Emulator emulator) {
        emulator.getMotion().setMotionX(emulator.getMotion().getMotionX() * 0.4D);
        emulator.getMotion().setMotionZ(emulator.getMotion().getMotionZ() * 0.4D);
    }
}
