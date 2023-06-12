package ubl.nohurtcam.features;

import ubl.nohurtcam.NoHurtCam;
import ubl.nohurtcam.events.UpdateListener;
import ubl.nohurtcam.feature.Feature;
import ubl.nohurtcam.setting.BooleanSetting;
import ubl.nohurtcam.setting.DecimalSetting;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;

public class AutoXpFeature extends Feature implements UpdateListener {
    private int DropClock = 0;
    private final BooleanSetting ActivateOnRightClick = new BooleanSetting("activate On Right Click", "When deactivated, XP will also splash in Inventory Screen", true, this);
    private final BooleanSetting OnlyMainScreen = new BooleanSetting("Only Main Screen", "When deactivated, XP will also splash in Inventory Screen", true, this);
    private final DecimalSetting speed = new DecimalSetting("speed", "Throwing Speed", 1, this);

    public AutoXpFeature() {
        super("AutoEXP", "automatically splashes XP When you hold them");
    }

    @Override
    public void onEnable() {
        this.DropClock = 0;
        super.onEnable();
        eventManager.add(UpdateListener.class, this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        eventManager.remove(UpdateListener.class, this);
    }

    @Override
    public void onUpdate() {
        if (NoHurtCam.MC.currentScreen != null && this.OnlyMainScreen.getValue()) {
            return;
        }
        if (this.ActivateOnRightClick.getValue() && GLFW.glfwGetMouseButton(NoHurtCam.MC.getWindow().getHandle(), 1) != 1) {
            return;
        }
        if (!NoHurtCam.MC.player.getMainHandStack().isOf(Items.EXPERIENCE_BOTTLE)) {
            return;
        }
        ++this.DropClock;
        if ((double)this.DropClock != this.speed.getValue() + 1.0) {
            return;
        }
        this.DropClock = 0;
        NoHurtCam.MC.interactionManager.interactItem((PlayerEntity) NoHurtCam.MC.player, Hand.MAIN_HAND);
        NoHurtCam.MC.player.swingHand(Hand.MAIN_HAND);
    }
}