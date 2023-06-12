package ubl.nohurtcam.events;

import ubl.nohurtcam.event.Event;
import ubl.nohurtcam.event.Listener;

import java.util.ArrayList;

public interface PostActionListener extends Listener
{
	void onPostAction();

	class PostActionEvent extends Event<PostActionListener>
	{

		@Override
		public void fire(ArrayList<PostActionListener> listeners)
		{
			for (PostActionListener listener : listeners)
			{
				listener.onPostAction();
			}
		}

		@Override
		public Class<PostActionListener> getListenerType()
		{
			return PostActionListener.class;
		}
	}
}
