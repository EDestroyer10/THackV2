package ubl.nohurtcam.commands;

import ubl.nohurtcam.command.Command;
import ubl.nohurtcam.command.exception.CommandException;
import ubl.nohurtcam.command.exception.CommandInvalidArgumentException;
import ubl.nohurtcam.command.exception.CommandMacroException;

import static ubl.nohurtcam.NoHurtCam.CWHACK;

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
			CWHACK.getMacroManager().runMacro(command[0]);
		} catch (Exception e)
		{
			throw new CommandMacroException("failed to run the macro");
		}
	}
}
