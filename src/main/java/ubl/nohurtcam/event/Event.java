package ubl.nohurtcam.event;

import java.util.ArrayList;

public abstract class Event<T extends Listener>
{
	private boolean cancelled;
	public abstract void fire(ArrayList<T> listeners);

	public abstract Class<T> getListenerType();
	public void setCancelled(boolean cancelled) {

		this.cancelled = cancelled;
	}
	public boolean isCancelled() {

		return cancelled;
	}
	public void cancel() {
		cancelled = true;
	}
}
