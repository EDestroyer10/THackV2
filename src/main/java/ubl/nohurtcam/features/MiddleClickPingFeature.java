package ubl.nohurtcam.features;

import ubl.nohurtcam.events.UpdateListener;
import ubl.nohurtcam.feature.Feature;
import ubl.nohurtcam.setting.BooleanSetting;
import ubl.nohurtcam.utils.ChatUtils;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.lwjgl.glfw.GLFW;

import static ubl.nohurtcam.NoHurtCam.MC;

public class MiddleClickPingFeature extends Feature implements UpdateListener {
    private final BooleanSetting includePrefix = new BooleanSetting("Include Prefix", "whether or not to include the prefix in the ping message", false, this);
    private boolean isMiddleClicking = false;

    public MiddleClickPingFeature() {
        super("MidClickPing", "Middle Click a player to get their ping.");
    }

    @Override
    public void onEnable() {
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
        HitResult hit = MC.crosshairTarget;
        if (hit.getType() != HitResult.Type.ENTITY) {
            return;
        }
        Entity target = ((EntityHitResult)hit).getEntity();
        if (!(target instanceof PlayerEntity)) {
            return;
        }
        if (GLFW.glfwGetMouseButton(MC.getWindow().getHandle(), 2) == 1 && !this.isMiddleClicking) {
            this.isMiddleClicking = true;
            if (this.includePrefix.getValue()) {
                ChatUtils.plainMessageWithPrefix(target.getEntityName() + "'s §l§bping is " + MiddleClickPingFeature.getPing(target));
            } else {
                ChatUtils.sendPlainMessage(target.getEntityName() + "'s §l§bping is " + MiddleClickPingFeature.getPing(target));
            }
        }
        if (GLFW.glfwGetMouseButton(MC.getWindow().getHandle(), 2) == 0 && this.isMiddleClicking) {
            this.isMiddleClicking = false;
        }
    }

    public static int getPing(Entity player) {
        if (MC.getNetworkHandler() == null) {
            return 0;
        }
        PlayerListEntry playerListEntry = MC.getNetworkHandler().getPlayerListEntry(player.getUuid());
        if (playerListEntry == null) {
            return 0;
        }
        return playerListEntry.getLatency();
    }
}