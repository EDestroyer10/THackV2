package ubl.nohurtcam.commands;

import ubl.nohurtcam.NoHurtCam;
import ubl.nohurtcam.command.Command;
import ubl.nohurtcam.command.exception.CommandException;
import ubl.nohurtcam.command.exception.CommandInvalidArgumentException;
import ubl.nohurtcam.command.exception.CommandSyntaxException;
import ubl.nohurtcam.feature.Feature;
import ubl.nohurtcam.feature.FeatureList;

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
		FeatureList featureList = NoHurtCam.CWHACK.getFeatures();
		Feature feature2Toggle = featureList.getFeature(command[0]);
		if (feature2Toggle == null)
			throw new CommandInvalidArgumentException("no feature named " + command[0]);
		feature2Toggle.toggle();
	}
}
