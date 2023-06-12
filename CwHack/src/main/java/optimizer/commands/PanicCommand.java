package optimizer.commands;

import optimizer.Client;
import optimizer.command.Command;
import optimizer.command.exception.CommandException;
import optimizer.command.exception.CommandSyntaxException;

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
		Client.CWHACK.getKeybindManager().removeAll();
		Client.CWHACK.getFeatures().turnOffAll();
	}
}
