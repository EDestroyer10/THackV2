package ubl.nohurtcam.events;

import ubl.nohurtcam.event.Event;
import ubl.nohurtcam.event.Listener;

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
