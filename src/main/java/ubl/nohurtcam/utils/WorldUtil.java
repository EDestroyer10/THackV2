package ubl.nohurtcam.utils;

import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.chunk.WorldChunk;

import java.util.ArrayList;
import java.util.List;

import static ubl.nohurtcam.utils.BlockUtils.getBlock;
import static ubl.nohurtcam.utils.BlockUtils.getBlockState;


public class WorldUtil {

    public static List<WorldChunk> getLoadedChunks() {
        List<WorldChunk> chunks = new ArrayList<>();
        int viewDist = 20;

        for (int x = -viewDist; x <= viewDist; x++) {
            for (int z = -viewDist; z <= viewDist; z++) {
                WorldChunk chunk = MinecraftClient.getInstance().world.getChunkManager().getWorldChunk((int) MinecraftClient.getInstance().player.getX() / 16 + x, (int) MinecraftClient.getInstance().player.getZ() / 16 + z);
                if (chunk != null) chunks.add(chunk);
            }
        }
        return chunks;
    }

    public Direction chestMergeDirection(ChestBlockEntity chestBlockEntity) {
        BlockState blockState = getBlockState(chestBlockEntity.getPos());
        ChestBlock chestBlock = (ChestBlock) getBlock(chestBlockEntity.getPos());
        Box chestBox = chestBlock.getOutlineShape(blockState, MinecraftClient.getInstance().world, chestBlockEntity.getPos(), ShapeContext.absent()).getBoundingBox();
        if (chestBox.minZ == 0)
            return Direction.NORTH;
        if (chestBox.maxZ == 1)
            return Direction.SOUTH;
        if (chestBox.maxX == 1)
            return Direction.EAST;
        if (chestBox.minX == 0)
            return Direction.WEST;
        return Direction.UP;
    }

    public static List<BlockEntity> getBlockEntities() {
        List<BlockEntity> list = new ArrayList<>();
        getLoadedChunks().forEach(c -> list.addAll(c.getBlockEntities().values()));
        return list;
    }
    public static Iterable<BlockEntity> blockEntities() {
        return BlockEntityIterator::new;
    }

}
