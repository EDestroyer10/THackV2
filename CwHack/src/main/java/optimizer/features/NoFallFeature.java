package optimizer.features;

import optimizer.events.PostMotionListener;
import optimizer.events.PreMotionListener;
import optimizer.events.SendMovementPacketsListener;
import optimizer.feature.Feature;
import net.minecraft.entity.player.PlayerEntity;

import static optimizer.Client.MC;

public class NoFallFeature extends Feature implements SendMovementPacketsListener, PreMotionListener, PostMotionListener
{

	public NoFallFeature()
	{
		super("NoFall", "cancel fall damage");
	}

	@Override
	protected void onEnable()
	{
		eventManager.add(SendMovementPacketsListener.class, this);
		eventManager.add(PreMotionListener.class, this);
		eventManager.add(PostMotionListener.class, this);
	}

	@Override
	protected void onDisable()
	{
		eventManager.remove(SendMovementPacketsListener.class, this);
		eventManager.remove(PreMotionListener.class, this);
		eventManager.remove(PostMotionListener.class, this);
	}

	private boolean origOnGround = false;

	@Override
	public void onSendMovementPackets(SendMovementPacketsEvent event)
	{
		origOnGround = MC.player.isOnGround();
	}

	@Override
	public void onPreMotion()
	{
		PlayerEntity player = MC.player;
		if (player.fallDistance <= (player.isFallFlying() ? 1 : 2))
			return;
		if (player.isFallFlying() && player.getVelocity().y > -0.5)
			return;
		player.setOnGround(true);
	}

	@Override
	public void onPostMotion()
	{
		MC.player.setOnGround(origOnGround);
	}
}
