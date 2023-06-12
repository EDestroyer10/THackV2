package optimizer.utils;

import optimizer.event.Event;
import optimizer.event.EventManager;
import optimizer.event.CancellableEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public enum MixinUtils
{
	;
	public static void fireEvent(Event<?> event)
	{
		EventManager.fire(event);
	}

	public static void fireCancellableEvent(CancellableEvent<?> event, CallbackInfo ci)
	{
		EventManager.fire(event);
		if (event.isCancelled())
			ci.cancel();
	}

}
