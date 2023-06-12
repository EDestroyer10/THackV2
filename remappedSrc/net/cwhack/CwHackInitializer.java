package net.cwhack;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class CwHackInitializer implements ModInitializer
{
	public static boolean toggledOn = true;
	MinecraftClient client = MinecraftClient.getInstance();

	@Override
	public void onInitialize()
	{
		CwHack.CWHACK.init();
		KeyBinding k = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.noHurtCam.toggle",
				GLFW.GLFW_KEY_F8,
				"category.noHurtCam"));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (k.wasPressed()) {
				if (toggledOn) {
					toggledOn = false;
					client.player.sendMessage(Text.of("§9[NoHurtCam] §rEnabled Hurtcam"), false);
				}
				else {
					toggledOn = true;
					client.player.sendMessage(Text.of("§9[NoHurtCam] §rDisabled Hurtcam"), false);
				}


			}
		});
	}
}

