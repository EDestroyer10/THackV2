package optimizer.commands;

import optimizer.command.Command;
import optimizer.command.exception.CommandException;
import optimizer.command.exception.CommandInvalidArgumentException;
import optimizer.command.exception.CommandSyntaxException;
import optimizer.utils.ChatUtils;
import optimizer.Client;

import java.util.Set;

public class HelpCommand extends Command
{

	public HelpCommand()
	{
		super("help", "check usage and syntax for a specific command, when the command is not specified, all available command will be listed", new String[]{"command (optional)"});
	}

	@Override
	public void execute(String[] command) throws CommandException
	{
		if (command.length > 1)
			throw new CommandSyntaxException("argument number not matching");
		if (command.length == 0)
		{
			ChatUtils.info("All available commands:");
			listAllCommands();
			ChatUtils.info("do @help [command] to see the usage and syntax for a specific command");
		}
		if (command.length == 1)
		{
			Command cmd = Client.CWHACK.getCommandParser().commandList.getCommand(command[0]);
			if (cmd == null)
				throw new CommandInvalidArgumentException("no command named " + command[0] + " found");
			ChatUtils.info(cmd.getDescription());
			StringBuilder syntax = new StringBuilder("Syntax: " + cmd.getName());
			for (String string : cmd.getSyntax())
			{
				syntax.append(" [").append(string).append("]");
			}
			ChatUtils.info(syntax.toString());
		}
	}

	private void listAllCommands()
	{
		Set<String> commands = Client.CWHACK.getCommandParser().commandList.getAllCommandNames();
		for (String command : commands)
		{
			ChatUtils.info(command);
		}
	}
}
