package optimizer.commands;

import optimizer.Client;
import optimizer.command.Command;
import optimizer.command.CommandParser;
import optimizer.command.exception.CommandException;
import optimizer.command.exception.CommandFileNotFoundException;
import optimizer.command.exception.CommandSyntaxException;
import optimizer.utils.ChatUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class RunCommand extends Command
{
	public RunCommand()
	{
		super("run", "run a script", new String[]{"script name"});
	}

	@Override
	public void execute(String[] command) throws CommandException
	{
		if (command.length != 1)
			throw new CommandSyntaxException("argument number not matching");
		Path scriptDir = Client.CWHACK.getCwHackDirectory().resolve("script");
		Path scriptFilePath = scriptDir.resolve(command[0] + ".cwscript");
		try
		{
			if (Files.notExists(scriptFilePath))
				throw new CommandFileNotFoundException(command[0]);
			Scanner scanner = new Scanner(scriptFilePath);
			while (scanner.hasNextLine())
			{
				String line = scanner.nextLine();
				if (line.startsWith(CommandParser.COMMAND_PREFIX))
					line = line.substring(1);
				Client.CWHACK.getCommandParser().execute(line);
			}
		} catch (IOException e)
		{
			throw new RuntimeException("Failed to open script file");
		}

		ChatUtils.info("script run");
	}
}
