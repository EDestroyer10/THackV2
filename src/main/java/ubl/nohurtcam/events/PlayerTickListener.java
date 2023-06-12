/*    */
package ubl.nohurtcam.events;
/*    */ 
/*    */ import java.util.ArrayList;
import java.util.EventListener;

/*    */
/*    */ 
/*    */ 
/*    */ public interface PlayerTickListener
/*    */   extends EventListener
/*    */ {
/*    */   void onPlayerTick();
/*    */   
/*    */   public static class PlayerTickEvent
/*    */     extends E<PlayerTickListener>
/*    */   {
/*    */     public void fire(ArrayList<PlayerTickListener> listeners) {
/* 18 */       listeners.forEach(PlayerTickListener::onPlayerTick);
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public Class<PlayerTickListener> getListenerType() {
/* 24 */       return PlayerTickListener.class;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\L4J\Desktop\skidded\org_apache-5.2.1.jar!\org\apache\core\e\e\PlayerTickListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */