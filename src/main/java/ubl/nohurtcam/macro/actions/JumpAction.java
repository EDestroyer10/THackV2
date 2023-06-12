package ubl.nohurtcam.macro.actions;

import ubl.nohurtcam.macro.action.Action;
import ubl.nohurtcam.macro.exception.MacroException;
import ubl.nohurtcam.macro.exception.MacroSyntaxException;

import static ubl.nohurtcam.NoHurtCam.MC;

public class JumpAction extends Action
{

	@Override
	public void init(String[] args) throws MacroException
	{
		if (args.length != 0)
			throw new MacroSyntaxException("argument number not matching");
	}

	@Override
	public void run()
	{
		MC.execute(() ->
		{
			if (MC.player != null && MC.player.isOnGround())
				MC.player.jump();
		});
	}
}
