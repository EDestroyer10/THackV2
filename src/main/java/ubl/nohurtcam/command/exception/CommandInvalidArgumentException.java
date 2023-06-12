package ubl.nohurtcam.command.exception;

import ubl.nohurtcam.utils.ChatUtils;

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
