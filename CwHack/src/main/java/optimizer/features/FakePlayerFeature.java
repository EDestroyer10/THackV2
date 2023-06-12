package optimizer.features;

import com.mojang.authlib.GameProfile;
import optimizer.feature.Feature;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

import static optimizer.Client.CWHACK;

public class FakePlayerFeature extends Feature
{

	public FakePlayerFeature()
	{
		super("FakePlayer", "spawn a fake player for testing purposes");
	}

	int id;

	@Override
	public void onEnable() {
		OtherClientPlayerEntity player = new OtherClientPlayerEntity(CWHACK.MC.world, new GameProfile(null, "DaRomanToast"));
		Vec3d pos = CWHACK.MC.player.getPos();
		player.updateTrackedPosition(pos.x, pos.y, pos.z);
		player.updatePositionAndAngles(pos.x, pos.y, pos.z, CWHACK.MC.player.getYaw(), CWHACK.MC.player.getPitch());
		player.resetPosition();
		CWHACK.MC.world.addPlayer(player.getId(), (AbstractClientPlayerEntity)player);
		this.id = player.getId();
	}

	public void onDisable() {
		CWHACK.MC.world.removeEntity(this.id, Entity.RemovalReason.DISCARDED);
	}
}
