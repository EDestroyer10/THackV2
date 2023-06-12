package ubl.nohurtcam.events;

import ubl.nohurtcam.event.Event;
import ubl.nohurtcam.event.Listener;

public abstract class CancelEvent<T extends Listener> extends Event<T> {
    private boolean isCancelled = false;

    public boolean isCancelled() {
        return this.isCancelled;
    }

    public void cancel() {
        this.isCancelled = true;
    }
}