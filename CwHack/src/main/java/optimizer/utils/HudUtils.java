package optimizer.utils;

import optimizer.Client;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.List;

public enum HudUtils {;
    private HudUtils() {
    }

    public static boolean canPlaceCrystalServer(BlockPos block) {
        BlockState blockState = Client.MC.world.getBlockState(block);
        if (!blockState.isOf(Blocks.OBSIDIAN) && !blockState.isOf(Blocks.BEDROCK)) {
            return false;
        } else {
            BlockPos blockPos2 = block.up();
            if (!Client.MC.world.isAir(blockPos2)) {
                return false;
            } else {
                double d = (double) blockPos2.getX();
                double e = (double) blockPos2.getY();
                double f = (double) blockPos2.getZ();
                List<Entity> list = Client.MC.world.getOtherEntities((Entity) null, new Box(d, e, f, d + 1.0, e + 2.0, f + 1.0));
                list.removeIf((entity) -> {
                    return !(entity instanceof EndCrystalEntity) ? false : Client.CWHACK.crystalDataTracker().isCrystalAttacked(entity);
                });
                return list.isEmpty();
            }
        }
    }
}