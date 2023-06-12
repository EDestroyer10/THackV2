package ubl.nohurtcam.utils;

import net.minecraft.text.Text;
import org.apache.logging.log4j.LogManager;

import static ubl.nohurtcam.NoHurtCam.CWHACK;
import static ubl.nohurtcam.NoHurtCam.MC;
import static net.minecraft.client.resource.language.I18n.translate;

public enum ChatUtils
{
	;
	private static final String prefix = "§f[§9CwHack§f] ";

	public static void log(String message)
	{
		if (CWHACK.isGhostMode())
			return;
		LogManager.getLogger().info("[CWHACK] {}", message.replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n"));
	}
	public static void info(String message)
	{
		if (CWHACK.isGhostMode())
			return;
		String string = prefix + "Info: " + message;
		sendPlainMessage(string);
	}
	public static void error(String message)
	{
		if (CWHACK.isGhostMode())
			return;
		String string = prefix + "§4Error: §f" + message;
		sendPlainMessage(string);
		log(message);
	}
	public static void sendPlainMessage(String message)
	{
		if (CWHACK.isGhostMode())
			return;
		if (MC.inGameHud != null)
			MC.inGameHud.getChatHud().addMessage(Text.literal(message));
	}
	public static void plainMessageWithPrefix(String message) {
		String string = prefix + message;
		ChatUtils.sendPlainMessage(string);
	}
	public void sendMsg(String text) {
		if (MC.player != null) MC.player.sendMessage(Text.of(translate(text)));
	}
}
