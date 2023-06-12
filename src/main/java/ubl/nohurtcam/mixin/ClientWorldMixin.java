package ubl.nohurtcam.mixin;

import ubl.nohurtcam.event.EventManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ubl.nohurtcam.events.EntityDespawnListener;
import ubl.nohurtcam.events.EntitySpawnListener;

import static ubl.nohurtcam.NoHurtCam.MC;

@Mixin(ClientWorld.class)
public class ClientWorldMixin
{

	@Inject(method = "addEntityPrivate", at = @At("TAIL"))
	private void onAddEntityPrivate(int id, Entity entity, CallbackInfo info) {
		if (entity != null)
			EventManager.fire(new EntitySpawnListener.EntitySpawnEvent(entity));
	}

	@Inject(method = "removeEntity", at = @At("TAIL"))
	private void onFinishRemovingEntity(int entityId, Entity.RemovalReason removalReason, CallbackInfo info) {
		Entity entity = MC.world.getEntityById(entityId);
		if (entity != null)
			EventManager.fire(new EntityDespawnListener.EntityDespawnEvent(entity));
	}

}
