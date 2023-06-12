package ubl.nohurtcam.events;

import ubl.nohurtcam.event.Event;
import ubl.nohurtcam.mixinterface.IClientPlayerInteractionManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.Window;
import net.minecraft.client.world.ClientWorld;

public enum Wrapper {
    CWHACK;

    public MinecraftClient getMinecraft() {
        return MinecraftClient.getInstance();
    }

    public ClientWorld getWorld() {
        return getMinecraft().world;
    }

    public ClientPlayerInteractionManager getClientPlayerInteractionManager() {
        return getMinecraft().interactionManager;
    }

    public IClientPlayerInteractionManager getIClientPlayerInteractionManager() { return (IClientPlayerInteractionManager)getMinecraft().interactionManager; }

    public Window getWindow() {
        return getMinecraft().getWindow();
    }

    public TextRenderer getTextRenderer() {
        return getMinecraft().textRenderer;
    }

    public WorldRenderer getWorldRenderer() {
        return getMinecraft().worldRenderer;
    }

    public GameRenderer getGameRenderer() {
        return getMinecraft().gameRenderer;
    }
    public enum EventType {
        PACKET_SEND(PacketEvent.class), PACKET_RECEIVE(PacketEvent.class);
        private final Class<? extends Event> expectedType = null;

        EventType(Class<PacketEvent> entityRenderEventClass) {
        }

        public Class<? extends Event> getExpectedType() {
            return expectedType;
        }
    }
}
