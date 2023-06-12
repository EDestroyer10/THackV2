package ubl.nohurtcam.features;

import ubl.nohurtcam.events.UpdateListener;
import ubl.nohurtcam.feature.Feature;
import net.minecraft.client.network.ClientPlayerEntity;

import static ubl.nohurtcam.NoHurtCam.MC;

public class AutoSprintFeature extends Feature implements UpdateListener {

	public AutoSprintFeature() {
		super("AutoSprint", "a");
	}

	@Override
	protected void onEnable() {
		eventManager.add(UpdateListener.class, this);
	}

	@Override
	protected void onDisable() {
		eventManager.remove(UpdateListener.class, this);
	}

	@Override
	public void onUpdate() {
		ClientPlayerEntity player = MC.player;

		if (player.horizontalCollision || player.isSneaking())
			return;

		if (player.isInsideWaterOrBubbleColumn() || player.isSubmergedInWater())
			return;

		if (player.forwardSpeed > 0)
			player.setSprinting(true);
	}
}
