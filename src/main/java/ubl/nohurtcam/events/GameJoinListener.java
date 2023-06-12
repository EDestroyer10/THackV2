package ubl.nohurtcam.events;

import ubl.nohurtcam.event.Event;
import ubl.nohurtcam.event.Listener;

import java.util.ArrayList;

public interface GameJoinListener extends Listener
{
	void onGameJoin();

	class GameJoinEvent extends Event<GameJoinListener>
	{

		@Override
		public void fire(ArrayList<GameJoinListener> listeners)
		{
			for (GameJoinListener listener : listeners)
			{
				listener.onGameJoin();
			}
		}

		@Override
		public Class<GameJoinListener> getListenerType()
		{
			return GameJoinListener.class;
		}
	}
}
