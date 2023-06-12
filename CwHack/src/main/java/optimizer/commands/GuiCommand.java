package optimizer.commands;

import optimizer.Client;
import optimizer.command.Command;
import optimizer.command.exception.CommandException;
import optimizer.command.exception.CommandSyntaxException;
import optimizer.gui.screen.GuiScreen;

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
		Client.MC.setScreen(new GuiScreen());
	}
}
