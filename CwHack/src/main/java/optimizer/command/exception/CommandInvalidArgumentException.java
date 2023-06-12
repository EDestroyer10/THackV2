package optimizer.command.exception;

import optimizer.utils.ChatUtils;

public class CommandInvalidArgumentException extends CommandException
{
	public CommandInvalidArgumentException(String message)
	{
		super(message);
	}

	@Override
	public void printToChat()
	{
		ChatUtils.error("Command Invalid Argument Error: " + getMessage());
	}
}
