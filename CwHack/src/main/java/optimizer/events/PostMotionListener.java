package optimizer.events;

import optimizer.event.Event;
import optimizer.event.Listener;

import java.util.ArrayList;

public interface PostMotionListener extends Listener
{
	void onPostMotion();

	class PostMotionEvent extends Event<PostMotionListener>
	{

		@Override
		public void fire(ArrayList<PostMotionListener> listeners)
		{
			for (PostMotionListener listener : listeners)
			{
				listener.onPostMotion();
			}
		}

		@Override
		public Class<PostMotionListener> getListenerType()
		{
			return PostMotionListener.class;
		}
	}
}
