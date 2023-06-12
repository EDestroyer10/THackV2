package optimizer.command.exception;

import optimizer.utils.ChatUtils;

public class CommandFileNotFoundException extends CommandException
{

	public CommandFileNotFoundException(String configName)
	{
		super(configName);
	}

	@Override
	public void printToChat()
	{
		ChatUtils.error("Command Config Not Found Error: config " + getMessage() + " not found");
	}
}
