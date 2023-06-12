package ubl.nohurtcam.commands;

import ubl.nohurtcam.NoHurtCam;
import ubl.nohurtcam.command.Command;
import ubl.nohurtcam.command.exception.CommandException;
import ubl.nohurtcam.command.exception.CommandSyntaxException;
import ubl.nohurtcam.utils.ChatUtils;

public class UnbindCommand extends Command
{

	public UnbindCommand()
	{
		super("unbind", "Unbind a keybind", new String[]{"name"});
	}

	@Override
	public void execute(String[] command) throws CommandException
	{
		if (command.length != 1)
			throw new CommandSyntaxException("argument number not matching");

		NoHurtCam.CWHACK.getKeybindManager().removeKeybind(command[0]);

		ChatUtils.info("keybind unbinded");
	}
}
