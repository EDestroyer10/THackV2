package ubl.nohurtcam.mixin;

import ubl.nohurtcam.event.EventManager;
import net.minecraft.client.render.chunk.ChunkOcclusionDataBuilder;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ubl.nohurtcam.events.SetOpaqueCubeListener;

@Mixin(ChunkOcclusionDataBuilder.class)
public class ChunkOcclusionDataBuilderMixin
{
	@Inject(at = {@At("HEAD")},
			method = {"markClosed(Lnet/minecraft/util/math/BlockPos;)V"},
			cancellable = true)
	private void onMarkClosed(BlockPos pos, CallbackInfo ci)
	{
		SetOpaqueCubeListener.SetOpaqueCubeEvent event = new SetOpaqueCubeListener.SetOpaqueCubeEvent();
		EventManager.fire(event);

		if(event.isCancelled())
			ci.cancel();
	}
}
