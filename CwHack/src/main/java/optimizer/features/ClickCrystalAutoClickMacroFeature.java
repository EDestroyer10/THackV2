package optimizer.features;

import optimizer.events.UpdateListener;
import optimizer.feature.Feature;
import optimizer.setting.IntegerSetting;
import optimizer.utils.InventoryUtils;
import optimizer.utils.Timer;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;


import static optimizer.Client.MC;

public class ClickCrystalAutoClickMacroFeature extends Feature implements UpdateListener {


    private static Timer delayTimer = new Timer();
    private final IntegerSetting delay = new IntegerSetting("delay", "a", 20, this);

    public ClickCrystalAutoClickMacroFeature() {
        super("MacroCC", "auto click end crystal");
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
                ScreenHandler handler = MC.player.currentScreenHandler;

                for (int i = 0; i < handler.slots.size() - InventoryUtils.MAIN_END; i++) {
                    Slot slot = handler.slots.get(i);
                    ItemStack stack = slot.getStack();
                    if (stack.getItem() != Items.RED_STAINED_GLASS_PANE)
                        if (stack.getItem() != Items.GREEN_STAINED_GLASS_PANE) {
                            if (delayTimer.hasTimeElapsed(delay.getValue(), true))
                            {
                                MC.interactionManager.clickSlot(handler.syncId, slot.id, 0, SlotActionType.QUICK_MOVE, MC.player);
                            }
                        }
                }
            }
        }
    }
}