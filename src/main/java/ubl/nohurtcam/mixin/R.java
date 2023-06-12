/*    */
package ubl.nohurtcam.mixin;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class R
/*    */ {
/*    */   private final float yaw;
/*    */   private final float pitch;
/*    */   private final boolean ignoreYaw;
/*    */   private final boolean ignorePitch;
/*    */   
/*    */   public R(float yaw, float pitch) {
/* 13 */     this.yaw = yaw;
/* 14 */     this.pitch = pitch;
/* 15 */     this.ignoreYaw = false;
/* 16 */     this.ignorePitch = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public R(float yaw, boolean ignoreYaw, float pitch, boolean ignorePitch) {
/* 21 */     this.yaw = yaw;
/* 22 */     this.ignoreYaw = ignoreYaw;
/* 23 */     this.pitch = pitch;
/* 24 */     this.ignorePitch = ignorePitch;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getYaw() {
/* 29 */     return this.yaw;
/*    */   }
/*    */   
/*    */   public float getPitch() {
/* 33 */     return this.pitch;
/*    */   }
/*    */   
/*    */   public boolean isIgnoreYaw() {
/* 37 */     return this.ignoreYaw;
/*    */   }
/*    */   
/*    */   public boolean isIgnorePitch() {
/* 41 */     return this.ignorePitch;
/*    */   }
/*    */ }
