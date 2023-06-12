package optimizer.features;

import com.mojang.blaze3d.systems.RenderSystem;
import optimizer.Client;
import optimizer.events.GUIRenderListener;
import optimizer.feature.Feature;
import optimizer.setting.DecimalSetting;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;

public class FeatureListFeature extends Feature implements GUIRenderListener
{
	private final DecimalSetting scale = new DecimalSetting("scale", "scale of the text", 2.0, this);

	private int textColor = 0x049933ff;
	private int posY;

	public FeatureListFeature()
	{
		super("FeatureList", "display a list of enabled feature on the screen");
	}

	@Override
	protected void onEnable()
	{
		eventManager.add(GUIRenderListener.class, this);
	}

	@Override
	protected void onDisable()
	{
		eventManager.remove(GUIRenderListener.class, this);
	}

	@Override
	public void onRenderGUI(GUIRenderEvent event)
	{
		MatrixStack matrices = event.getMatrixStack();

		float scaleV = scale.getValueF();
		matrices.push();
		matrices.scale(scaleV, scaleV, 1);

		posY = 2;
		for (String s : Client.CWHACK.getFeatures().getAllFeatureNames())
		{
			Feature feature = Client.CWHACK.getFeatures().getFeature(s);
			if (feature.isEnabled())
				drawLine(event.getMatrixStack(), feature.getName());
		}
		matrices.pop();
		RenderSystem.applyModelViewMatrix();
	}

	private void drawLine(MatrixStack matrixStack, String line)
	{
		TextRenderer textRenderer = Client.MC.textRenderer;

		int posX = 2;

		textRenderer.draw(matrixStack, line, posX + 1, posY + 1, 0xff000000);
		textRenderer.draw(matrixStack, line, posX, posY, textColor | 0xff000000);

		posY += 9;
	}
}
