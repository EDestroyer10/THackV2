package optimizer.events;

import optimizer.event.Event;
import optimizer.event.Listener;

import java.util.ArrayList;

public interface PreMotionListener extends Listener
{
	void onPreMotion();

	class PreMotionEvent extends Event<PreMotionListener>
	{

		@Override
		public void fire(ArrayList<PreMotionListener> listeners)
		{
			for (PreMotionListener listener : listeners)
			{
				listener.onPreMotion();
			}
		}

		@Override
		public Class<PreMotionListener> getListenerType()
		{
			return PreMotionListener.class;
		}
	}
}
