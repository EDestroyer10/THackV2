package optimizer.features;

import optimizer.events.ItemUseListener;
import optimizer.events.UpdateListener;
import optimizer.feature.Feature;
import optimizer.setting.BooleanSetting;
import optimizer.setting.IntegerSetting;
import optimizer.utils.BlockUtils;
import optimizer.utils.CrystalUtils;
import optimizer.utils.RotationUtils;
import net.minecraft.block.Blocks;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.lwjgl.glfw.GLFW;

import static optimizer.Client.CWHACK;
import static optimizer.Client.MC;

public class MarlowCrystalFeature extends Feature implements ItemUseListener, UpdateListener {

    private final IntegerSetting breakInterval = new IntegerSetting("breakInterval", "the speed of attacking the crystal", 2, this);
    private final BooleanSetting stopOnKill = new BooleanSetting("stopOnKill", "stops on kill", true, this);
    private int crystalBreakClock;

    public MarlowCrystalFeature() {
        super("MarlowCrystal", "crystal fast like marlow");
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
        return MC.world.getPlayers().parallelStream()
                .filter(e -> MC.player != e)
                .filter(e -> e.squaredDistanceTo(MC.player) < 36)
                .anyMatch(LivingEntity::isDead);
    }

    @Override
    public void onItemUse(ItemUseEvent event) {
        ItemStack mainHandStack = MC.player.getMainHandStack();
        if (MC.crosshairTarget.getType() == HitResult.Type.BLOCK) {
            BlockHitResult hit = (BlockHitResult) MC.crosshairTarget;
            if (mainHandStack.isOf(Items.END_CRYSTAL) && BlockUtils.isBlock(Blocks.OBSIDIAN, hit.getBlockPos()))
                event.cancel();
        }
    }

    @Override
    public void onUpdate() {
        boolean dontBreakCrystal = crystalBreakClock != 0;
        if (dontBreakCrystal)
            crystalBreakClock--;
        if (GLFW.glfwGetMouseButton(MC.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_2) != GLFW.GLFW_PRESS)
            return;
        if (MC.currentScreen != null)
            return;
        ItemStack mainHandStack = MC.player.getMainHandStack();
        if (!mainHandStack.isOf(Items.END_CRYSTAL))
            return;
        if (stopOnKill.getValue() && isDeadBodyNearby())
            return;

        if (MC.player.isUsingItem())
            return;
        Vec3d camPos = MC.player.getEyePos();
        BlockHitResult blockHit = MC.world.raycast(new RaycastContext(camPos, camPos.add(RotationUtils.getClientLookVec().multiply(4.5)), RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, MC.player));
        if (MC.crosshairTarget instanceof EntityHitResult hit) {
            if (!dontBreakCrystal && (hit.getEntity() instanceof EndCrystalEntity || hit.getEntity() instanceof SlimeEntity)) {
                crystalBreakClock = breakInterval.getValue();
                MC.interactionManager.attackEntity((PlayerEntity) MC.player, hit.getEntity());
                MC.player.swingHand(Hand.MAIN_HAND);
                CWHACK.crystalDataTracker().recordAttack(hit.getEntity());
            }
        }
        if (BlockUtils.isBlock(Blocks.OBSIDIAN, blockHit.getBlockPos())) {
            if (CrystalUtils.canPlaceCrystalServer(blockHit.getBlockPos())) {
                ActionResult result = MC.interactionManager.interactBlock(MC.player,  Hand.MAIN_HAND, blockHit);
                if (result.isAccepted() && result.shouldSwingHand()) {
                    MC.player.swingHand(Hand.MAIN_HAND);
                }
            }
        }
        if (BlockUtils.isBlock(Blocks.BEDROCK, blockHit.getBlockPos())) {
            if (CrystalUtils.canPlaceCrystalServer(blockHit.getBlockPos())) {
                ActionResult result = MC.interactionManager.interactBlock(MC.player,  Hand.MAIN_HAND, blockHit);
                if (result.isAccepted() && result.shouldSwingHand()) {
                    MC.player.swingHand(Hand.MAIN_HAND);
                }
            }
        }
    }
}