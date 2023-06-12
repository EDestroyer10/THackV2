package optimizer.events;

import optimizer.event.Event;
import optimizer.event.Listener;

import java.util.ArrayList;

public interface PlayerTickListener extends Listener {
    public void onPlayerTick();

    public static class PlayerTickEvent
            extends Event<PlayerTickListener> {
        @Override
        public void fire(ArrayList<PlayerTickListener> listeners) {
            listeners.forEach(PlayerTickListener::onPlayerTick);
        }

        @Override
        public Class<PlayerTickListener> getListenerType() {
            return PlayerTickListener.class;
        }
    }
}