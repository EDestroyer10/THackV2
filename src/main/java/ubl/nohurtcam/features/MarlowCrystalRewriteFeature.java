package ubl.nohurtcam.features;

import ubl.nohurtcam.NoHurtCam;
import ubl.nohurtcam.events.ItemUseListener;
import ubl.nohurtcam.events.UpdateListener;
import ubl.nohurtcam.feature.Feature;

import ubl.nohurtcam.setting.BooleanSetting;
import ubl.nohurtcam.setting.IntegerSetting;
import ubl.nohurtcam.utils.BlockUtils;
import ubl.nohurtcam.utils.CrystalUtils;
import ubl.nohurtcam.utils.RotationUtils;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.lwjgl.glfw.GLFW;

public class MarlowCrystalRewriteFeature extends Feature implements ItemUseListener, UpdateListener {
    private final IntegerSetting breakInterval = new IntegerSetting("breakInterval", "the speed of attacking the crystal", 2, this);

    private final BooleanSetting activateOnRightClick = new BooleanSetting("activate On Right Click", "a", true, this);

    private final BooleanSetting stopOnKill = new BooleanSetting("stopOnKill", "Anti Loot Blow Up", true, this);
   private int crystalBreakClock;

   public MarlowCrystalRewriteFeature() {
     super("MarlowCrystalRewrite", "crystal like marlow");


     this.crystalBreakClock = 0;
   }


   public void onEnable() {
       eventManager.add(UpdateListener.class, this);
       eventManager.add(ItemUseListener.class, this);
       crystalBreakClock = 0;
   }



   public void onDisable() {
       eventManager.remove(UpdateListener.class, this);
       eventManager.remove(ItemUseListener.class, this);
   }


   private boolean isDeadBodyNearby() {
     return NoHurtCam.MC.world.getPlayers().parallelStream()
       .filter(e -> (NoHurtCam.MC.player != e))
       .filter(e -> (e.squaredDistanceTo((Entity) NoHurtCam.MC.player) < 36.0D))
       .anyMatch(LivingEntity::isDead);
   }



   public void onItemUse(ItemUseEvent event) {
     ItemStack mainHandStack = NoHurtCam.MC.player.getMainHandStack();
     if (NoHurtCam.MC.crosshairTarget.getType() == HitResult.Type.BLOCK) {

       BlockHitResult hit = (BlockHitResult) NoHurtCam.MC.crosshairTarget;
       if (mainHandStack.isOf(Items.END_CRYSTAL) && BlockUtils.isBlock(Blocks.OBSIDIAN, hit.getBlockPos())) {
         event.cancel();
       }
     }
   }


   public void onUpdate() {
     boolean dontBreakCrystal = (this.crystalBreakClock != 0);
     if (dontBreakCrystal)
       this.crystalBreakClock--;
     if (this.activateOnRightClick.getValue() && GLFW.glfwGetMouseButton(NoHurtCam.MC.getWindow().getHandle(), 1) != 1)
       return;
     ItemStack mainHandStack = NoHurtCam.MC.player.getMainHandStack();
     if (!mainHandStack.isOf(Items.END_CRYSTAL))
       return;
     if (this.stopOnKill.getValue() && isDeadBodyNearby())
       return;
     Vec3d camPos = NoHurtCam.MC.player.getEyePos();
     BlockHitResult blockHit = NoHurtCam.MC.world.raycast(new RaycastContext(camPos, camPos.add(RotationUtils.getCwHackLookVec().multiply(4.5D)), RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, (Entity) NoHurtCam.MC.player));
     HitResult HitResult = NoHurtCam.MC.crosshairTarget; if (HitResult instanceof EntityHitResult) { EntityHitResult hit = (EntityHitResult)HitResult;

       if (!dontBreakCrystal && (hit.getEntity() instanceof net.minecraft.entity.decoration.EndCrystalEntity || hit.getEntity() instanceof net.minecraft.entity.mob.SlimeEntity)) {

         this.crystalBreakClock = this.breakInterval.getValue();
         NoHurtCam.MC.interactionManager.attackEntity((PlayerEntity) NoHurtCam.MC.player, hit.getEntity());
         NoHurtCam.MC.player.swingHand(Hand.MAIN_HAND);
         NoHurtCam.CWHACK.crystalDataTracker().recordAttack(hit.getEntity());
       }  }

     if (BlockUtils.isBlock(Blocks.OBSIDIAN, blockHit.getBlockPos()))
     {
       if (CrystalUtils.canPlaceCrystalServer(blockHit.getBlockPos())) {

         ActionResult result = NoHurtCam.MC.interactionManager.interactBlock(NoHurtCam.MC.player, Hand.MAIN_HAND, blockHit);
         if (result.isAccepted() && result.shouldSwingHand())
           NoHurtCam.MC.player.swingHand(Hand.MAIN_HAND);
       }
     }
   }
}