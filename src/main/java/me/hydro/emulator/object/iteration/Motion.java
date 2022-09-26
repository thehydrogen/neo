package me.hydro.emulator.object.iteration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Motion implements Cloneable {

    private double motionX, motionY, motionZ;
    private float forward, strafing;

    public double multiplyForward(double x) {
        forward *= x;
        return forward;
    }

    public double multiplyStrafing(double z) {
        strafing *= z;
        return strafing;
    }

    public double multiplyX(double x) {
        motionX *= x;
        return motionX;
    }

    public double multiplyY(double y) {
        motionY *= y;
        return motionY;
    }

    public double multiplyZ(double z) {
        motionZ *= z;
        return motionZ;
    }

    public double divideX(double x) {
        motionX /= x;
        return motionX;
    }

    public double divideY(double y) {
        motionY /= y;
        return motionY;
    }

    public double divideZ(double z) {
        motionZ /= z;
        return motionZ;
    }

    public double addX(double x) {
        motionX += x;
        return motionX;
    }

    public double addY(double y) {
        motionY += y;
        return motionY;
    }

    public double addZ(double z) {
        motionZ += z;
        return motionZ;
    }

    public double subtractX(double x) {
        motionX -= x;
        return motionX;
    }

    public double subtractY(double y) {
        motionY -= y;
        return motionY;
    }

    public double subtractZ(double z) {
        motionZ -= z;
        return motionZ;
    }

    @Override
    public Motion clone() {
        return new Motion(motionX, motionY, motionZ, forward, strafing);
    }
}
