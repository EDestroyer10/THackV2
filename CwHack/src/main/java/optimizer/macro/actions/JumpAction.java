package optimizer.macro.actions;

import optimizer.macro.exception.MacroException;
import optimizer.macro.exception.MacroSyntaxException;
import optimizer.macro.action.Action;

import static optimizer.Client.MC;

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
