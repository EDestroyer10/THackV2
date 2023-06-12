package ubl.nohurtcam.mixin;

import org.objectweb.asm.Opcodes;
import ubl.nohurtcam.NoHurtCamInitializer;
import ubl.nohurtcam.event.EventManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.RunArgs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ubl.nohurtcam.events.FrameBeginListener;
import ubl.nohurtcam.events.GameLeaveListener;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin
{
	@Inject(method = "setScreen",
			at = @At(value = "FIELD",
					target = "Lnet/minecraft/client/MinecraftClient;currentScreen:Lnet/minecraft/client/gui/screen/Screen;",
					opcode = Opcodes.PUTFIELD))
	private void onScreenOpen(Screen newScreen, CallbackInfo info) {
		NoHurtCamInitializer.onScreenChange(newScreen);
	}
	@Shadow
	public ClientWorld world;

	@Inject(method = "render(Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Mouse;updateMouse()V", shift = At.Shift.AFTER))
	private void onRender(boolean tick, CallbackInfo info)
	{
		EventManager.fire(new FrameBeginListener.FrameBeginEvent());
	}

	@Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;setOverlay(Lnet/minecraft/client/gui/screen/Overlay;)V", shift = At.Shift.BEFORE))
	private void init(RunArgs args, CallbackInfo ci) {
		NoHurtCamInitializer.init();
	}
}
