package ubl.nohurtcam.utils;

import ubl.nohurtcam.NoHurtCam;
import ubl.nohurtcam.Rotation;
import ubl.nohurtcam.RotationFaker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class RotationUtils {
    public static Vec3d getEyesPos() {
        return RotationUtils.getEyesPos((PlayerEntity) NoHurtCam.MC.player);
    }

    public static Vec3d getEyesPos(PlayerEntity player) {
        return RenderUtils.getCameraPos();
    }

    public static BlockPos getEyesBlockPos() {
        return new BlockPos((Vec3i)RotationUtils.getEyesBlockPos());
    }

    public static Vec3d getPlayerLookVec(PlayerEntity player) {
        float f = (float)Math.PI / 180;
        float pi = (float)Math.PI;
        float f1 = MathHelper.cos((float)(-player.getYaw() * f - pi));
        float f2 = MathHelper.sin((float)(-player.getYaw() * f - pi));
        float f3 = -MathHelper.cos((float)(-player.getPitch() * f));
        float f4 = MathHelper.sin((float)(-player.getPitch() * f));
        return new Vec3d((double)(f2 * f3), (double)f4, (double)(f1 * f3)).normalize();
    }

    public static Vec3d getCwHackLookVec() {
        return RotationUtils.getPlayerLookVec((PlayerEntity) NoHurtCam.MC.player);
    }

    public static Rotation getNeededRotations(Vec3d from, Vec3d vec) {
        Vec3d eyesPos = from;
        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new Rotation(yaw, pitch);
    }

    public static Rotation getNeededRotations(Vec3d vec) {
        Vec3d eyesPos = RotationUtils.getEyesPos();
        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new Rotation(yaw, pitch);
    }

    public static double getAngleToLookVec(Vec3d vec) {
        return RotationUtils.getAngleToLookVec((PlayerEntity) NoHurtCam.MC.player, vec);
    }

    public static double getAngleToLookVec(PlayerEntity player, Vec3d vec) {
        Rotation needed = RotationUtils.getNeededRotations(vec);
        float currentYaw = MathHelper.wrapDegrees((float)player.getYaw());
        float currentPitch = MathHelper.wrapDegrees((float)player.getPitch());
        float diffYaw = currentYaw - needed.getYaw();
        float diffPitch = currentPitch - needed.getPitch();
        return Math.sqrt(diffYaw * diffYaw + diffPitch * diffPitch);
    }

    public static void setRotation(Rotation rotation) {
        if (!rotation.isIgnoreYaw()) {
            NoHurtCam.MC.player.setYaw(rotation.getYaw());
        }
        if (!rotation.isIgnorePitch()) {
            NoHurtCam.MC.player.setPitch(rotation.getPitch());
        }
    }
    public static Vec3d getServerLookVec()
    {
        RotationFaker rotationFaker = NoHurtCam.CWHACK.getRotationFaker();
        float serverYaw = rotationFaker.getServerYaw();
        float serverPitch = rotationFaker.getServerPitch();

        float f = MathHelper.cos(-serverYaw * 0.017453292F - (float)Math.PI);
        float f1 = MathHelper.sin(-serverYaw * 0.017453292F - (float)Math.PI);
        float f2 = -MathHelper.cos(-serverPitch * 0.017453292F);
        float f3 = MathHelper.sin(-serverPitch * 0.017453292F);
        return new Vec3d(f1 * f2, f3, f * f2).normalize();
    }

    public static void lookAt(Vec3d pos) {
        Rotation rot = RotationUtils.getNeededRotations(pos);
        RotationUtils.setRotation(rot);
    }
}