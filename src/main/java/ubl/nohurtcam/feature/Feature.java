package ubl.nohurtcam.feature;

import ubl.nohurtcam.NoHurtCam;
import ubl.nohurtcam.event.EventManager;
import ubl.nohurtcam.gui.screen.FeatureSettingScreen;
import ubl.nohurtcam.setting.Setting;
import ubl.nohurtcam.utils.NotificationUtils;
import ubl.nohurtcam.utils.RenderUtils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.Packet;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import static ubl.nohurtcam.NoHurtCam.MC;

public abstract class Feature
{
	private final HashMap<String, Setting<?>> settings;

	private final String name;
	private final String description;
	private int key;
	private RenderUtils renderUtils;

	public void onToggle() {

	}

	private boolean state;

	public void onReceivePacket(Packet<?> packet) {

	}

	public boolean getState() {
		return this.state;
	}

	public void onSendPacket(Packet<?> packet) {

	}
	public void onWorldRender(MatrixStack matrices) {
	}

	private boolean enabled = false;
	protected EventManager eventManager = NoHurtCam.CWHACK.getEventManager();

	public Feature(String name, String description)
	{
		this.name = name;
		this.description = description;
		settings = new HashMap<>();
	}

	public void setState(boolean state) {
		this.onToggle();
		if(this.state = state) return;
		if (state) {
			this.onEnable();
			this.state = true;
		} else {
			this.onDisable();
			this.state = false;
		}
	}
	public RenderUtils getRenderUtils() {
		return this.renderUtils;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		if (this.enabled == enabled)
			return;

		this.enabled = enabled;

		if (enabled)
		{
			NotificationUtils.notify(name + " has been enabled");
			onEnable();
		}
		else
		{
			NotificationUtils.notify(name + " has been disabled");
			onDisable();
		}
	}
	public void setKey(int key) {
		this.key = key;
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
		return new FeatureSettingScreen(MC.currentScreen, this);
	}

	public void addSetting(Setting<?> setting)
	{
		settings.put(setting.getName().toLowerCase(), setting);
	}

	protected void onEnable()
	{

	}

	protected void onDisable()
	{

	}

	public void onChangeSetting(Setting<?> setting)
	{

	}
}
