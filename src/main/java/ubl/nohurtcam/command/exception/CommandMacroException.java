package ubl.nohurtcam.command.exception;

import ubl.nohurtcam.utils.ChatUtils;

public class CommandMacroException extends CommandException
{

	public CommandMacroException(String message)
	{
		super(message);
	}

	@Override
	public void printToChat()
	{
		ChatUtils.error("Command Macro Error: " + getMessage());
	}
}