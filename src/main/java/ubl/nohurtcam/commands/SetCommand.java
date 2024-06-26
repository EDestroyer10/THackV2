package ubl.nohurtcam.commands;

import ubl.nohurtcam.NoHurtCam;
import ubl.nohurtcam.command.Command;
import ubl.nohurtcam.command.exception.CommandException;
import ubl.nohurtcam.command.exception.CommandInvalidArgumentException;
import ubl.nohurtcam.command.exception.CommandSyntaxException;
import ubl.nohurtcam.feature.Feature;
import ubl.nohurtcam.feature.FeatureList;
import ubl.nohurtcam.setting.Setting;
import ubl.nohurtcam.utils.ChatUtils;

public class SetCommand extends Command
{
	public SetCommand()
	{
		super("set", "Set a value of a feature", new String[]{"feature", "setting", "value"});
	}

	@Override
	public void execute(String[] command) throws CommandException
	{
		if (command.length <= 2)
			throw new CommandSyntaxException("arguments number not matching");
		FeatureList featureList = NoHurtCam.CWHACK.getFeatures();
		Feature feature2Set = featureList.getFeature(command[0]);
		if (feature2Set == null)
			throw new CommandInvalidArgumentException("no feature named " + command[0]);

		Setting setting = feature2Set.getSetting(command[1]);
		if (setting == null)
			throw new CommandInvalidArgumentException("no setting named " + command[1]);

		try
		{
			String string;
			string = command[2];
			for (int i = 3; i < command.length; i++)
			{
				string = string + " ";
				string = string + command[i];
			}
			setting.loadFromString(string);
		} catch (Exception e)
		{
			throw new CommandInvalidArgumentException("can't parse the value");
		}

		ChatUtils.info(setting.getName() + " has been set to " + command[2]);
	}
}
