package optimizer.commands;

import optimizer.Client;
import optimizer.command.Command;
import optimizer.command.exception.CommandException;
import optimizer.command.exception.CommandInvalidArgumentException;
import optimizer.command.exception.CommandSyntaxException;
import optimizer.feature.Feature;
import optimizer.feature.FeatureList;

public class ToggleCommand extends Command
{
	public ToggleCommand()
	{
		super("toggle", "Toggle a feature", new String[]{"feature"});
	}

	@Override
	public void execute(String[] command) throws CommandException
	{
		if (command.length != 1)
			throw new CommandSyntaxException("argument number not matching");
		FeatureList featureList = Client.CWHACK.getFeatures();
		Feature feature2Toggle = featureList.getFeature(command[0]);
		if (feature2Toggle == null)
			throw new CommandInvalidArgumentException("no feature named " + command[0]);
		feature2Toggle.toggle();
	}
}
