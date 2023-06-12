package optimizer.gui.screen;

import optimizer.keybind.Keybind;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.ArrayList;

import static optimizer.Client.CWHACK;
import static optimizer.Client.MC;

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
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);

		int x = width / 3;
		int y = 50;
		ArrayList<Keybind> keybinds = CWHACK.getKeybindManager().getAllKeybinds();
		for (Keybind keybind : keybinds)
		{
			MC.textRenderer.draw(matrices, keybind.getName(), x, y, 0xffffff);
			y += 32;
		}
	}

}
