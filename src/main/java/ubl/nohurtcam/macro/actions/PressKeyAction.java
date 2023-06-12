package ubl.nohurtcam.macro.actions;

import ubl.nohurtcam.macro.action.Action;
import ubl.nohurtcam.macro.exception.MacroException;
import ubl.nohurtcam.macro.exception.MacroInvalidArgumentException;
import ubl.nohurtcam.macro.exception.MacroSyntaxException;
import ubl.nohurtcam.mixinterface.IKeyboard;
import org.lwjgl.glfw.GLFW;

import static ubl.nohurtcam.NoHurtCam.MC;

public class PressKeyAction extends Action
{

	private int key;

	@Override
	public void init(String[] args) throws MacroException
	{
		if (args.length != 1)
			throw new MacroSyntaxException("argument number not matching");
		try
		{
			key = Integer.parseInt(args[0]);
		} catch (Exception e)
		{
			throw new MacroInvalidArgumentException("can't parse the value");
		}
	}

	@Override
	public void run()
	{
		MC.execute(this::runInner);
	}

	private void runInner()
	{
		IKeyboard iKeyboard = (IKeyboard) MC.keyboard;
		long window = MC.getWindow().getHandle();
		MC.keyboard.onKey(window, key, 0, GLFW.GLFW_PRESS, 0);
		MC.keyboard.onKey(window, key, 0, GLFW.GLFW_RELEASE, 0);
		iKeyboard.cwOnChar(window, key, 0);
	}

}
