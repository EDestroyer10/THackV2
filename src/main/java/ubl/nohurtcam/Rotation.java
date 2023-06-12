package ubl.nohurtcam;

public class Rotation {
    private final float yaw;
    private final float pitch;
    private final boolean ignoreYaw;
    private final boolean ignorePitch;

    public Rotation(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.ignoreYaw = false;
        this.ignorePitch = false;
    }

    public Rotation(float yaw, boolean ignoreYaw, float pitch, boolean ignorePitch) {
        this.yaw = yaw;
        this.ignoreYaw = ignoreYaw;
        this.pitch = pitch;
        this.ignorePitch = ignorePitch;
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public boolean isIgnoreYaw() {
        return this.ignoreYaw;
    }

    public boolean isIgnorePitch() {
        return this.ignorePitch;
    }
}