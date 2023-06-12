package ubl.nohurtcam;

import ubl.nohurtcam.events.UpdateListener;
import ubl.nohurtcam.utils.RotationUtils;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

public class RotationStep implements UpdateListener {
    private final ArrayList<Rotation> rotations = new ArrayList();
    private Runnable callback;

    public RotationStep() {
        NoHurtCam.CWHACK.getEventManager().add(UpdateListener.class, this, Integer.MAX_VALUE);
    }

    @Override
    public void onUpdate() {
        if (this.rotations.size() != 0) {
            RotationUtils.setRotation(this.rotations.get(this.rotations.size() - 1));
            this.rotations.remove(this.rotations.size() - 1);
            if (this.rotations.size() == 0) {
                this.callback.run();
            }
        }
    }

    public void stepToward(Vec3d pos, int steps, Runnable callback) {
        this.stepToward(RotationUtils.getNeededRotations(pos), steps, callback);
    }

    public void stepToward(Rotation rotation, int steps, Runnable callback) {
        this.rotations.clear();
        float yaw = rotation.getYaw();
        float pitch = rotation.getPitch();
        float stepYaw = (yaw - NoHurtCam.MC.player.getYaw()) / (float)steps;
        float stepPitch = (pitch - NoHurtCam.MC.player.getPitch()) / (float)steps;
        for (int i = 0; i < steps; ++i) {
            this.rotations.add(new Rotation(yaw, rotation.isIgnoreYaw(), pitch, rotation.isIgnorePitch()));
            yaw -= stepYaw;
            pitch -= stepPitch;
        }
        this.callback = callback;
    }
}