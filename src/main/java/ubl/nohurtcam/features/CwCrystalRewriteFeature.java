package ubl.nohurtcam.features;

import ubl.nohurtcam.events.ItemUseListener;
import ubl.nohurtcam.events.UpdateListener;
import ubl.nohurtcam.feature.Feature;
import ubl.nohurtcam.setting.BooleanSetting;
import ubl.nohurtcam.setting.IntegerSetting;
import ubl.nohurtcam.utils.BlockUtils;
import ubl.nohurtcam.utils.CrystalUtils;
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
import net.minecraft.util.math.BlockPos;
import org.lwjgl.glfw.GLFW;

import static ubl.nohurtcam.NoHurtCam.MC;

public class CwCrystalRewriteFeature extends Feature implements ItemUseListener, UpdateListener {

	private final IntegerSetting breakInterval = new IntegerSetting("breakInterval", "the speed of attacking the crystal", 2, this);
	private final IntegerSetting placeInterval = new IntegerSetting("placeInterval", "the speed of placing the crystal", 2, this);
	private final BooleanSetting stopOnKill = new BooleanSetting("stopOnKill", "stops on kill", false, this);
	private final BooleanSetting activateOnRightClick = new BooleanSetting("activateOnRightClick", "wow", false, this);
	private int crystalBreakClock;
	private int crystalPlaceClock;


	public CwCrystalRewriteFeature() {
		super("CwCrystal", "rewriting for vulcan bypass");
	}

	@Override
	public void onEnable() {
		crystalBreakClock = 0;
		crystalPlaceClock = 0;
		eventManager.add(ItemUseListener.class, this);
		eventManager.add(UpdateListener.class, this);
	}

	@Override
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
		if (this.activateOnRightClick.getValue().booleanValue() && GLFW.glfwGetMouseButton(MC.getWindow().getHandle(), 1) != 1) {
			return;
		}
		ItemStack mainHandStack = MC.player.getMainHandStack();
		if (!mainHandStack.isOf(Items.END_CRYSTAL)) {
			return;
		}
		if (this.stopOnKill.getValue() && this.isDeadBodyNearby()) {
			return;
		}
		HitResult hitResult = MC.crosshairTarget;
		if (hitResult instanceof EntityHitResult) {
			hit = (EntityHitResult) hitResult;
			if (!dontBreakCrystal && (hit.getEntity() instanceof EndCrystalEntity || hit.getEntity() instanceof SlimeEntity)) {
				this.crystalBreakClock = this.breakInterval.getValue();
				MC.interactionManager.attackEntity((PlayerEntity) MC.player, hit.getEntity());
				MC.player.swingHand(Hand.MAIN_HAND);
			}
		}
		if ((hitResult = MC.crosshairTarget) instanceof BlockHitResult) {
			BlockHitResult innerHit = (BlockHitResult) hitResult;
			BlockPos block = innerHit.getBlockPos();
			if (!dontPlaceCrystal && CrystalUtils.canPlaceCrystalServer(block)) {
				this.crystalPlaceClock = this.placeInterval.getValue();
				ActionResult result = MC.interactionManager.interactBlock(MC.player, Hand.MAIN_HAND, (BlockHitResult) innerHit);
				if (result.isAccepted() && result.shouldSwingHand()) {
					MC.player.swingHand(Hand.MAIN_HAND);
				}
			}
		}
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
}
