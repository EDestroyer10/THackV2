package optimizer.features;

import optimizer.Client;
import optimizer.events.ItemUseListener;
import optimizer.events.UpdateListener;
import optimizer.feature.Feature;
import optimizer.setting.BooleanSetting;
import optimizer.utils.BlockUtils;
import optimizer.utils.CrystalUtils;
import optimizer.setting.IntegerSetting;
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
    private final IntegerSetting placeInterval = new IntegerSetting("place Interval", "the speed of placing the crystal", 3, this);
    private final IntegerSetting MaxCrystals = new IntegerSetting("Max crystal", "how much crystals to place", 2, this);
    private final IntegerSetting breakInterval = new IntegerSetting("breakInterval", "the speed of attacking the crystal", 2, this);
    private final BooleanSetting activateOnRightClick = new BooleanSetting("right click", "activates on right click", true, this);
    private final BooleanSetting stopOnKill = new BooleanSetting("stopOnKill", "stops on kill", true, this);
    private int crystalPlaceClock = 0;
    private int crystalBreakClock = 0;

    public AutoDtapFeature() {
        super("AutoDTap", "totempopper");
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
        return Client.MC.world.getPlayers().parallelStream().filter(e -> Client.MC.player != e).filter(e -> e.squaredDistanceTo((Entity) Client.MC.player) < 36.0).anyMatch(LivingEntity::isDead);
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
        if (this.activateOnRightClick.getValue().booleanValue() && GLFW.glfwGetMouseButton(Client.MC.getWindow().getHandle(), 1) != 1) {
            return;
        }
        ItemStack mainHandStack = Client.MC.player.getMainHandStack();
        if (!mainHandStack.isOf(Items.END_CRYSTAL)) {
            return;
        }
        if (this.stopOnKill.getValue().booleanValue() && this.isDeadBodyNearby()) {
            return;
        }
        HitResult hitResult = Client.MC.crosshairTarget;
        if (hitResult instanceof EntityHitResult) {
            hit = (EntityHitResult) hitResult;
            if (!dontBreakCrystal && (hit.getEntity() instanceof EndCrystalEntity || hit.getEntity() instanceof SlimeEntity)) {
                this.crystalBreakClock = this.breakInterval.getValue();
                Client.MC.interactionManager.attackEntity((PlayerEntity) Client.MC.player, hit.getEntity());
                Client.MC.player.swingHand(Hand.MAIN_HAND);
                Client.CWHACK.crystalDataTracker().recordAttack(hit.getEntity());
            }
        }
        if ((hitResult = Client.MC.crosshairTarget) instanceof BlockHitResult) {
            BlockHitResult innerHit = (BlockHitResult) hitResult;
            BlockPos block = innerHit.getBlockPos();
            if (!dontPlaceCrystal && CrystalUtils.canPlaceCrystalServer(block)) {
                this.crystalPlaceClock = this.placeInterval.getValue();
                ActionResult result = Client.MC.interactionManager.interactBlock(Client.MC.player, Hand.MAIN_HAND, (BlockHitResult) innerHit);
                if (result.isAccepted() && result.shouldSwingHand()) {
                    Client.MC.player.swingHand(Hand.MAIN_HAND);
                }
            }
        }
    }

    @Override
    public void onItemUse(ItemUseListener.ItemUseEvent event) {
        ItemStack mainHandStack = Client.MC.player.getMainHandStack();
        if (Client.MC.crosshairTarget.getType() == HitResult.Type.BLOCK) {
            BlockHitResult hit = (BlockHitResult) Client.MC.crosshairTarget;
            if (mainHandStack.isOf(Items.END_CRYSTAL) && BlockUtils.isBlock(Blocks.OBSIDIAN, hit.getBlockPos())) {
                event.cancel();
            }
        }
    }
}


