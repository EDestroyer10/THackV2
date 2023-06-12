package ubl.nohurtcam.mixin;

import ubl.nohurtcam.feature.Feature;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static ubl.nohurtcam.NoHurtCam.CWHACK;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {

    @Inject(method = "tiltViewWhenHurt", at = @At("HEAD"), cancellable = true)
    public void onHurtViewTilt(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        final Feature noHurtCamFeature = CWHACK.getFeatures().noHurtCamFeature;

        if (noHurtCamFeature.isEnabled()) ci.cancel();
    }

    @Inject(method = "bobView", at = @At("HEAD"), cancellable = true)
    public void onViewBob(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        final Feature noViewBobFeature = CWHACK.getFeatures().noHurtCamFeature;

        if (noViewBobFeature.isEnabled()) ci.cancel();
    }
}