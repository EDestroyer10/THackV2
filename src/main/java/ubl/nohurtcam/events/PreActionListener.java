package ubl.nohurtcam.events;

import ubl.nohurtcam.event.Event;
import ubl.nohurtcam.event.Listener;

import java.util.ArrayList;

public interface PreActionListener extends Listener
{
	void onPreAction();

	class PreActionEvent extends Event<PreActionListener>
	{

		@Override
		public void fire(ArrayList<PreActionListener> listeners)
		{
			for (PreActionListener listener : listeners)
			{
				listener.onPreAction();
			}
		}

		@Override
		public Class<PreActionListener> getListenerType()
		{
			return PreActionListener.class;
		}
	}
}
