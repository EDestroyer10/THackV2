package ubl.nohurtcam.events;

import ubl.nohurtcam.event.CancellableEvent;
import ubl.nohurtcam.event.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;

public interface AttackEntityListener extends Listener
{
	void onAttackEntity(AttackEntityEvent event);

	class AttackEntityEvent extends CancellableEvent<AttackEntityListener>
	{

		private PlayerEntity player;
		private Entity target;

		public AttackEntityEvent(PlayerEntity player, Entity target)
		{
			// player should always be a ClientPlayerEntity
			this.player = player;
			this.target = target;
		}

		public PlayerEntity getPlayer()
		{
			return player;
		}

		public Entity getTarget()
		{
			return target;
		}

		@Override
		public void fire(ArrayList<AttackEntityListener> listeners)
		{
			for (AttackEntityListener listener : listeners)
			{
				listener.onAttackEntity(this);
			}
		}

		@Override
		public Class<AttackEntityListener> getListenerType()
		{
			return AttackEntityListener.class;
		}
	}
}
