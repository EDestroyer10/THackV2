package ubl.nohurtcam.features;

import ubl.nohurtcam.NoHurtCam;
import ubl.nohurtcam.events.ItemUseListener;
import ubl.nohurtcam.events.UpdateListener;
import ubl.nohurtcam.feature.Feature;
import ubl.nohurtcam.setting.BooleanSetting;
import ubl.nohurtcam.setting.IntegerSetting;
import ubl.nohurtcam.utils.BlockUtils;
import ubl.nohurtcam.utils.CrystalUtils;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.glfw.GLFW;

public class AutoDtapFeature extends Feature implements UpdateListener, ItemUseListener {
    private final IntegerSetting placeInterval = new IntegerSetting("PlaceInterval", "the interval between placing crystals (in tick)", 3, this);
    private final IntegerSetting MaxCrystals = new IntegerSetting("MaxCrystals", "the interval between breaking crystals (in tick)", 2, this);
    private final IntegerSetting breakInterval = new IntegerSetting("BreakInterval", "the interval between breaking crystals (in tick)", 2, this);
    private final BooleanSetting activateOnRightClick = new BooleanSetting("activateOnRightClick", "will only activate on right click when enabled", true, this);

    private final BooleanSetting stopOnKill = new BooleanSetting("stopOnKill", "automatically stops crystalling when someone close to you dies", true, this);

    private int crystalPlaceClock = 0;
    private int crystalBreakClock = 0;

    public AutoDtapFeature() {
        super("AutoDtap", "Double pop like theo404");
    }

    @Override
    public void onEnable() {
        super.onEnable();
        eventManager.add(UpdateListener.class, this);
        eventManager.add(ItemUseListener.class, this);
        this.crystalPlaceClock = 0;
        this.crystalBreakClock = 0;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        eventManager.remove(UpdateListener.class, this);
        eventManager.remove(ItemUseListener.class, this);
    }

    private boolean isDeadBodyNearby() {
        return NoHurtCam.MC.world.getPlayers().parallelStream().filter(e -> NoHurtCam.MC.player != e).filter(e -> e.squaredDistanceTo((Entity) NoHurtCam.MC.player) < 36.0).anyMatch(LivingEntity::isDead);
    }

    @Override
    public void onUpdate() {
        EntityHitResult hit;
        boolean dontBreakCrystal;
        boolean dontPlaceCrystal = this.crystalPlaceClock != 0;
        boolean bl = dontBreakCrystal = this.crystalBreakClock != 0;
        if (dontPlaceCrystal) {
            --this.crystalPlaceClock;
        }
        if (dontBreakCrystal) {
            --this.crystalBreakClock;
        }
        if (this.activateOnRightClick.getValue().booleanValue() && GLFW.glfwGetMouseButton(NoHurtCam.MC.getWindow().getHandle(), 1) != 1) {
            return;
        }
        ItemStack mainHandStack = NoHurtCam.MC.player.getMainHandStack();
        if (!mainHandStack.isOf(Items.END_CRYSTAL)) {
            return;
        }
        if (this.stopOnKill.getValue().booleanValue() && this.isDeadBodyNearby()) {
            return;
        }
        HitResult hitResult = NoHurtCam.MC.crosshairTarget;
        if (hitResult instanceof EntityHitResult) {
            hit = (EntityHitResult)hitResult;
            if (!dontBreakCrystal && (hit.getEntity() instanceof EndCrystalEntity || hit.getEntity() instanceof SlimeEntity)) {
                this.crystalBreakClock = this.breakInterval.getValue();
                NoHurtCam.MC.interactionManager.attackEntity((PlayerEntity) NoHurtCam.MC.player, hit.getEntity());
                NoHurtCam.MC.player.swingHand(Hand.MAIN_HAND);
                NoHurtCam.CWHACK.crystalDataTracker().recordAttack(hit.getEntity());
            }
        }
        if ((hitResult = NoHurtCam.MC.crosshairTarget) instanceof BlockHitResult) {
            BlockHitResult innerHit = (BlockHitResult) hitResult;
            BlockPos block = innerHit.getBlockPos();
            if (!dontPlaceCrystal && CrystalUtils.canPlaceCrystalServer(block)) {
                this.crystalPlaceClock = this.placeInterval.getValue();
                ActionResult result = NoHurtCam.MC.interactionManager.interactBlock(NoHurtCam.MC.player, Hand.MAIN_HAND, (BlockHitResult)innerHit);
                if (result.isAccepted() && result.shouldSwingHand()) {
                    NoHurtCam.MC.player.swingHand(Hand.MAIN_HAND);
                }
            }
        }
    }

    @Override
    public void onItemUse(ItemUseEvent event) {
        ItemStack mainHandStack = NoHurtCam.MC.player.getMainHandStack();
        if (NoHurtCam.MC.crosshairTarget.getType() == HitResult.Type.BLOCK) {
            BlockHitResult hit = (BlockHitResult) NoHurtCam.MC.crosshairTarget;
            if (mainHandStack.isOf(Items.END_CRYSTAL) && BlockUtils.isBlock(Blocks.OBSIDIAN, hit.getBlockPos())) {
                event.cancel();
            }
        }
    }
}