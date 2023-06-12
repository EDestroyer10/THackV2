package ubl.nohurtcam.commands;

import ubl.nohurtcam.command.Command;
import ubl.nohurtcam.command.exception.CommandException;
import ubl.nohurtcam.command.exception.CommandSyntaxException;
import ubl.nohurtcam.utils.ChatUtils;

import static ubl.nohurtcam.NoHurtCam.CWHACK;

public class ListCommand extends Command
{

	public ListCommand()
	{
		super("list", "List all available features", new String[]{});
	}

	@Override
	public void execute(String[] command) throws CommandException
	{
		if (command.length != 0)
			throw new CommandSyntaxException("argument number not matching");
		for (String feature : CWHACK.getFeatures().getAllFeatureNames())
		{
			ChatUtils.info(CWHACK.getFeatures().getFeature(feature).getName());
		}
	}
}
