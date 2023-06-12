package ubl.nohurtcam.keybind;

import static ubl.nohurtcam.NoHurtCam.CWHACK;

public class Keybind
{

	private final String name;
	private final boolean down = false;
	private final int key;
	private final String command;
	private final boolean activeOnPress, activeOnRelease;

	public Keybind(String name, int key, boolean activeOnPress, boolean activeOnRelease, String command)
	{
		this.name = name;
		this.key = key;
		this.activeOnPress = activeOnPress;
		this.activeOnRelease = activeOnRelease;
		this.command = command;
	}

	public String getName()
	{
		return name;
	}

	public int getKey()
	{
		return key;
	}

	public void execute()
	{
		CWHACK.getCommandParser().execute(command);
	}

	public boolean shouldActiveOnPress()
	{
		return activeOnPress;
	}

	public boolean shouldActiveOnRelease()
	{
		return activeOnRelease;
	}
	public boolean isDown()
	{
		return down;
	}
}