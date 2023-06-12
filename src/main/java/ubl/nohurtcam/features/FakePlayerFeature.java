package ubl.nohurtcam.features;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import ubl.nohurtcam.feature.Feature;

import static ubl.nohurtcam.NoHurtCam.MC;

public class FakePlayerFeature extends Feature
{

	public FakePlayerFeature()
	{
		super("FakePlayer", "spawn a fake player for testing purposes");
	}

	int id;

	@Override
	protected void onEnable()
	{
		OtherClientPlayerEntity player = new OtherClientPlayerEntity(MC.world, new GameProfile(null, "Nigger"));
		Vec3d pos = MC.player.getPos();
		MC.player.updateTrackedPosition(pos.x, pos.y, pos.z);
		player.updatePositionAndAngles(pos.x, pos.y, pos.z, MC.player.getYaw(), MC.player.getPitch());
		player.resetPosition();
		MC.world.addPlayer(player.getId(), player);
		id = player.getId();
	}

	@Override
	protected void onDisable()
	{
		MC.world.removeEntity(id, Entity.RemovalReason.DISCARDED);

	}
}