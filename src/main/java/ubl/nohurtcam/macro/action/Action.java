package ubl.nohurtcam.macro.action;

import ubl.nohurtcam.macro.exception.MacroException;

public abstract class Action
{
	public abstract void init(String[] args) throws MacroException;

	public abstract void run();
}
