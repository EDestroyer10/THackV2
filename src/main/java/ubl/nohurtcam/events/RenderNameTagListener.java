package ubl.nohurtcam.events;

import ubl.nohurtcam.event.Event;
import ubl.nohurtcam.event.Listener;

import java.util.ArrayList;

public interface RenderNameTagListener extends Listener
{
	void onRenderNameTag();

	class RenderNameTagEvent extends Event<RenderNameTagListener>
	{

		@Override
		public void fire(ArrayList<RenderNameTagListener> listeners)
		{
			for (RenderNameTagListener listener : listeners)
				listener.onRenderNameTag();
		}

		@Override
		public Class<RenderNameTagListener> getListenerType()
		{
			return RenderNameTagListener.class;
		}
	}
}
