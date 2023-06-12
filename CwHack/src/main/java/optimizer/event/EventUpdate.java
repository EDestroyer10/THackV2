package optimizer.event;

import java.util.ArrayList;

public class EventUpdate extends Event {

    private static final EventUpdate instance = new EventUpdate();

    public static EventUpdate get() {
        instance.setCancelled(false);
        return instance;
    }

    private void setCancelled(boolean b) {
    }

    @Override
    public void fire(ArrayList listeners) {

    }

    @Override
    public Class getListenerType() {
        return null;
    }
}
