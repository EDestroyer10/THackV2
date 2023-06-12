package ubl.nohurtcam.feature;

import net.minecraft.network.packet.Packet;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import ubl.nohurtcam.features.*;
import ubl.nohurtcam.setting.Setting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import static ubl.nohurtcam.NoHurtCam.CWHACK;

public class FeatureList
{
	public static final FeatureList INSTANCE = new FeatureList();
	private Feature feature;

	public final AutoWTapFeature autoWTapFeature = new AutoWTapFeature();
	public final CameraNoClipFeature cameraNoClipFeature = new CameraNoClipFeature();
	public final CwCrystalRewriteFeature cwCrystalRewriteFeature = new CwCrystalRewriteFeature();
	public final FakePlayerFeature fakePlayerFeature = new FakePlayerFeature();
	public final MarlowAnchorFeature marlowAnchorFeature = new MarlowAnchorFeature();
	public final NoOverlayFeature noOverlayFeature = new NoOverlayFeature();
	public final AutoXpFeature autoXpFeature = new AutoXpFeature();
	public final AutoInventoryTotemFeature autoInventoryTotemFeature = new AutoInventoryTotemFeature();
	public final AutoLootFeature autoLootFeature = new AutoLootFeature();
	public final TriggerBotFeature triggerBotFeature = new TriggerBotFeature();
	public final NoHurtCamFeature noHurtCamFeature = new NoHurtCamFeature();
	public final XRayFeature xRayFeature = new XRayFeature();
	public final BoatExecutorFeature boatExecutorFeature = new BoatExecutorFeature();
	public final MiddleClickPingFeature middleClickPingFeature = new MiddleClickPingFeature();
	public final MarlowCrystalRewriteFeature marlowCrystalRewriteFeature = new MarlowCrystalRewriteFeature();
	//public final PlacementHighlightFeature placementHighlight = new PlacementHighlightFeature();
	public final AutoPotFeature autoPotFeature = new AutoPotFeature();
	public final AntiAntiCrystalFeature antiAntiCrystalFeature = new AntiAntiCrystalFeature();
	public final AutoDtapFeature autoDtapFeature = new AutoDtapFeature();
	public final MacroCCFeature macroCCFeature = new MacroCCFeature();
	public final AutoSprintFeature autoSprintFeature = new AutoSprintFeature();
	public final LegitScaffoldFeature legitScaffoldFeature = new LegitScaffoldFeature();
	//public final HUDFeature hudFeature = new HUDFeature();
	public final DiscordNotifierFeature discordNotifierFeature = new DiscordNotifierFeature();
	public final AutoHitCrystalFeature autoHitCrystalFeature = new AutoHitCrystalFeature();
	public final AntiDoubleTapFeature antiDoubleTapFeature = new AntiDoubleTapFeature();
	public final ESPFeature espFeature = new ESPFeature();
	private HashMap<String, Feature> features = new HashMap<>();

	public FeatureList()
	{
		try
		{
			for (Field field : FeatureList.class.getDeclaredFields())
			{
				if (!field.getName().endsWith("Feature"))
					continue;

				Feature feature = (Feature) field.get(this);
				features.put(feature.getName().toLowerCase(), feature);
			}
		} catch (Exception e)
		{
			String message = "Initializing NoHurtCam features";
			CrashReport report = CrashReport.create(e, message);
			throw new CrashException(report);
		}
	}

	public void turnOffAll()
	{
		features.forEach((name, feature) -> feature.setEnabled(false));
	}


	public void recievePacket(Packet<?> packet) {
			if (feature.getState()) {
				feature.onReceivePacket(packet);
			}
	}
	public void sendPacket(Packet<?> packet) {
		if (feature.getState()) {
			feature.onSendPacket(packet);
		}
	}

	public Feature getFeature(String name)
	{
		return features.get(name);
	}

	public Set<String> getAllFeatureNames()
	{
		return features.keySet();
	}

	public void loadFromFile(String path)
	{
		Path configFilePath = Path.of(path);
		try
		{
			if (Files.notExists(configFilePath))
				throw new RuntimeException(path);
			Scanner scanner = new Scanner(configFilePath);
			int state = 1;
			/*
			state machine
			0 = expect feature enabled state
			1 = expect setting name / feature name ("@")
			2 = expect setting state
			 */
			Feature readingFeature = null;
			Setting readingSetting = null;
			while (scanner.hasNextLine())
			{
				String line = scanner.nextLine().toLowerCase();
				if (state == 1)
				{
					if (line.startsWith("@"))
					{
						readingFeature = getFeature(line.substring(1));
						if (readingFeature == null)
							throw new RuntimeException(path);
						state = 0;
						continue;
					}
					readingSetting = readingFeature.getSetting(line);
					if (readingSetting == null)
						throw new RuntimeException(path);
					state = 2;
					continue;
				}
				if (state == 0)
				{
					if (!line.equals("true") && !line.equals("false"))
						throw new RuntimeException(path);
					readingFeature.setEnabled(line.equals("true"));
					state = 1;
					continue;
				}
				if (state == 2)
				{
					try
					{
						readingSetting.loadFromString(line);
					} catch (Exception e)
					{
						throw new RuntimeException(path);
					}
					state = 1;
					continue;
				}
			}
			if (state != 1)
				throw new RuntimeException(path);
		} catch (IOException e)
		{
			throw new RuntimeException("Failed to open config file");
		}
	}

	public void saveAsFile(String path)
	{
		Path configDir = CWHACK.getCwHackDirectory().resolve("config");
		try
		{
			Files.createDirectories(configDir);
		} catch (IOException e)
		{
			throw new RuntimeException("Failed to create config folder");
		}
		try
		{
			Path configFilePath = configDir.resolve(path);
			File configFile = new File(configFilePath.toString());
			configFile.createNewFile();
		} catch (IOException e)
		{
			throw new RuntimeException("Failed to create config file");
		}
		Path configFilePath = Path.of(path);
		try
		{
			FileWriter fileWriter = new FileWriter(configFilePath.toString());
			Set<String> featureNames = getAllFeatureNames();
			for (String featureName : featureNames)
			{
				Feature feature = getFeature(featureName);
				fileWriter.write("@" + featureName + "\n");
				fileWriter.write(feature.isEnabled() + "\n");
				for (String settingName : feature.getSettingNames())
				{
					Setting setting = feature.getSetting(settingName);
					fileWriter.write(settingName + "\n");
					fileWriter.write(setting.storeAsString() + "\n");
				}
			}
			fileWriter.close();
		} catch (IOException e)
		{
			throw new RuntimeException("Failed to create config file");
		}
	}
}
