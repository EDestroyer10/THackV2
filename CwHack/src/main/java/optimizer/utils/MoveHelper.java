package optimizer.utils;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import static optimizer.Client.MC;

public class MoveHelper {

    public static boolean hasMovement() {
        final Vec3d playerMovement = MC.player.getVelocity();
        return playerMovement.getX() != 0 || playerMovement.getY() != 0 || playerMovement.getZ() != 0;
    }

    public static double motionY(final double motionY) {
        final Vec3d vec3d = MC.player.getVelocity();
        MC.player.setVelocity(vec3d.x, motionY, vec3d.z);
        return motionY;
    }

    public static double motionYPlus(final double motionY) {
        final Vec3d vec3d = MC.player.getVelocity();
        MC.player.setVelocity(vec3d.x, vec3d.y + motionY, vec3d.z);
        return motionY;
    }

    public static double getDistanceToGround(Entity entity) {
        final double playerX = MC.player.getX();
        final int playerHeight = (int) Math.floor(MC.player.getY());
        final double playerZ = MC.player.getZ();

        for (int height = playerHeight; height > 0; height--) {
            final BlockPos checkPosition = new BlockPos((int) playerX, height, (int) playerZ);

            // Check if the block is solid
            if (!MC.world.isAir(checkPosition)) {
                return playerHeight - height;
            }
        }
        return 0;
    }
}
