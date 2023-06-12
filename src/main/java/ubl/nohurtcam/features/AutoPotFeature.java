package ubl.nohurtcam.features;

import ubl.nohurtcam.events.UpdateListener;
import ubl.nohurtcam.feature.Feature;
import ubl.nohurtcam.setting.IntegerSetting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

import static ubl.nohurtcam.NoHurtCam.MC;

public class AutoPotFeature extends Feature implements UpdateListener
{

    private final IntegerSetting health = new IntegerSetting("Health", "a", 4, this);
    private final IntegerSetting delay = new IntegerSetting("Delay", "a", 0, this);
    Float prevPitch;
    int potSlot;
    int preSlot;
    int ticksAfterPotion = 0;

    public AutoPotFeature() {
        super("AutoPotFeature", "automatically aims for you");
    }
    @Override
    public void onEnable()
    {
        eventManager.add(UpdateListener.class, this);
    }

    @Override
    public void onDisable()
    {
        eventManager.remove(UpdateListener.class, this);
    }

    @Override
    public void onUpdate() {
        if (MC.player.getHealth() <= health.getValue()) {
            if (hotbarContainsPotions()) {
                if (ticksAfterPotion > 0) {
                    ticksAfterPotion++;
                    if (ticksAfterPotion > delay.getValue()) {
                        ticksAfterPotion = 0;
                    }
                    return;
                }
                prevPitch = MC.player.getPitch();

                MC.player.setPitch(90.0f);
                MC.player.getInventory().selectedSlot = potSlot;

                MC.interactionManager.interactItem(MC.player, Hand.MAIN_HAND);
                ticksAfterPotion++;

                MC.player.setPitch(prevPitch);
                MC.player.getInventory().selectedSlot = preSlot;
            }
        } else {
            preSlot = MC.player.getInventory().selectedSlot;
            ticksAfterPotion = 0;
        }
    }

    public boolean hotbarContainsPotions() {
        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = MC.player.getInventory().getStack(i);
            if (itemStack.getItem() == Items.SPLASH_POTION) {
                potSlot = i;
                return true;
            }
        }
        return false;
    }
}