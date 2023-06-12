package ubl.nohurtcam.mixin.sodium;

import ubl.nohurtcam.features.XRayFeature;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static ubl.nohurtcam.NoHurtCam.CWHACK;

@Pseudo
@Mixin(targets = "me.jellysquid.mods.sodium.client.render.occlusion.BlockOcclusionCache", remap = false)
public class SodiumBlockOcclusionCacheMixin {

    @Inject(method = "shouldDrawSide", at = @At("RETURN"), cancellable = true)
    private void onShouldDrawSide(BlockState state, BlockView view, BlockPos pos, Direction facing, CallbackInfoReturnable<Boolean> cir) {
        XRayFeature xRayFeature = CWHACK.getFeatures().xRayFeature;

        if (xRayFeature.isEnabled()) {
            boolean isXRay = xRayFeature.isXRayBlock(state.getBlock().getTranslationKey());
            cir.setReturnValue(isXRay);
        }
    }

}