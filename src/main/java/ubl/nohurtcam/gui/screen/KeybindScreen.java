package ubl.nohurtcam.gui.screen;

import net.minecraft.client.gui.DrawContext;
import ubl.nohurtcam.gui.Color;
import ubl.nohurtcam.keybind.Keybind;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import ubl.nohurtcam.text.VanillaTextRenderer;

import java.util.ArrayList;

import static ubl.nohurtcam.NoHurtCam.CWHACK;
import static ubl.nohurtcam.NoHurtCam.MC;

public class KeybindScreen extends Screen
{

	private final Screen prevScreen;
	private final ArrayList<ButtonWidget> editButtons = new ArrayList<>();

	public KeybindScreen(Screen prevScreen)
	{
		super(Text.of(""));
		this.prevScreen = prevScreen;
	}

	@Override
	protected void init()
	{
		ButtonWidget doneButton = ButtonWidget.builder(Text.of("Done"), b -> MC.setScreen(prevScreen)).dimensions(width / 2 - 100, height - 50, 200, 20).build();		addDrawableChild(doneButton);
		addDrawableChild(doneButton);
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta)
	{
		renderBackground(context);
		super.render(context, mouseX, mouseY, delta);

		int x = width / 3;
		int y = 50;
		ArrayList<Keybind> keybinds = CWHACK.getKeybindManager().getAllKeybinds();
		for (Keybind keybind : keybinds)
		{
			Color color = new Color(128, 128, 128);
			VanillaTextRenderer.INSTANCE.render(keybind.getName(), x, y, color);
			y += 32;
		}
	}

}
