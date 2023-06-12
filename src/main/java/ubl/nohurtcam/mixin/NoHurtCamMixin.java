package ubl.nohurtcam.mixin;

import ubl.nohurtcam.NoHurtCamInitializer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class NoHurtCamMixin {
    @Inject(at = @At("HEAD"), method = "bobView(Lnet/minecraft/client/util/math/MatrixStack;F)V", cancellable = true)
    public void bobViewWhenHurt(MatrixStack matrixStack_1, float float_1, CallbackInfo ci) {
        if (NoHurtCamInitializer.toggledOn) ci.cancel();
    }
}
