package ubl.nohurtcam.events;

import ubl.nohurtcam.event.CancellableEvent;
import ubl.nohurtcam.event.Listener;

import java.util.ArrayList;

public interface PlayerJumpListener extends Listener
{
	void onPlayerJump(PlayerJumpEvent event);

	class PlayerJumpEvent extends CancellableEvent<PlayerJumpListener>
	{

		@Override
		public void fire(ArrayList<PlayerJumpListener> listeners)
		{
			for (PlayerJumpListener listener : listeners)
			{
				listener.onPlayerJump(this);
				if (isCancelled())
					return;
			}
		}

		@Override
		public Class<PlayerJumpListener> getListenerType()
		{
			return PlayerJumpListener.class;
		}
	}
}
