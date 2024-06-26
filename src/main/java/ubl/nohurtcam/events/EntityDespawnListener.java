package ubl.nohurtcam.events;

import ubl.nohurtcam.event.Event;
import ubl.nohurtcam.event.Listener;
import net.minecraft.entity.Entity;

import java.util.ArrayList;

public interface EntityDespawnListener extends Listener
{
	void onEntityDespawn(Entity entity);

	class EntityDespawnEvent extends Event<EntityDespawnListener>
	{

		private Entity entity;

		public EntityDespawnEvent(Entity entity)
		{
			this.entity = entity;
		}

		@Override
		public void fire(ArrayList<EntityDespawnListener> listeners)
		{
			for (EntityDespawnListener listener : listeners)
			{
				listener.onEntityDespawn(entity);
			}
		}

		@Override
		public Class<EntityDespawnListener> getListenerType()
		{
			return EntityDespawnListener.class;
		}
	}
}
