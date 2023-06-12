package ubl.nohurtcam.events;

import ubl.nohurtcam.event.Event;
import ubl.nohurtcam.event.Listener;

import java.util.ArrayList;

public interface PostUpdateListener extends Listener
{
	void onPostUpdate();

	class PostUpdateEvent extends Event<PostUpdateListener>
	{

		@Override
		public void fire(ArrayList<PostUpdateListener> listeners)
		{
			for (PostUpdateListener listener : listeners)
			{
				listener.onPostUpdate();
			}
		}

		@Override
		public Class<PostUpdateListener> getListenerType()
		{
			return PostUpdateListener.class;
		}
	}
}
