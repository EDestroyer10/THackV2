package ubl.nohurtcam.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.AbstractBlock;

import static ubl.nohurtcam.NoHurtCam.CWHACK;

@Mixin(AbstractBlock.AbstractBlockState.class)
public class XRayLighting {

    @Inject(method = "getLuminance", at = @At("HEAD"), cancellable = true)
    public void getLuminance(CallbackInfoReturnable<Integer> cir) {
        if (CWHACK.getFeatures().xRayFeature.isEnabled()) {
            cir.setReturnValue(15);
        }
    }
}