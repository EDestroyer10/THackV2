package ubl.nohurtcam.commands;

import ubl.nohurtcam.command.Command;
import ubl.nohurtcam.command.exception.CommandBadConfigException;
import ubl.nohurtcam.command.exception.CommandException;
import ubl.nohurtcam.command.exception.CommandSyntaxException;
import ubl.nohurtcam.utils.ChatUtils;

import java.nio.file.Path;

import static ubl.nohurtcam.NoHurtCam.CWHACK;

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
		Path configDir = CWHACK.getCwHackDirectory().resolve("config");
		Path configFilePath = configDir.resolve(command[0] + ".cw");

		try
		{
			CWHACK.getFeatures().loadFromFile(configFilePath.toString());
		}
		catch (Exception e)
		{
			throw new CommandBadConfigException(command[0]);
		}

		ChatUtils.info("config loaded");
	}
}
