package ubl.nohurtcam.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ubl.nohurtcam.NoHurtCamInitializer;

@Mixin(Screen.class)
public abstract class MixinScreen {

    @Shadow @Nullable protected MinecraftClient client;

    @Shadow @Final protected Text title;
    private final Text blurConfig = Text.translatable("blur.midnightconfig.title");

    @Inject(at = @At("HEAD"), method = "tick")
    private void blur$reloadShader(CallbackInfo ci) {
        if (this.client != null && this.title.equals(blurConfig)) {
            NoHurtCamInitializer.onScreenChange(this.client.currentScreen);
        }
    }
    @Inject(at = @At("HEAD"), method = "renderBackground")
    public void blur$getBackgroundEnabled(DrawContext context, CallbackInfo ci) {
        if (this.client != null && this.client.world != null) {
            NoHurtCamInitializer.screenHasBackground = true;
        }
    }

    @ModifyConstant(
            method = "renderBackground",
            constant = @Constant(intValue = -1072689136))
    private int blur$getFirstBackgroundColor(int color) {
        return NoHurtCamInitializer.getBackgroundColor(false, true);
    }

    @ModifyConstant(
            method = "renderBackground",
            constant = @Constant(intValue = -804253680))
    private int blur$getSecondBackgroundColor(int color) {
        return NoHurtCamInitializer.getBackgroundColor(true, true);
    }
}