package ubl.nohurtcam;

import ubl.nohurtcam.utils.BlockUtils;
import ubl.nohurtcam.utils.RotationUtils;
import net.minecraft.block.BlockState;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;

import static ubl.nohurtcam.NoHurtCam.CWHACK;
import static ubl.nohurtcam.NoHurtCam.MC;

public enum BlockBreaker
{
	;

	public static boolean breakOneBlock(BlockPos pos)
	{
		Direction side = null;
		Direction[] sides = Direction.values();

		BlockState state = BlockUtils.getBlockState(pos);
		VoxelShape shape = state.getOutlineShape(MC.world, pos);
		if(shape.isEmpty())
			return false;

		Vec3d eyesPos = RotationUtils.getEyesPos();
		Vec3d relCenter = shape.getBoundingBox().getCenter();
		Vec3d center = Vec3d.of(pos).add(relCenter);

		Vec3d[] hitVecs = new Vec3d[sides.length];
		for(int i = 0; i < sides.length; i++)
		{
			Vec3i dirVec = sides[i].getVector();
			Vec3d relHitVec = new Vec3d(relCenter.x * dirVec.getX(),
					relCenter.y * dirVec.getY(), relCenter.z * dirVec.getZ());
			hitVecs[i] = center.add(relHitVec);
		}

		double distanceSqToCenter = eyesPos.squaredDistanceTo(center);
		for(int i = 0; i < sides.length; i++)
		{
			// check if side is facing towards player
			if(eyesPos.squaredDistanceTo(hitVecs[i]) >= distanceSqToCenter)
				continue;
			side = sides[i];
			break;
		}

		// player is inside of block, side doesn't matter
		if(side == null)
			side = sides[0];

		// face block
		CWHACK.getRotationFaker().setServerLookPos(hitVecs[side.ordinal()]);

		// damage block
		if(!MC.interactionManager.updateBlockBreakingProgress(pos, side))
			return false;

		// swing arm
		MC.player.swingHand(Hand.MAIN_HAND);

		return true;
	}
}
