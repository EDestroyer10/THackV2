package optimizer.events;

import optimizer.event.Event;
import optimizer.event.Listener;
import net.minecraft.entity.Entity;

import java.util.ArrayList;

public interface EntitySpawnListener extends Listener
{
	void onEntitySpawn(Entity entity);

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
