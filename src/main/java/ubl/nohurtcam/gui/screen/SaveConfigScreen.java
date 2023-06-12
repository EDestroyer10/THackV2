package ubl.nohurtcam.gui.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import ubl.nohurtcam.gui.Color;
import ubl.nohurtcam.text.VanillaTextRenderer;

import static ubl.nohurtcam.NoHurtCam.CWHACK;
import static ubl.nohurtcam.NoHurtCam.MC;

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
		ButtonWidget doneButton = ButtonWidget.builder(Text.of("Done"), b -> MC.setScreen(prevScreen)).dimensions(width / 2 - 100, height - 50, 200, 20).build();
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
	public void render(DrawContext context, int mouseX, int mouseY, float delta)
	{
		renderBackground(context);
		super.render(context, mouseX, mouseY, delta);
		Color color = new Color(128, 128, 128);
		VanillaTextRenderer.INSTANCE.render("Save config as ...", width / 2, height / 3 - 20, color);
	}
}
