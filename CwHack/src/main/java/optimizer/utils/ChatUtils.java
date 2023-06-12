package optimizer.utils;

import net.minecraft.text.Text;
import org.apache.logging.log4j.LogManager;
import optimizer.Client;

public enum ChatUtils
{
	;
	private static final String prefix = "§f[§9CwHack§f] ";

	public static void log(String message)
	{
		if (Client.CWHACK.isGhostMode())
			return;
		LogManager.getLogger().info("[CWHACK] {}", message.replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n"));
	}
	public static void info(String message)
	{
		if (Client.CWHACK.isGhostMode())
			return;
		String string = prefix + "Info: " + message;
		sendPlainMessage(string);
	}
	public static void error(String message)
	{
		if (Client.CWHACK.isGhostMode())
			return;
		String string = prefix + "§4Error: §f" + message;
		sendPlainMessage(string);
		log(message);
	}
	public static void sendPlainMessage(String message)
	{
		if (Client.CWHACK.isGhostMode())
			return;
		if (Client.MC.inGameHud != null)
			Client.MC.inGameHud.getChatHud().addMessage(Text.literal(message));
	}
}
