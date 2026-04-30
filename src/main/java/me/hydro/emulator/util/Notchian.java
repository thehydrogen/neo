package me.hydro.emulator.util;

/**
 * This interface houses several constants used by the Notchian client to determine
 * its movement. Minecraft makes heavy and widespread use of double-float conversions,
 * some of which have not been preserved here. Refer to MCP wherever there is a comment
 * to find the true values. Otherwise, you will notice slight offsets with your
 * emulated movement.
 */
public interface Notchian {

    float SPEED_AIR = 0.02F;
    float LAND_MOVEMENT_FACTOR_LEGACY = 0.16277136F;

    float UPWARDS_MOTION = 0.42F;

    double SPRINT_MULTIPLIER = 0.3D; // This has been truncated, find the real value :)

    double GRAVITY = 0.08D;
    double DRAG = 0.9800000190734863D;

    // This value is different on modern versions and replaced with 0.003.
    double RESET = 0.005D;
}
