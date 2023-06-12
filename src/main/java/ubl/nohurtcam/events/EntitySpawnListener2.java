
package ubl.nohurtcam.events;

 import java.util.ArrayList;
 import ubl.nohurtcam.event.Listener;
import net.minecraft.entity.Entity;




 public interface EntitySpawnListener2
   extends Listener
 {
   void onEntitySpawn(Entity entity);

   public static class EntitySpawnEvent
     extends E<EntitySpawnListener>
   {
     private Entity entity;

     public EntitySpawnEvent(Entity entity) {
       this.entity = entity;
     }



     public void fire(ArrayList<EntitySpawnListener> listeners) {
       listeners.forEach(e -> e.onEntitySpawn(this.entity));
     }



     public Class<EntitySpawnListener> getListenerType() {
       return EntitySpawnListener.class;
     }
   }
 }

