package ubl.nohurtcam.features;

import ubl.nohurtcam.NoHurtCam;
import ubl.nohurtcam.events.AttackEntityListener;
import ubl.nohurtcam.events.UpdateListener;
import ubl.nohurtcam.feature.Feature;
import ubl.nohurtcam.setting.IntegerSetting;

public class AutoWTapFeature
   extends Feature implements AttackEntityListener, UpdateListener {
   private final IntegerSetting delay = new IntegerSetting("delay", "the interval delay", 0, this);

   private int delayClock = 0;
   private boolean reset = false;

   public AutoWTapFeature() {
     super("AutoWTap", "automaticly reset sprint");
   }


   public void onEnable() {
     super.onEnable();
     eventManager.add(AttackEntityListener.class, this);
     eventManager.add(UpdateListener.class, this);

     this.reset = false;
     this.delayClock = 0;
   }


   public void onDisable() {
     super.onDisable();
     eventManager.remove(AttackEntityListener.class, this);
     eventManager.remove(UpdateListener.class, this);
   }

   @Override
   public void onUpdate() {
     if (this.reset && this.delayClock != this.delay.getValue()) {
       this.delayClock++;
     } else if (this.reset) {
       NoHurtCam.MC.options.sprintKey.setPressed(true);
       this.reset = false;
       this.delayClock = 0;
     }
   }


   public void onAttackEntity(AttackEntityEvent event) {

    assert NoHurtCam.MC.player != null;
    if (NoHurtCam.MC.player.isSprinting()) {
       NoHurtCam.MC.options.sprintKey.setPressed(false);
       this.reset = true;
     }
   }
 }