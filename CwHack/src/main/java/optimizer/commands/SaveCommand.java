package optimizer.commands;

import optimizer.command.Command;
import optimizer.command.exception.CommandException;
import optimizer.command.exception.CommandSyntaxException;
import optimizer.utils.ChatUtils;
import optimizer.Client;

import java.nio.file.Path;

public class SaveCommand extends Command
{

	public SaveCommand()
	{
		super("save", "save a configuration", new String[]{"config name"});
	}

	@Override
	public void execute(String[] command) throws CommandException
	{
		if (command.length != 1)
			throw new CommandSyntaxException("argument number not matching");
		writeConfig(command[0]);

		ChatUtils.info("config saved");
	}

	private void writeConfig(String name)
	{
		Path configDir = Client.CWHACK.getConfigDirectory();
		Path configFilePath = configDir.resolve(name + ".cw");
		Client.CWHACK.getFeatures().saveAsFile(configFilePath.toString());
	}
}
