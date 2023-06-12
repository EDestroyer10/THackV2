package ubl.nohurtcam.features;

import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.lwjgl.glfw.GLFW;
import ubl.nohurtcam.events.UpdateListener;
import ubl.nohurtcam.feature.Feature;
import ubl.nohurtcam.keybind.Keybind;
import ubl.nohurtcam.setting.BooleanSetting;
import ubl.nohurtcam.setting.IntegerSetting;

import static ubl.nohurtcam.NoHurtCam.MC;

public class AutoInventoryTotemLegitFeature extends Feature implements UpdateListener {

    private final IntegerSetting delay = new IntegerSetting("delay", "delay", 1, this);


    private final IntegerSetting totemSlot = new IntegerSetting("totemSlot", "totemSlot", 9, this);


    private final BooleanSetting activateOnKey = new BooleanSetting("activateOnKey", "activateOnKey", false, this);


    private final Keybind activateKey = new Keybind(
            "Keybind",
            GLFW.GLFW_KEY_V,
            false,
            false,
            null
    );

    public AutoInventoryTotemLegitFeature() {
        super("Legit Retotem", "Automatically puts on totems for you when you are in inventory");
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

    private boolean check() {
        return (activateOnKey.getValue()
                && (GLFW.glfwGetKey(MC.getWindow().getHandle(),
                activateKey.getKey()) == GLFW.GLFW_PRESS))
                || (!activateOnKey.getValue());
    }

    @Override
    public void onUpdate() {

        if (MC.currentScreen instanceof InventoryScreen) {

            InventoryScreen invScreen = (InventoryScreen) MC.currentScreen;

            if (getFocusedSlot(invScreen) != null && check()) {

                int slot = getFocusedSlot(invScreen).getIndex();

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

    private Slot getFocusedSlot(InventoryScreen screen) {
        //return screen.focusedSlot();
        return null;
    }

    private boolean isTotem(int slot) {
        return MC.player.getInventory().main.get(slot).isOf(Items.TOTEM_OF_UNDYING);
    }
}