package ubl.nohurtcam.events;

import ubl.nohurtcam.event.Event;
import ubl.nohurtcam.event.Listener;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;

public interface GUIRenderListener extends Listener
{
	void onRenderGUI(GUIRenderEvent event);

	class GUIRenderEvent extends Event<GUIRenderListener>
	{

		private final MatrixStack matrixStack;
		private final float partialTicks;

		public GUIRenderEvent(MatrixStack matrixStack, float partialTicks)
		{
			this.matrixStack = matrixStack;
			this.partialTicks = partialTicks;
		}

		public MatrixStack getMatrixStack()
		{
			return matrixStack;
		}

		public float getPartialTicks()
		{
			return partialTicks;
		}

		@Override
		public void fire(ArrayList<GUIRenderListener> listeners)
		{
			for (GUIRenderListener listener : listeners)
			{
				listener.onRenderGUI(this);
			}
		}

		@Override
		public Class<GUIRenderListener> getListenerType()
		{
			return GUIRenderListener.class;
		}
	}
}
