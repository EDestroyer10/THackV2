package optimizer.macro.action;

import optimizer.macro.exception.MacroException;

public abstract class Action
{
	public abstract void init(String[] args) throws MacroException;

	public abstract void run();
}
