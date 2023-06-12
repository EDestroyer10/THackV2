package optimizer.gui.screen;

import optimizer.setting.Setting;
import optimizer.utils.ChatUtils;
import optimizer.feature.Feature;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.Comparator;
import java.util.TreeMap;

import static optimizer.Client.MC;

public class FeatureSettingScreen extends Screen
{

	private final Screen prevScreen;
	private final Feature feature;

	private final TreeMap<Setting, TextFieldWidget> settings2widgets = new TreeMap<>(Comparator.comparing(Setting::getName));

	public FeatureSettingScreen(Screen prevScreen, Feature feature)
	{
		super(Text.of(""));
		this.prevScreen = prevScreen;
		this.feature = feature;
	}
	@Override
	protected void init()
	{
		ButtonWidget toggleButton = ButtonWidget.builder(Text.of("Toggle"), b -> feature.toggle()).dimensions(width / 2 - 100, height - 50, 200, 20).build();
		addDrawableChild(toggleButton);
		ButtonWidget doneButton = ButtonWidget.builder(Text.of("Done"), b -> done()).dimensions(width / 2 - 100, height - 30, 200, 20).build();
		addDrawableChild(doneButton);
		int x = width / 3 * 2;
		var ref = new Object()
		{
			int y = 50;
		};
		feature.getSettings().forEach(s ->
		{
			TextFieldWidget widget = new TextFieldWidget(MC.textRenderer, x, ref.y, 200, 20, Text.of(""));
			widget.setText(s.storeAsString());
			settings2widgets.put(s, widget);
			addSelectableChild(widget);
			ref.y += 32;
		});
	}

	private void done() {
		settings2widgets.forEach((s, w) -> {
			try {
				s.loadFromString(w.getText());
			} catch (Exception ignored) {
				ChatUtils.error("Failed to set " + s.getName() + " to " + w.getText());
			}
		});
		MC.setScreen(prevScreen);
	}

	@Override
	public void tick()
	{
		settings2widgets.forEach((s, w) -> w.tick());
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		drawCenteredTextWithShadow(matrices, MC.textRenderer, feature.getName(), width / 2, 20, 0xffffff);
		settings2widgets.forEach((s, w) ->
		{
			MC.textRenderer.draw(matrices, s.getName(), width / 3, w.getY(), 0xffffff);
			w.render(matrices, mouseX, mouseY, delta);
		});
	}
}
