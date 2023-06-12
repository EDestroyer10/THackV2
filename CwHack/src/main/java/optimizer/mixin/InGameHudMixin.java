package optimizer.mixin;

import optimizer.event.EventManager;
import optimizer.mixinterface.IInGameHud;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import optimizer.events.GUIRenderListener;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper implements IInGameHud
{
	@Shadow
	protected abstract void renderHealthBar(MatrixStack matrices, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking);

//	@Inject(
//			at = {@At(value = "INVOKE",
//					target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableBlend()V",
//					ordinal = 4)},
//			method = {"render(Lnet/minecraft/client/util/math/MatrixStack;F)V"})
//	private void onRender(MatrixStack matrixStack, float partialTicks,
//	                      CallbackInfo ci)
//	{
//		if(Client.MC.options.debugEnabled)
//			return;
//
//		GUIRenderEvent event = new GUIRenderEvent(matrixStack, partialTicks);
//		EventManager.fire(event);
//	}

	@Inject(method = "render", at = @At("TAIL"))
	private void onRender(MatrixStack matrixStack, float partialTicks, CallbackInfo ci)
	{
		GUIRenderListener.GUIRenderEvent event = new GUIRenderListener.GUIRenderEvent(matrixStack, partialTicks);
		EventManager.fire(event);
	}

	@Override
	public void cwRenderHealthBar(MatrixStack matrices, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking)
	{
		renderHealthBar(matrices, player, x, y, lines, regeneratingHeartIndex, maxHealth, lastHealth, health, absorption, blinking);
	}
}