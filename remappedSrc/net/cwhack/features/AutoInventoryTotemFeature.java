package net.cwhack.features;

import net.cwhack.events.UpdateListener;
import net.cwhack.feature.Feature;
import net.cwhack.setting.BooleanSetting;
import net.cwhack.setting.IntegerSetting;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.ToolItem;
import net.minecraft.screen.slot.SlotActionType;

import static net.cwhack.CwHack.MC;

public class AutoInventoryTotemFeature extends Feature implements UpdateListener {
    private final IntegerSetting totemSlot = new IntegerSetting("totemSlot", "slot to put the totem on", 1, this);
    private final BooleanSetting useTotemSlot = new BooleanSetting("useTotemSlot", "if the totem slot should be used", false, this);

    private final BooleanSetting dontSwapIfTool = new BooleanSetting("dontSwapIfTool", "dont swap offhand item if its a tool", false, this);
    private int tslot;

    public AutoInventoryTotemFeature() {
        super("AutoInventoryTotem", "puts a totem in ur offhand and totem slot when u open ur inv");
    }

    @Override
    protected void onEnable() {
        eventManager.add(UpdateListener.class, this);
        tslot = 0;
    }

    @Override
    protected void onDisable() {
        eventManager.remove(UpdateListener.class, this);
    }

    @Override
    public void onUpdate() {
        PlayerInventory inv = MC.player.getInventory();

        if (!(MC.currentScreen instanceof InventoryScreen))
            return;

        if (inv.offHand.get(0).getItem() != Items.TOTEM_OF_UNDYING) {
            int slot = findTotemSlot();
            if (slot != -1) {
                if (!(inv.offHand.get(0).getItem() instanceof ShieldItem || inv.offHand.get(0).getItem() instanceof ToolItem && dontSwapIfTool.getValue()))
                    MC.interactionManager.clickSlot(((InventoryScreen) MC.currentScreen).getScreenHandler().syncId, slot, 40, SlotActionType.SWAP, MC.player);
            }
        }

        ItemStack mainHand = inv.main.get(inv.selectedSlot);
        if (useTotemSlot.getValue()) {
            tslot = totemSlot.getValue() - 1;
            int slot = findTotemSlot();
            if (slot != -1) {
                MC.interactionManager.clickSlot(((InventoryScreen) MC.currentScreen).getScreenHandler().syncId, slot, tslot, SlotActionType.SWAP, MC.player);
            }
        } else {
            if (mainHand.isEmpty()) {
                int slot = findTotemSlot();
                if (slot != -1) {
                    MC.interactionManager.clickSlot(((InventoryScreen) MC.currentScreen).getScreenHandler().syncId, slot, inv.selectedSlot, SlotActionType.SWAP, MC.player);
                }
            }
        }
    }

    private int findTotemSlot() {
        PlayerInventory inv = MC.player.getInventory();
        for (int i = 9; i < 36; i++) {
            if (inv.main.get(i).getItem() == Items.TOTEM_OF_UNDYING)
                return i;
        }
        return -1;

    }

}