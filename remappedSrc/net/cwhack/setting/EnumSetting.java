package net.cwhack.setting;

import net.cwhack.feature.Feature;

import java.util.Arrays;
import java.util.Optional;

public class EnumSetting<T extends Enum<T>> extends Setting<T>
{

	private final T[] values;
	private T value;

	public EnumSetting(String name, String description, T[] values, T value, Feature feature)
	{
		super(name, description, feature);
		this.values = values;
		this.value = value;
	}

	@Override
	public T getValue()
	{
		return value;
	}

	@Override
	public void loadFromStringInternal(String string)
	{
		Optional<T> v = Arrays.stream(values)
				.filter(e -> e.toString().equalsIgnoreCase(string))
				.findFirst();
		if (v.isEmpty())
			throw new RuntimeException();
		value = v.get();
	}

	@Override
	public String storeAsString()
	{
		return value.toString();
	}
}
