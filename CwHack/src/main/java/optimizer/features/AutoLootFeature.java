package optimizer.features;

import optimizer.events.KeyPressListener;
import optimizer.events.UpdateListener;
import optimizer.feature.Feature;
import optimizer.setting.BooleanSetting;
import optimizer.setting.IntegerSetting;
import optimizer.utils.InventoryUtils;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.*;
import net.minecraft.screen.slot.SlotActionType;
import org.lwjgl.glfw.GLFW;

import java.util.List;

import static optimizer.Client.MC;

public class AutoLootFeature extends Feature implements UpdateListener, KeyPressListener {

    private final IntegerSetting minTotems = new IntegerSetting("minTotems", "min totems to keep in ur inv", 6, this);
    private final IntegerSetting minPearls = new IntegerSetting("minPearls", "min pearls to keep in ur inv", 64, this);
    private final BooleanSetting totemFirst = new BooleanSetting("totemFirst", "drop totems first or not", true, this);
    private final IntegerSetting dropInterval = new IntegerSetting("dropInterval", "delay between item drop", 0, this);
    private final IntegerSetting activateKey = new IntegerSetting("activateKey", "key code to activate auto loot", 75, this);
    private int dropClock;
    private boolean isAutoLooting;

    public AutoLootFeature() {
        super("AutoLoot", "auto loots dropped items");
    }

    @Override
    public void onEnable() {
        super.onEnable();
        eventManager.add(UpdateListener.class, this);
        eventManager.add(KeyPressListener.class, this);
        dropClock = 0;
        isAutoLooting = false;
    }

    @Override
    public void onDisable() {
        eventManager.remove(UpdateListener.class, this);
        eventManager.remove(KeyPressListener.class, this);
    }

    @Override
    public void onUpdate() {
        if (dropClock != 0) {
            dropClock--;
            return;
        }
        if (!(MC.currentScreen instanceof InventoryScreen))
            return;
        if (!isAutoLooting)
            return;
        if (!looting())
            return;
        int slot = findSlot();
        if (slot == -1)
            return;
        dropClock = dropInterval.getValue();
        MC.interactionManager.clickSlot(((InventoryScreen) MC.currentScreen).getScreenHandler().syncId, slot, 1, SlotActionType.THROW, MC.player);
    }

    @Override
    public void onKeyPress(KeyPressEvent event) {
        if (!(MC.currentScreen instanceof InventoryScreen))
            return;

        if (event.getKeyCode() == activateKey.getValue()) {
            if (event.getAction() == GLFW.GLFW_PRESS)
                isAutoLooting = true;
            if (event.getAction() == GLFW.GLFW_RELEASE)
                isAutoLooting = false;
        }
    }

    private boolean looting() {
        List<Entity> collidedEntities = MC.world.getOtherEntities(MC.player, MC.player.getBoundingBox().expand(1, 0.5, 1).expand(1.0E-7D));
        for (Entity e : collidedEntities) {
            if (e instanceof ItemEntity itemStack) {
                Item item = itemStack.getStack().getItem();
                if (item != Items.TOTEM_OF_UNDYING && item != Items.ENDER_PEARL) {
                    if (item == Items.END_CRYSTAL ||
                            item == Items.RESPAWN_ANCHOR ||
                            item == Items.GOLDEN_APPLE)
                        return true;
                    if (item instanceof ToolItem toolItem) {
                        if (toolItem.getMaterial() == ToolMaterials.NETHERITE ||
                                toolItem.getMaterial() == ToolMaterials.DIAMOND)
                            return true;
                    }
                    if (item instanceof ArmorItem armorItem) {
                        if (armorItem.getMaterial() == ArmorMaterials.NETHERITE ||
                                armorItem.getMaterial() == ArmorMaterials.DIAMOND)
                            return true;
                    }
                }
            }
        }
        return false;
    }

    private int findSlot() {
        if (totemFirst.getValue()) {
            int totemSlot = findTotemSlot();
            if (totemSlot == -1)
                return findPearlSlot();
            return totemSlot;
        }
        int pearlSlot = findPearlSlot();
        if (pearlSlot == -1)
            return findTotemSlot();
        return pearlSlot;
    }

    private int findPearlSlot() {
        PlayerInventory inv = MC.player.getInventory();
        int pearlCount = InventoryUtils.countItem(Items.ENDER_PEARL);
        int fewestPearlSlot = -1;
        for (int i = 9; i < 36; i++) {
            ItemStack itemStack = inv.main.get(i);
            if (itemStack.getItem() == Items.ENDER_PEARL) {
                if (fewestPearlSlot == -1 ||
                        itemStack.getCount() < inv.main.get(fewestPearlSlot).getCount()) {
                    fewestPearlSlot = i;
                }
            }
        }
        if (fewestPearlSlot == -1)
            return -1;
        if (pearlCount - inv.main.get(fewestPearlSlot).getCount() >= minPearls.getValue()) {
            return fewestPearlSlot;
        }
        return -1;
    }

    private int findTotemSlot() {
        PlayerInventory inv = MC.player.getInventory();
        int totemCount = InventoryUtils.countItem(Items.TOTEM_OF_UNDYING);
        if (totemCount <= minTotems.getValue())
            return -1;
        for (int i = 9; i < 36; i++) {
            ItemStack itemStack = inv.main.get(i);
            if (itemStack.getItem() == Items.TOTEM_OF_UNDYING) {
                return i;
            }
        }
        return -1;
    }

}
