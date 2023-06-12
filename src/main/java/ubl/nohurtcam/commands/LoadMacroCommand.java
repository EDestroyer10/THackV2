package ubl.nohurtcam.commands;

import ubl.nohurtcam.command.Command;
import ubl.nohurtcam.command.exception.CommandException;
import ubl.nohurtcam.command.exception.CommandInvalidArgumentException;
import ubl.nohurtcam.command.exception.CommandMacroException;

import static ubl.nohurtcam.NoHurtCam.CWHACK;

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
			CWHACK.getMacroManager().loadMacro(command[0]);
		} catch (Exception e)
		{
			throw new CommandMacroException("failed to load the macro");
		}
	}
}
