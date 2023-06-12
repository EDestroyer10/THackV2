package optimizer.features;

import optimizer.events.GUIRenderListener;
import optimizer.feature.Feature;
import optimizer.utils.PacketHelper;
import net.minecraft.client.util.math.MatrixStack;

public class HUDFeature extends Feature implements GUIRenderListener {


    public HUDFeature() {
        super("HUD", "Toasthack");
    }

    @Override
    public void onEnable() {
        super.onEnable();
        eventManager.add(GUIRenderListener.class, this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        eventManager.remove(GUIRenderListener.class, this);
    }


    @Override
    public void onRenderGUI(GUIRenderListener.GUIRenderEvent event) {
        MatrixStack matrices = event.getMatrixStack();
        matrices.push();
        matrices.translate(2, 2, 2);
        PacketHelper.mc.textRenderer.drawWithShadow(matrices, "Toast", 2, 2, 0x6FA8DC);
        PacketHelper.mc.textRenderer.drawWithShadow(matrices, "Hack", 2 + PacketHelper.mc.textRenderer.getWidth("THack"), 2, 0xFFFFFF);
        PacketHelper.mc.player.getHealth();
        if (PacketHelper.mc.player.getHealth() >= 15) {
            PacketHelper.mc.textRenderer.drawWithShadow(matrices, "HP: " + Math.round(PacketHelper.mc.player.getHealth()), 2, 6 + PacketHelper.mc.textRenderer.fontHeight * 2, 0xFF0FFF33);
        } else if (PacketHelper.mc.player.getHealth() > 10) {
            PacketHelper.mc.textRenderer.drawWithShadow(matrices, "HP: " + Math.round(PacketHelper.mc.player.getHealth()), 2, 6 + PacketHelper.mc.textRenderer.fontHeight * 2, 0xFFFF8C00);
        } else if (PacketHelper.mc.player.isAlive()) {
            PacketHelper.mc.textRenderer.drawWithShadow(matrices, "HP: " + Math.round(PacketHelper.mc.player.getHealth()), 2, 6 + PacketHelper.mc.textRenderer.fontHeight * 2, 0xFFFF0A0A);
        } else {
            PacketHelper.mc.textRenderer.drawWithShadow(matrices, "HP: " + Math.round(PacketHelper.mc.player.getHealth()), 2, 6 + PacketHelper.mc.textRenderer.fontHeight * 2, 0xFF000000);
        }
        PacketHelper.mc.getCurrentFps();
        PacketHelper.mc.textRenderer.drawWithShadow(matrices, "FPS: " + PacketHelper.mc.getCurrentFps(), 2, 4 + PacketHelper.mc.textRenderer.fontHeight, 0xFFFFFF);
        PacketHelper.mc.player.getBlockPos();
        PacketHelper.mc.textRenderer.drawWithShadow(matrices, "XYZ: " + PacketHelper.mc.player.getBlockPos().getX() + " " + PacketHelper.mc.player.getBlockPos().getY() + " " + PacketHelper.mc.player.getBlockPos().getZ(), 2, 8 + PacketHelper.mc.textRenderer.fontHeight * 3, 0xFFFFFF);
        PacketHelper.mc.player.getDisplayName();
        PacketHelper.mc.textRenderer.drawWithShadow(matrices, "Username: " + PacketHelper.mc.player.getDisplayName().getString(), 2, 10 + PacketHelper.mc.textRenderer.fontHeight * 4, 0xFF6A54);
        matrices.pop();
    }
}
