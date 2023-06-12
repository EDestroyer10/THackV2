/*    */
package ubl.nohurtcam.features;
/*    */ import ubl.nohurtcam.NoHurtCam;
import ubl.nohurtcam.events.ItemUseListener;
import ubl.nohurtcam.events.UpdateListener;
import ubl.nohurtcam.feature.Feature;
/*    */ import ubl.nohurtcam.setting.IntegerSetting;
import ubl.nohurtcam.utils.BlockUtils;
import ubl.nohurtcam.utils.CrystalUtils;
import ubl.nohurtcam.utils.RotationUtils;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.lwjgl.glfw.GLFW;

 public class AntiAntiCrystalFeature extends Feature implements ItemUseListener, UpdateListener {
   private final IntegerSetting placeInterval;

   public AntiAntiCrystalFeature() {
     super("FastCrystals", "make crystal placing fast");


     this.placeInterval = new IntegerSetting("placeInterval", "the delay between placing crystals (in tick)", 2, this);

     this.delay = 0;
   }

   private int delay;

   public void onEnable() {
     super.onEnable();
     eventManager.add(UpdateListener.class, this);
     eventManager.add(ItemUseListener.class, this);

     this.delay = 0;
   }


   public void onDisable() {
     super.onDisable();
     eventManager.remove(UpdateListener.class, this);
     eventManager.remove(ItemUseListener.class, this);
   }


   public void onItemUse(ItemUseEvent event) {
     ItemStack mainHandStack = NoHurtCam.MC.player.getMainHandStack();
     if (NoHurtCam.MC.crosshairTarget.getType() == HitResult.Type.BLOCK) {

       BlockHitResult hit = (BlockHitResult) NoHurtCam.MC.crosshairTarget;
       if (mainHandStack.isOf(Items.END_CRYSTAL) && (
               BlockUtils.isBlock(Blocks.OBSIDIAN, hit.getBlockPos()) ||
                       BlockUtils.isBlock(Blocks.BEDROCK, hit.getBlockPos()))) {
         event.cancel();
       }
     }
   }


   public void onUpdate() {
     if (!NoHurtCam.MC.player.getMainHandStack().isOf(Items.END_CRYSTAL))
       return;
     if (GLFW.glfwGetMouseButton(NoHurtCam.MC.getWindow().getHandle(), 1) != 1)
       return;
     if (this.delay != this.placeInterval.getValue().intValue()) {
       this.delay++;

       return;
     }
     this.delay = 0;

     Vec3d camPos = NoHurtCam.MC.player.getEyePos();
     BlockHitResult blockHit = NoHurtCam.MC.world.raycast(new RaycastContext(camPos, camPos.add(RotationUtils.getCwHackLookVec().multiply(4.5D)), RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, (Entity) NoHurtCam.MC.player));

     if ((BlockUtils.isBlock(Blocks.OBSIDIAN, blockHit.getBlockPos()) || BlockUtils.isBlock(Blocks.BEDROCK, blockHit.getBlockPos())) &&
             CrystalUtils.canPlaceCrystalServer(blockHit.getBlockPos())) {
       ActionResult result = NoHurtCam.MC.interactionManager.interactBlock(NoHurtCam.MC.player, Hand.MAIN_HAND, blockHit);
       if (result.isAccepted() && result.shouldSwingHand()) NoHurtCam.MC.player.swingHand(Hand.MAIN_HAND);
     }
   }
 }