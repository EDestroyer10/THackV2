package optimizer.macro.actions;

import optimizer.macro.exception.MacroException;
import optimizer.macro.exception.MacroInvalidArgumentException;
import optimizer.macro.exception.MacroSyntaxException;
import optimizer.macro.action.Action;

public class SleepAction extends Action
{

	private int time;

	@Override
	public void init(String[] args) throws MacroException
	{
		if (args.length != 1)
			throw new MacroSyntaxException("argument number not matching");
		try
		{
			time = Integer.parseInt(args[0]);
		} catch (Exception e)
		{
			throw new MacroInvalidArgumentException("can't parse the value");
		}
	}

	@Override
	public void run()
	{
		try
		{
			Thread.sleep(time);
		} catch (Exception e)
		{

		}
	}
}
