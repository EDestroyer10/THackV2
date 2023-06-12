package optimizer.features;

import optimizer.events.UpdateListener;
import optimizer.feature.Feature;
import optimizer.setting.IntegerSetting;
import net.minecraft.item.Items;
import org.lwjgl.glfw.GLFW;

import static optimizer.Client.MC;

public class BowSpamFeature extends Feature implements UpdateListener {

    private final IntegerSetting charge = new IntegerSetting("charge", "how long to charge the bow", 3, this);

    public BowSpamFeature() {
        super("BowSpam", "auto bows for you");
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
        if (GLFW.glfwGetMouseButton(MC.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_2) != GLFW.GLFW_PRESS)
            return;

        if (MC.currentScreen != null)
            return;

        if (MC.player.getMainHandStack().getItem() != Items.BOW)
            return;

        if (MC.player.getItemUseTime() >= charge.getValue()) {
            MC.player.stopUsingItem();
            MC.interactionManager.stopUsingItem(MC.player);
        }
    }


}
