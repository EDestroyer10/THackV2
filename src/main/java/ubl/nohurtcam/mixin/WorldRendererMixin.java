package ubl.nohurtcam.mixin;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static ubl.nohurtcam.NoHurtCam.CWHACK;

@Mixin(GameRenderer.class)
public class WorldRendererMixin {
    @Inject(method = "bobView", at = @At("HEAD"), cancellable = true)
    public void disableHurtCam(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (!CWHACK.getFeatures().noHurtCamFeature.isEnabled()) {
            ci.cancel();
        }
    }
    @Inject(method = "renderWorld", at = @At(value = "INVOKE_STRING", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V", args = { "ldc=hand" }), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void onRenderWorld(float tickDelta, long limitTime, MatrixStack matrices, CallbackInfo info) {
        }
    }