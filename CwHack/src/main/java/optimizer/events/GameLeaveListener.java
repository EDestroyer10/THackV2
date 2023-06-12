package optimizer.events;

import optimizer.event.Event;
import optimizer.event.Listener;

import java.util.ArrayList;

public interface GameLeaveListener extends Listener
{
	void onGameLeave();

	class GameLeaveEvent extends Event<GameLeaveListener>
	{

		@Override
		public void fire(ArrayList<GameLeaveListener> listeners)
		{
			for (GameLeaveListener listener : listeners)
			{
				listener.onGameLeave();
			}
		}

		@Override
		public Class<GameLeaveListener> getListenerType()
		{
			return GameLeaveListener.class;
		}
	}
}
