package optimizer.setting;

import optimizer.feature.Feature;

public class BooleanSetting extends Setting<Boolean>
{
	private boolean enabled;
	private boolean value;

	public BooleanSetting(String name, String description, boolean value, Feature feature)
	{
		super(name, description, feature);
		this.value = value;
	}

	@Override
	public Boolean getValue()
	{
		return value;
	}

	@Override
	public void loadFromStringInternal(String string)
	{
		value = Boolean.parseBoolean(string);
	}

	@Override
	public String storeAsString()
	{
		return Boolean.toString(value);
	}

	public boolean isEnabled() {
		return enabled;
	}
}
