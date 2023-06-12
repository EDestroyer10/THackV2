package optimizer.macro.actions;

import optimizer.macro.exception.MacroException;
import optimizer.macro.exception.MacroInvalidArgumentException;
import optimizer.macro.exception.MacroSyntaxException;
import optimizer.mixinterface.IMouse;
import optimizer.macro.action.Action;
import org.lwjgl.glfw.GLFW;

import static optimizer.Client.MC;

public class ClickAction extends Action
{

	private int button;

	@Override
	public void init(String[] args) throws MacroException
	{
		if (args.length != 1)
			throw new MacroSyntaxException("argument number not matching");
		try
		{
			button = Integer.parseInt(args[0]);
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
		IMouse iMouse = (IMouse) MC.mouse;
		iMouse.cwOnMouseButton(MC.getWindow().getHandle(), button, GLFW.GLFW_PRESS, 0);
		iMouse.cwOnMouseButton(MC.getWindow().getHandle(), button, GLFW.GLFW_RELEASE, 0);
	}
}
