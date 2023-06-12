package ubl.nohurtcam.macro.exception;

import ubl.nohurtcam.utils.ChatUtils;

public class MacroSyntaxException extends MacroException
{

	public MacroSyntaxException(String message)
	{
		super(message);
	}

	@Override
	public void printToChat()
	{
		ChatUtils.error("Macro Syntax Error: " + getMessage());
	}
}
