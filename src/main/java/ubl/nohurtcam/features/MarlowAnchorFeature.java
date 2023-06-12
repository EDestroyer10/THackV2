package ubl.nohurtcam.features;

import ubl.nohurtcam.events.UpdateListener;
import ubl.nohurtcam.feature.Feature;
import ubl.nohurtcam.setting.BooleanSetting;
import ubl.nohurtcam.setting.IntegerSetting;
import ubl.nohurtcam.utils.BlockUtils;
import ubl.nohurtcam.utils.InventoryUtils;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;

import static ubl.nohurtcam.NoHurtCam.MC;

public class MarlowAnchorFeature extends Feature implements UpdateListener {
	public BooleanSetting stopOnShift = new BooleanSetting("stopOnShift", "wont anchor while ur shifiting", false, this);
	public IntegerSetting cooldown = new IntegerSetting("cooldown", "delay between anchors", 0, this);
	public IntegerSetting backupSlot = new IntegerSetting("backupSlot", "slot to go on when you dont have a totem in ur offhand", 1, this);

	private int clock;

	public MarlowAnchorFeature() {
		super("MarlowAnchor", "lalalalalalalalala alalala");
	}

	@Override
	protected void onEnable() {
		eventManager.add(UpdateListener.class, this);
		clock = 0;
	}

	@Override
	protected void onDisable() {
		eventManager.remove(UpdateListener.class, this);
	}

	@Override
	public void onUpdate() {
		PlayerInventory inv = MC.player.getInventory();

		if (MC.player.isSneaking() && stopOnShift.getValue())
			return;

		if (MC.currentScreen != null)
			return;

		if (MC.player.isUsingItem())
			return;

		boolean dontExplode = clock != 0;

		if (dontExplode)
			clock--;

		if (MC.crosshairTarget instanceof BlockHitResult hit) {
			BlockPos pos = hit.getBlockPos();
			if (dontExplode)
				return;
			clock = cooldown.getValue();
			if (BlockUtils.isBlock(Blocks.RESPAWN_ANCHOR, hit.getBlockPos())) {
				if (!BlockUtils.isAnchorCharged(pos)) {
					InventoryUtils.selectItemFromHotbar(Items.GLOWSTONE);
					ActionResult actionResult = MC.interactionManager.interactBlock(MC.player,  Hand.MAIN_HAND, hit);
					if (actionResult.isAccepted() && actionResult.shouldSwingHand()) {
						MC.player.swingHand(Hand.MAIN_HAND);
					}
				} else {
					if (InventoryUtils.hasItemInHotbar(Items.TOTEM_OF_UNDYING)) {
						InventoryUtils.selectItemFromHotbar(Items.TOTEM_OF_UNDYING);
						ActionResult actionResult = MC.interactionManager.interactBlock(MC.player,  Hand.MAIN_HAND, hit);
						if (actionResult.isAccepted() && actionResult.shouldSwingHand())
							MC.player.swingHand(Hand.MAIN_HAND);
					} else {
						inv.selectedSlot = backupSlot.getValue() - 1;
						ActionResult actionResult = MC.interactionManager.interactBlock(MC.player,  Hand.MAIN_HAND, hit);
						if (actionResult.isAccepted() && actionResult.shouldSwingHand())
							MC.player.swingHand(Hand.MAIN_HAND);
					}
				}
			}
		}
	}
}