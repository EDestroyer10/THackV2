package optimizer.features;

import optimizer.events.UpdateListener;
import optimizer.feature.Feature;
import optimizer.setting.IntegerSetting;
import optimizer.mixin.ClientAccessorMixin;
import net.minecraft.item.Items;

import static optimizer.Client.MC;

public class FastExpFeature extends Feature implements UpdateListener {
    private final IntegerSetting cooldown = new IntegerSetting("cooldown", "cooldown between xp throws", 0, this);

    public FastExpFeature() {
        super("FastEXP", "fast af exp throws");
    }

    @Override
    protected void onEnable() {
        eventManager.add(UpdateListener.class, this);
    }

    @Override
    protected void onDisable() {
        eventManager.remove(UpdateListener.class, this);

    }

    @Override
    public void onUpdate() {

        if (MC.currentScreen != null)
            return;

        if (MC.player.getMainHandStack().getItem() != Items.EXPERIENCE_BOTTLE)
            return;

        int cooldownTicks = Math.min(((ClientAccessorMixin) MC).getItemUseCooldown(), cooldown.getValue());
        ((ClientAccessorMixin) MC).setItemUseCooldown(cooldownTicks);

    }
}
