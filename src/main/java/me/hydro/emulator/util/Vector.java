package me.hydro.emulator.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Vector {

    private double x, y, z;

    public double distance(final Vector other) {
        final double deltaX = other.getX() - x;
        final double deltaY = other.getY() - y;
        final double deltaZ = other.getZ() - z;

        return Math.sqrt((deltaX * deltaX) + (deltaY * deltaY) + (deltaZ * deltaZ));
    }
}
