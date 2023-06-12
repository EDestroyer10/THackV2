package optimizer.events;

import optimizer.event.Event;
import optimizer.event.Listener;

import java.util.ArrayList;

public interface UpdateListener extends Listener
{
	void onUpdate();

	class UpdateEvent extends Event<UpdateListener>
	{

		@Override
		public void fire(ArrayList<UpdateListener> listeners)
		{
			for (UpdateListener listener : listeners)
			{
				listener.onUpdate();
			}
		}

		@Override
		public Class<UpdateListener> getListenerType()
		{
			return UpdateListener.class;
		}
	}
}
