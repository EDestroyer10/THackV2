package optimizer.events;

import optimizer.event.CancellableEvent;
import optimizer.event.Listener;

import java.util.ArrayList;

public interface StopUsingItemListener extends Listener
{
	void onStopUsingItem(StopUsingItemEvent event);

	class StopUsingItemEvent extends CancellableEvent<StopUsingItemListener>
	{

		@Override
		public void fire(ArrayList<StopUsingItemListener> listeners)
		{
			for (StopUsingItemListener listener : listeners)
				listener.onStopUsingItem(this);
		}

		@Override
		public Class<StopUsingItemListener> getListenerType()
		{
			return StopUsingItemListener.class;
		}
	}
}
