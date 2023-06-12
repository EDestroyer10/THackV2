package ubl.nohurtcam.command.exception;

import ubl.nohurtcam.utils.ChatUtils;

public class CommandSyntaxException extends CommandException
{
	public CommandSyntaxException(String message)
	{
		super(message);
	}

	@Override
	public void printToChat()
	{
		ChatUtils.error("Command Syntax Error: " + getMessage());
	}
}