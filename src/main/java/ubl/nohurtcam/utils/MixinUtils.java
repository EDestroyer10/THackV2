package ubl.nohurtcam.utils;

import ubl.nohurtcam.event.CancellableEvent;
import ubl.nohurtcam.event.Event;
import ubl.nohurtcam.event.EventManager;
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
