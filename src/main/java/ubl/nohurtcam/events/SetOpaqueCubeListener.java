package ubl.nohurtcam.events;

import ubl.nohurtcam.event.CancellableEvent;
import ubl.nohurtcam.event.Listener;

import java.util.ArrayList;

public interface SetOpaqueCubeListener extends Listener
{
	void onSetOpaqueCube(SetOpaqueCubeEvent event);

	class SetOpaqueCubeEvent extends CancellableEvent<SetOpaqueCubeListener>
	{

		@Override
		public void fire(ArrayList<SetOpaqueCubeListener> listeners)
		{
			for (SetOpaqueCubeListener listener : listeners)
			{
				listener.onSetOpaqueCube(this);
				if (isCancelled())
					return;
			}
		}

		@Override
		public Class<SetOpaqueCubeListener> getListenerType()
		{
			return SetOpaqueCubeListener.class;
		}
	}
}
