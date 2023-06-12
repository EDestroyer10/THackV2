package ubl.nohurtcam.events;

import ubl.nohurtcam.event.Event;
import ubl.nohurtcam.event.Listener;
import net.minecraft.entity.Entity;

import java.util.ArrayList;

public interface EntitySpawnListener extends Listener
{
	int onEntitySpawn(Entity entity);

	class EntitySpawnEvent extends Event<EntitySpawnListener>
	{

		private Entity entity;

		public EntitySpawnEvent(Entity entity)
		{
			this.entity = entity;
		}

		@Override
		public void fire(ArrayList<EntitySpawnListener> listeners)
		{
			for (EntitySpawnListener listener : listeners)
			{
				listener.onEntitySpawn(entity);
			}
		}

		@Override
		public Class<EntitySpawnListener> getListenerType()
		{
			return EntitySpawnListener.class;
		}
	}
}
