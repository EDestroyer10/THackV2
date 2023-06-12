package optimizer.macro.action;

import optimizer.macro.actions.ClickAction;
import optimizer.macro.actions.JumpAction;
import optimizer.macro.actions.PressKeyAction;
import optimizer.macro.actions.SleepAction;

import java.util.HashMap;

public class ActionList
{

	private final HashMap<String, Class<? extends Action>> actions = new HashMap<>();

	public ActionList()
	{
		actions.put("click", ClickAction.class);
		actions.put("jump", JumpAction.class);
		actions.put("press", PressKeyAction.class);
		actions.put("sleep", SleepAction.class);
	}

	public Class<? extends Action> getAction(String name)
	{
		return actions.get(name);
	}
}
