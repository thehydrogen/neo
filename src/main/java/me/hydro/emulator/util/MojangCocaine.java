package me.hydro.emulator.util;

/**
 * Here we house the various "magic values" that
 * Mojang uses.
 */
public interface MojangCocaine {

    float SPEED_AIR = 0.02F;
    float LAND_MOVEMENT_FACTOR_LEGACY = 0.16277136F;

    float UPWARDS_MOTION = 0.42F;

    double SPRINT_MULTIPLIER = 0.3D; // This has been truncated, find the real value :)
    double GRAVITY = 0.9800000190734863D;
    double RESET = 0.05D;
}
