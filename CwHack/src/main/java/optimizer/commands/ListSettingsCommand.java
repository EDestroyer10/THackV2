package optimizer.commands;

import optimizer.command.Command;
import optimizer.command.exception.CommandException;
import optimizer.command.exception.CommandInvalidArgumentException;
import optimizer.command.exception.CommandSyntaxException;
import optimizer.feature.Feature;
import optimizer.setting.Setting;
import optimizer.utils.ChatUtils;
import optimizer.Client;

public class ListSettingsCommand extends Command
{

	public ListSettingsCommand()
	{
		super("ListSettings", "List all the settings of a feature", new String[]{"feature name"});
	}

	@Override
	public void execute(String[] command) throws CommandException
	{
		if (command.length != 1)
			throw new CommandSyntaxException("argument number not matching");
		Feature feature = Client.CWHACK.getFeatures().getFeature(command[0]);
		if (feature == null)
			throw new CommandInvalidArgumentException("no feature named " + command[0]);
		for (Setting setting : feature.getSettings())
		{
			ChatUtils.info(setting.getName() + ": " + setting.storeAsString());
		}
	}
}
