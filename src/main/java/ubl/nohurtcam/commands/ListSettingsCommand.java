package ubl.nohurtcam.commands;

import ubl.nohurtcam.command.Command;
import ubl.nohurtcam.command.exception.CommandException;
import ubl.nohurtcam.command.exception.CommandInvalidArgumentException;
import ubl.nohurtcam.command.exception.CommandSyntaxException;
import ubl.nohurtcam.feature.Feature;
import ubl.nohurtcam.setting.Setting;
import ubl.nohurtcam.utils.ChatUtils;

import static ubl.nohurtcam.NoHurtCam.CWHACK;

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
		Feature feature = CWHACK.getFeatures().getFeature(command[0]);
		if (feature == null)
			throw new CommandInvalidArgumentException("no feature named " + command[0]);
		for (Setting setting : feature.getSettings())
		{
			ChatUtils.info(setting.getName() + ": " + setting.storeAsString());
		}
	}
}
