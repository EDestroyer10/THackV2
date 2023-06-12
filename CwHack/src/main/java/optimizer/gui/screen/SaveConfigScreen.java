package optimizer.gui.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import static optimizer.Client.CWHACK;
import static optimizer.Client.MC;

public class SaveConfigScreen extends Screen
{
	private ButtonWidget doneButton;

	private final Screen prevScreen;
	private TextFieldWidget textWidget;

	public SaveConfigScreen(Screen prevScreen)
	{
		super(Text.of(""));
		this.prevScreen = prevScreen;
	}

	@Override
	protected void init()
	{
		ButtonWidget doneButton = ButtonWidget.builder(Text.of("Done"), b -> MC.setScreen(prevScreen)).dimensions(width / 2 - 100, height - 50, 200, 20).build();		addDrawableChild(doneButton);
		addDrawableChild(doneButton);
		textWidget = new TextFieldWidget(MC.textRenderer, width / 2 - 200, height / 3, 400, 20, Text.of(""));
		addSelectableChild(textWidget);
		setInitialFocus(textWidget);
	}

	private void done()
	{
		CWHACK.getFeatures().saveAsFile(CWHACK.getConfigDirectory().resolve(textWidget.getText()).toString() + ".cw");
		MC.setScreen(prevScreen);
	}

	@Override
	public void tick()
	{
		textWidget.tick();
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		drawCenteredTextWithShadow(matrices, MC.textRenderer, "Save config as ...", width / 2, height / 3 - 20, 0xffffff);
	}
}
