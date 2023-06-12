package ubl.nohurtcam.mixin;

import ubl.nohurtcam.mixinterface.ITextRenderer;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(TextRenderer.class)
public class TextRendererMixin implements ITextRenderer {
    @Shadow
    public List<OrderedText> wrapLines(StringVisitable text, int width) {
        return null;
    }

    private void draw(OrderedText text, float x, float y, int color, Matrix4f matrix, boolean shadow) {
    }

    @Override
    public void drawTrimmed(StringVisitable text, float x, float y, int maxWidth, int color, Matrix4f matrix) {
        for (OrderedText orderedText : this.wrapLines(text, maxWidth)) {
            this.draw(orderedText, x, y, color, matrix, false);
            y += 9.0f;
        }
    }
}