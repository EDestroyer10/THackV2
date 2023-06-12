package optimizer.commands;

import optimizer.Client;
import optimizer.command.Command;
import optimizer.command.exception.CommandException;
import optimizer.command.exception.CommandInvalidArgumentException;
import optimizer.command.exception.CommandMacroException;

public class LoadMacroCommand extends Command
{

	public LoadMacroCommand()
	{
		super("loadmacro", "load and compile a macro", new String[]{"macro name"});
	}

	@Override
	public void execute(String[] command) throws CommandException
	{
		if (command.length != 1)
			throw new CommandInvalidArgumentException("argument number not matching");
		try
		{
			Client.CWHACK.getMacroManager().loadMacro(command[0]);
		} catch (Exception e)
		{
			throw new CommandMacroException("failed to load the macro");
		}
	}
}
