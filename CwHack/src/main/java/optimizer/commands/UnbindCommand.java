package optimizer.commands;

import optimizer.Client;
import optimizer.command.Command;
import optimizer.command.exception.CommandException;
import optimizer.command.exception.CommandSyntaxException;
import optimizer.utils.ChatUtils;

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

		Client.CWHACK.getKeybindManager().removeKeybind(command[0]);

		ChatUtils.info("keybind unbinded");
	}
}
