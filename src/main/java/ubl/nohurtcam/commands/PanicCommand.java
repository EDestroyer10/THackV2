package ubl.nohurtcam.commands;

import ubl.nohurtcam.command.Command;
import ubl.nohurtcam.command.exception.CommandException;
import ubl.nohurtcam.command.exception.CommandSyntaxException;

import static ubl.nohurtcam.NoHurtCam.CWHACK;

public class PanicCommand extends Command
{
	public PanicCommand()
	{
		super("panic", "Turn off all features and unbind all keybinds", new String[]{});
	}

	@Override
	public void execute(String[] command) throws CommandException
	{
		if (command.length != 0)
			throw new CommandSyntaxException("argument number not matching");
		CWHACK.getKeybindManager().removeAll();
		CWHACK.getFeatures().turnOffAll();
	}
}
