package ubl.nohurtcam.mixinterface;

import net.minecraft.world.chunk.BlockEntityTickInvoker;

import java.util.List;

public interface IWorld
{
	public List<BlockEntityTickInvoker> getBlockEntityTickers();
}
