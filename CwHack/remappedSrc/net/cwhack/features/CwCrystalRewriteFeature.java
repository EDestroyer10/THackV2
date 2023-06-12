package net.cwhack.features;

import net.cwhack.events.ItemUseListener;
import net.cwhack.events.UpdateListener;
import net.cwhack.feature.Feature;
import net.cwhack.setting.BooleanSetting;
import net.cwhack.setting.IntegerSetting;
import net.cwhack.mixinterface.IMouse;
import net.cwhack.utils.BlockUtils;
import net.cwhack.utils.CrystalUtils;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.glfw.GLFW;

import static net.cwhack.CwHack.CWHACK;
import static net.cwhack.CwHack.MC;

public class CwCrystalRewriteFeature extends Feature implements ItemUseListener, UpdateListener {

	private final IntegerSetting crystalBreakInterval = new IntegerSetting("breakInterval", "the speed of attacking the crystal", 3, this);
	private final IntegerSetting crystalPlaceInterval = new IntegerSetting("placeInterval", "the speed of placing the crystal", 3, this);
	private final BooleanSetting stopOnKill = new BooleanSetting("stopOnKill", "stops on kill", false, this);
	private final BooleanSetting fakeCPS = new BooleanSetting("fakeCPS", "fakes your cps", false, this);

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
		IMouse mouse = (IMouse) MC.mouse;
		boolean dontPlaceCrystal = crystalPlaceClock != 0;
		boolean dontBreakCrystal = crystalBreakClock != 0;

		if (dontPlaceCrystal)
			crystalPlaceClock--;

		if (dontBreakCrystal)
			crystalBreakClock--;

		if (GLFW.glfwGetMouseButton(MC.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_2) != GLFW.GLFW_PRESS)
			return;

		if (MC.player.isUsingItem())
			return;

		if (MC.currentScreen != null)
			return;

		if (stopOnKill.getValue() && isDeadBodyNearby())
			return;

		ItemStack mainHandStack = MC.player.getMainHandStack();
		if (!mainHandStack.isOf(Items.END_CRYSTAL))
			return;

		if (MC.crosshairTarget instanceof EntityHitResult hit) {
			if (!dontBreakCrystal && hit.getEntity() instanceof EndCrystalEntity crystal) {
				crystalBreakClock = crystalBreakInterval.getValue();
				MC.interactionManager.attackEntity(MC.player, crystal);
				MC.player.swingHand(Hand.MAIN_HAND);
				CWHACK.getCrystalDataTracker().recordAttack(crystal);
				if (fakeCPS.getValue()) {
					mouse.click(MC.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_1, GLFW.GLFW_PRESS, 0);
					mouse.click(MC.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_1, GLFW.GLFW_RELEASE, 0);
				}
			}
		}
		if (MC.crosshairTarget instanceof BlockHitResult hit) {
			BlockPos block = hit.getBlockPos();
			if (!dontPlaceCrystal && CrystalUtils.canPlaceCrystalServer(block)) {
				crystalPlaceClock = crystalPlaceInterval.getValue();
				ActionResult result = MC.interactionManager.interactBlock(MC.player, MC.world, Hand.MAIN_HAND, hit);
				if (result.isAccepted() && result.shouldSwingHand()) {
					MC.player.swingHand(Hand.MAIN_HAND);
					if (fakeCPS.getValue()) {
						mouse.click(MC.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_2, GLFW.GLFW_PRESS, 0);
						mouse.click(MC.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_2, GLFW.GLFW_RELEASE, 0);
					}
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
