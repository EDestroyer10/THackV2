package ubl.nohurtcam.features;
/*
import ubl.nohurtcam.events.GUIRenderListener;
import ubl.nohurtcam.feature.Feature;
import net.minecraft.client.util.math.MatrixStack;

import static ubl.nohurtcam.NoHurtCam.MC;

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
        MC.textRenderer.drawWithShadow(matrices, "§lToast", 2, 2, 0x6FA8DC);
        MC.textRenderer.drawWithShadow(matrices, "§lHack", 2 + MC.textRenderer.getWidth("THack"), 2, 0xFFFFFF);
        MC.player.getHealth();
        if (MC.player.getHealth() >= 15) {
            MC.textRenderer.drawWithShadow(matrices, "HP: " + Math.round(MC.player.getHealth()), 2, 6 + MC.textRenderer.fontHeight * 2, 0xFF0FFF33);
        } else if (MC.player.getHealth() > 10) {
            MC.textRenderer.drawWithShadow(matrices, "HP: " + Math.round(MC.player.getHealth()), 2, 6 + MC.textRenderer.fontHeight * 2, 0xFFFF8C00);
        } else if (MC.player.isAlive()) {
            MC.textRenderer.drawWithShadow(matrices, "HP: " + Math.round(MC.player.getHealth()), 2, 6 + MC.textRenderer.fontHeight * 2, 0xFFFF0A0A);
        } else {
            MC.textRenderer.drawWithShadow(matrices, "HP: " + Math.round(MC.player.getHealth()), 2, 6 + MC.textRenderer.fontHeight * 2, 0xFF000000);
        }
        MC.getCurrentFps();
        MC.textRenderer.drawWithShadow(matrices, "FPS: " + MC.getCurrentFps(), 2, 4 + MC.textRenderer.fontHeight, 0xFFFFFF);
        MC.player.getBlockPos();
        MC.textRenderer.drawWithShadow(matrices, "XYZ: " + MC.player.getBlockPos().getX() + " " + MC.player.getBlockPos().getY() + " " + MC.player.getBlockPos().getZ(), 2, 8 + MC.textRenderer.fontHeight * 3, 0xFFFFFF);
        MC.player.getDisplayName();
        MC.textRenderer.drawWithShadow(matrices, "Username: " + MC.player.getDisplayName().getString(), 2, 10 + MC.textRenderer.fontHeight * 4, 0xFF6A54);
        matrices.pop();
    }
}*/