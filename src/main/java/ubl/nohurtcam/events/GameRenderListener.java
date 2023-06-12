package ubl.nohurtcam.events;

import ubl.nohurtcam.event.Event;
import ubl.nohurtcam.event.Listener;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;

public interface GameRenderListener extends Listener {
    public void onGameRender(MatrixStack var1, float var2);

    public static class GameRenderEvent
            extends Event<GameRenderListener> {
        private MatrixStack matrixStack;
        private float tickDelta;

        public GameRenderEvent(MatrixStack matrixStack, float tickDelta) {
            this.matrixStack = matrixStack;
            this.tickDelta = tickDelta;
        }

        @Override
        public void fire(ArrayList<GameRenderListener> listeners) {
            listeners.forEach(e -> e.onGameRender(this.matrixStack, this.tickDelta));
        }

        @Override
        public Class<GameRenderListener> getListenerType() {
            return GameRenderListener.class;
        }
    }
}