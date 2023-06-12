package ubl.nohurtcam.mixinterface;

import net.minecraft.text.StringVisitable;
import org.joml.Matrix4f;

public interface ITextRenderer {
    public void drawTrimmed(StringVisitable var1, float var2, float var3, int var4, int var5, Matrix4f var6);
}