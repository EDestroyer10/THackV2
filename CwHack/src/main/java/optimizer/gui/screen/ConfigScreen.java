package optimizer.gui.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

import static optimizer.Client.CWHACK;
import static optimizer.Client.MC;

public class ConfigScreen extends Screen
{

	private final Screen prevScreen;
	private final File[] configList;
	private final ArrayList<ButtonWidget> loadButtons = new ArrayList<>();

	public ConfigScreen(Screen prevScreen)
	{
		super(Text.literal(""));
		this.prevScreen = prevScreen;
		Path configDir = CWHACK.getCwHackDirectory().resolve("config");
		configList = new File(String.valueOf(configDir)).listFiles();
	}

	@Override
	protected void init()
	{
		ButtonWidget doneButton = ButtonWidget.builder(Text.of("Done"), b -> MC.setScreen(prevScreen)).dimensions(width / 2 - 100, height - 50, 200, 20).build();addDrawableChild(doneButton);
		ButtonWidget saveButton = ButtonWidget.builder(Text.of("Done"), b -> MC.setScreen(prevScreen)).dimensions(width / 2 - 100, height - 80, 200, 20).build();addDrawableChild(saveButton);
		int y = 50;
		for (File f : configList)
		{
			ButtonWidget button = ButtonWidget.builder(Text.of("Load"), new ButtonWidget.PressAction() {
						private final String path = f.getAbsolutePath();
						@Override
						public void onPress(ButtonWidget button) {
							CWHACK.getFeatures().loadFromFile(path);
						}
					}).dimensions(width / 3 * 2, y, 100, 20)
					.build();
		}
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		renderBackground(matrices);

		super.render(matrices, mouseX, mouseY, delta);

		int x = width / 3;
		int y = 50;
		for (File file : configList)
		{
			MC.textRenderer.draw(matrices, file.getName(), x, y, 0xffffff);
			y += 32;
		}
	}
}
