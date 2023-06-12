package ubl.nohurtcam.commands;

import ubl.nohurtcam.command.Command;
import ubl.nohurtcam.command.exception.CommandException;
import ubl.nohurtcam.command.exception.CommandSyntaxException;
import ubl.nohurtcam.gui.screen.GuiScreen;

import static ubl.nohurtcam.NoHurtCam.MC;

public class GuiCommand extends Command
{

	public GuiCommand()
	{
		super("gui", "open up gui", new String[] {});
	}

	@Override
	public void execute(String[] command) throws CommandException
	{
		if (command.length != 0)
			throw new CommandSyntaxException("argument number not matching");
		MC.setScreen(new GuiScreen());
	}
}
