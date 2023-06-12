package optimizer.mixin;

import optimizer.events.AttackEntityListener;
import optimizer.events.StopUsingItemListener;
import optimizer.mixinterface.IClientPlayerInteractionManager;
import optimizer.utils.MixinUtils;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin implements IClientPlayerInteractionManager
{

	@Override
	public void cwSyncSelectedSlot()
	{
		syncSelectedSlot();
	}

	@Shadow
	private boolean breakingBlock;

	@Override
	public void setBreakingBlock(boolean breakingBlock)
	{
		this.breakingBlock = breakingBlock;
	}

	@Shadow
	private BlockPos currentBreakingPos;

	@Override
	public void setCurrentBreakingPos(BlockPos pos)
	{
		currentBreakingPos = pos;
	}

	@Shadow
	private void syncSelectedSlot()
	{

	}

	@Inject(method = "stopUsingItem(Lnet/minecraft/entity/player/PlayerEntity;)V", at = @At("HEAD"), cancellable = true)
	private void onStopUsingItem(CallbackInfo ci)
	{
		MixinUtils.fireCancellableEvent(new StopUsingItemListener.StopUsingItemEvent(), ci);
	}

	@Inject(method = "attackEntity(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/Entity;)V", at = @At("HEAD"), cancellable = true)
	private void onAttackEntity(PlayerEntity player, Entity target, CallbackInfo ci)
	{
		MixinUtils.fireCancellableEvent(new AttackEntityListener.AttackEntityEvent(player, target), ci);
	}
}
