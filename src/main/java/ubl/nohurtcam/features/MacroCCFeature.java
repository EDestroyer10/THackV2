package ubl.nohurtcam.features;

import ubl.nohurtcam.events.UpdateListener;
import ubl.nohurtcam.feature.Feature;
import ubl.nohurtcam.setting.IntegerSetting;
import ubl.nohurtcam.utils.InventoryUtils;
import ubl.nohurtcam.utils.Timer;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;

import static ubl.nohurtcam.NoHurtCam.MC;

public class MacroCCFeature extends Feature implements UpdateListener {


	private final Timer delayTimer = new Timer();
	private final IntegerSetting delay = new IntegerSetting("delay", "a", 20, this);

	public MacroCCFeature() {
		super("MacroCCFeature", "Steals items from chests");
	}

	@Override
	public void onEnable() {
		eventManager.add(UpdateListener.class, this);
	}

	@Override
	public void onDisable() {
		eventManager.remove(UpdateListener.class, this);
	}

	@Override
	public void onUpdate() {
		if (MC.currentScreen instanceof GenericContainerScreen) {
			 {
				 assert MC.player != null;
				 ScreenHandler handler = MC.player.currentScreenHandler;

				for (int i = 0; i < handler.slots.size() - InventoryUtils.MAIN_END; i++) {
					Slot slot = handler.slots.get(i);
					ItemStack stack = slot.getStack();
					if (stack.getItem() != Items.RED_STAINED_GLASS_PANE)
						if (stack.getItem() != Items.GREEN_STAINED_GLASS_PANE)
							if (stack.getItem() != Items.BARRIER) {
							if (delayTimer.hasTimeElapsed(delay.getValue(), true))
						{
							MC.interactionManager.clickSlot(handler.syncId, slot.id, 0, SlotActionType.PICKUP, MC.player);
						}
					}
				}
			}
		}
	}
}