package optimizer.commands;

import optimizer.command.Command;
import optimizer.command.exception.CommandBadConfigException;
import optimizer.command.exception.CommandException;
import optimizer.command.exception.CommandSyntaxException;
import optimizer.utils.ChatUtils;
import optimizer.Client;

import java.nio.file.Path;

public class LoadCommand extends Command
{

	public LoadCommand()
	{
		super("load", "load a configuration", new String[]{"config name"});
	}

	@Override
	public void execute(String[] command) throws CommandException
	{
		if (command.length != 1)
			throw new CommandSyntaxException("argument number not matching");
		Path configDir = Client.CWHACK.getCwHackDirectory().resolve("config");
		Path configFilePath = configDir.resolve(command[0] + ".cw");

		try
		{
			Client.CWHACK.getFeatures().loadFromFile(configFilePath.toString());
		}
		catch (Exception e)
		{
			throw new CommandBadConfigException(command[0]);
		}

		ChatUtils.info("config loaded");
	}
}
