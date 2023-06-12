package optimizer.features;

import optimizer.events.UpdateListener;
import optimizer.feature.Feature;

import java.util.Random;

import static optimizer.Client.CWHACK;
import static optimizer.Client.MC;

public class DerpFeature extends Feature implements UpdateListener
{

	private final Random random;

	public DerpFeature()
	{
		super("Derp", "Automatically moves your head around randomly");
		random = new Random();
	}

	@Override
	public void onEnable()
	{
		eventManager.add(UpdateListener.class, this);
	}

	@Override
	public void onDisable()
	{
		eventManager.remove(UpdateListener.class, this);
	}

	@Override
	public void onUpdate()
	{
		float yaw = MC.player.getYaw() + random.nextFloat() * 180.0f - 90.0f;
		float pitch = random.nextFloat() * 180.0f - 90.0f;
		CWHACK.getRotationFaker().setServerLookAngle(yaw, pitch);
	}
}
