package optimizer.commands;

import optimizer.command.Command;
import optimizer.command.exception.CommandException;
import optimizer.command.exception.CommandInvalidArgumentException;
import optimizer.command.exception.CommandMacroException;
import optimizer.Client;

public class RunMacroCommand extends Command
{

	public RunMacroCommand()
	{
		super("runmacro", "run a macro", new String[]{"macro name"});
	}

	@Override
	public void execute(String[] command) throws CommandException
	{
		if (command.length != 1)
			throw new CommandInvalidArgumentException("argument number not matching");
		try
		{
			Client.CWHACK.getMacroManager().runMacro(command[0]);
		} catch (Exception e)
		{
			throw new CommandMacroException("failed to run the macro");
		}
	}
}
