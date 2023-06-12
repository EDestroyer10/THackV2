package ubl.nohurtcam.utils;

import static ubl.nohurtcam.NoHurtCam.CWHACK;

public enum NotificationUtils
{
	;
	public static void notify(String string)
	{
		if (!CWHACK.isGhostMode())
			CWHACK.getNotificationRenderer().sendNotification(string);
	}
}