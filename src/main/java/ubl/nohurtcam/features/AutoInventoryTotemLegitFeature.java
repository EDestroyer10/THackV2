package ubl.nohurtcam.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import ubl.nohurtcam.events.UpdateListener;
import ubl.nohurtcam.feature.Feature;
import ubl.nohurtcam.setting.IntegerSetting;
import ubl.nohurtcam.utils.AccessorUtils;

import static ubl.nohurtcam.NoHurtCam.MC;

public class AutoInventoryTotemLegitFeature extends Feature implements UpdateListener {

    private final IntegerSetting delay = new IntegerSetting("delay", "delay", 1, this);


    private final IntegerSetting totemSlot = new IntegerSetting("totemSlot", "totemSlot", 8, this);



    public AutoInventoryTotemLegitFeature() {
        super("AutoInventoryTotemLegit", "Automatically puts on totems for you when you are in inventory");
    }

    private int totemClock = 0;

    @Override
    public void onEnable() {
        super.onEnable();
        eventManager.add(UpdateListener.class, this);

        totemClock = 0;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        eventManager.remove(UpdateListener.class, this);
    }


    @Override
    public void onUpdate() {

        if (MC.currentScreen instanceof InventoryScreen) {

            InventoryScreen invScreen = (InventoryScreen) MC.currentScreen;

            if (getFocusedSlot() != null) {

                int slot = getFocusedSlot().getIndex();

                if (slot <= 35) {
                    if (!isTotem(totemSlot.getValue() - 1) && isTotem(slot)) {

                        if (totemClock != delay.getValue()) {
                            totemClock++;
                            return;
                        }

                        MC.interactionManager.clickSlot(
                                invScreen.getScreenHandler().syncId,
                                slot,
                                totemSlot.getValue() - 1,
                                SlotActionType.SWAP,
                                MC.player);
                        totemClock = 0;
                    }

                    if (!MC.player.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING)
                            && isTotem(slot)) {

                        if (totemClock != delay.getValue()) {
                            totemClock++;
                            return;
                        }

                        MC.interactionManager.clickSlot(
                                invScreen.getScreenHandler().syncId,
                                slot,
                                40,
                                SlotActionType.SWAP,
                                MC.player);
                        totemClock = 0;
                    }
                }

            }
        } else {
            totemClock = 0;
        }

    }

    private Slot getFocusedSlot() {
        final Screen screen = MinecraftClient.getInstance().currentScreen;
        final HandledScreen<?> gui = (HandledScreen<?>) screen;
        final Slot slot = AccessorUtils.getSlotUnderMouse(gui);
        return slot;
    }

    private boolean isTotem(int slot) {
        return MC.player.getInventory().main.get(slot).isOf(Items.TOTEM_OF_UNDYING);
    }
}