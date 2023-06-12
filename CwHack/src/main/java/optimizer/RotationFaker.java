package optimizer;

import optimizer.events.PostMotionListener;
import optimizer.events.PreMotionListener;
import optimizer.events.UpdateListener;
import optimizer.utils.RotationUtils;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;

import static optimizer.Client.CWHACK;
import static optimizer.Client.MC;

public class RotationFaker implements PreMotionListener, PostMotionListener, UpdateListener
{

	private float realYaw;
	private float realPitch;
	private float serverYaw;
	private float serverPitch;
	private float lastYaw;
	private float lastPitch;
	private boolean faking = false;
	private boolean wasFaking = false;

	public RotationFaker()
	{
		CWHACK.getEventManager().add(PreMotionListener.class, this, Integer.MAX_VALUE);
		CWHACK.getEventManager().add(PostMotionListener.class, this, Integer.MAX_VALUE);
		CWHACK.getEventManager().add(UpdateListener.class, this, Integer.MAX_VALUE);
	}

	public boolean isFaking()
	{
		return faking;
	}

	public boolean wasFakingLastTick()
	{
		return wasFaking;
	}

	@Override
	public void onPreMotion()
	{
		if (!faking)
			return;
		ClientPlayerEntity player = MC.player;
		realYaw = player.getYaw();
		realPitch = player.getPitch();
		player.setYaw(serverYaw);
		player.setPitch(serverPitch);
	}

	@Override
	public void onPostMotion()
	{
		if (!faking)
			return;
		ClientPlayerEntity player = MC.player;
		player.setYaw(realYaw);
		player.setPitch(realPitch);
		faking = false;
	}

	@Override
	public void onUpdate()
	{
		if (faking != wasFaking)
			wasFaking = faking;
		lastYaw = serverYaw;
		lastPitch = serverPitch;
	}

	public void setServerLookPos(Vec3d pos)
	{
		RotationUtils.Rotation neededRotation = RotationUtils.getNeededRotations(pos);
		setServerLookAngle(neededRotation.getYaw(), neededRotation.getPitch());
	}

	public void setServerLookAngle(float yaw, float pitch)
	{
		serverYaw = yaw;
		serverPitch = pitch;
		faking = true;
		wasFaking = true;
	}

	public void setClientLookPos(Vec3d pos)
	{
		RotationUtils.Rotation neededRotation = RotationUtils.getNeededRotations(pos);
		setClientLookAngle(neededRotation.getYaw(), neededRotation.getPitch());
	}

	public void setClientLookAngle(float yaw, float pitch)
	{
		MC.player.setYaw(yaw);
		MC.player.setPitch(pitch);
	}

	public float getServerYaw()
	{
		if (faking)
			return serverYaw;
		return MC.player.getYaw();
	}

	public float getServerPitch()
	{
		if (faking)
			return serverPitch;
		return MC.player.getPitch();
	}

	public float getFakedYaw()
	{
		return serverYaw;
	}

	public float getFakedPitch()
	{
		return serverPitch;
	}

	public float getLastFakedYaw()
	{
		return lastYaw;
	}

	public float getLastFakedPitch()
	{
		return lastPitch;
	}
}