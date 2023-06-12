package optimizer.feature;

import optimizer.Client;
import optimizer.event.EventManager;
import optimizer.gui.screen.FeatureSettingScreen;
import optimizer.setting.Setting;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public abstract class Feature
{
	private final String name;
	private final String description;
	private boolean enabled = false;
	protected EventManager eventManager = Client.CWHACK.getEventManager();

	private final HashMap<String, Setting<?>> settings;

	public Feature(String name, String description)
	{
		this.name = name;
		this.description = description;
		settings = new HashMap<>();
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		if (this.enabled != enabled)
		{
			this.enabled = enabled;
			if (enabled)
				onEnable();
			else
				onDisable();
		}
	}

	public String getName()
	{
		return name;
	}

	public String getDescription()
	{
		return description;
	}

	public Setting<?> getSetting(String name)
	{
		return settings.get(name);
	}

	public Set<String> getSettingNames()
	{
		return settings.keySet();
	}

	public Collection<Setting<?>> getSettings()
	{
		return settings.values();
	}

	public void toggle()
	{
		setEnabled(!enabled);
	}

	public FeatureSettingScreen getSettingScreen()
	{
		return new FeatureSettingScreen(Client.MC.currentScreen, this);
	}

	public void addSetting(Setting<?> setting)
	{
		settings.put(setting.getName().toLowerCase(), setting);
	}

	protected void onEnable()
	{

	}
	public void drawLine(MatrixStack String) {
	}
	protected void onDisable()
	{

	}

	public void onChangeSetting(Setting<?> setting)
	{

	}
}
