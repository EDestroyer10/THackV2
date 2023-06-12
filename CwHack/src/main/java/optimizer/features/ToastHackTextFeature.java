package optimizer.features;

import optimizer.events.GUIRenderListener;
import optimizer.feature.Feature;
import net.minecraft.client.util.math.MatrixStack;

import static optimizer.Client.MC;

public class ToastHackTextFeature extends Feature implements GUIRenderListener
{

    public ToastHackTextFeature()
    {
        super("ToastHackText", "Toasthack");
    }

    @Override
    public void onEnable()
    {
        super.onEnable();
        eventManager.add(GUIRenderListener.class, this);
    }

    @Override
    public void onDisable()
    {
        super.onDisable();
        eventManager.remove(GUIRenderListener.class, this);
    }

    @Override
    public void onRenderGUI(GUIRenderListener.GUIRenderEvent event)
    {
        MatrixStack matrices = event.getMatrixStack();
        matrices.push();
        matrices.translate(2, 2, 2);
        MC.textRenderer.drawWithShadow(matrices, "ToastHack Ghost 2.0", 0, 0, 0x6FA8DC);
        matrices.pop();
    }
}