package ubl.nohurtcam.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ubl.nohurtcam.BlurConfig;
import ubl.nohurtcam.NoHurtCamInitializer;

@Mixin(InGameHud.class)
public class MixinInGameHud {
    @Shadow private int scaledWidth;
    @Shadow private int scaledHeight;
    @Final @Shadow private MinecraftClient client;
    @Inject(at = @At("TAIL"), method = "render")
    public void blur$onRender(DrawContext context, float tickDelta, CallbackInfo ci) {
        if (client.currentScreen == null && client.world != null && NoHurtCamInitializer.start > 0 && !BlurConfig.blurExclusions.contains(NoHurtCamInitializer.prevScreen) && NoHurtCamInitializer.screenHasBackground) {
            context.fillGradient(0, 0, this.scaledWidth, this.scaledHeight, NoHurtCamInitializer.getBackgroundColor(false, false), NoHurtCamInitializer.getBackgroundColor(true, false));
        }
    }
}