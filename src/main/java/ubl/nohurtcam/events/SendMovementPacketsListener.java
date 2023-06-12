package ubl.nohurtcam.events;

import ubl.nohurtcam.event.CancellableEvent;
import ubl.nohurtcam.event.Listener;

import java.util.ArrayList;

public interface SendMovementPacketsListener extends Listener
{
	void onSendMovementPackets(SendMovementPacketsEvent event);

	class SendMovementPacketsEvent extends CancellableEvent<SendMovementPacketsListener>
	{

		@Override
		public void fire(ArrayList<SendMovementPacketsListener> listeners)
		{
			for (SendMovementPacketsListener listener : listeners)
			{
				listener.onSendMovementPackets(this);
				if (isCancelled())
					return;
			}
		}

		@Override
		public Class<SendMovementPacketsListener> getListenerType()
		{
			return SendMovementPacketsListener.class;
		}
	}
}
