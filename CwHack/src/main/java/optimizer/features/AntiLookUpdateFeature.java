package optimizer.features;

import optimizer.events.PacketInputListener;
import optimizer.feature.Feature;
import net.minecraft.network.packet.c2s.play.TeleportConfirmC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;

import static optimizer.Client.MC;

public class AntiLookUpdateFeature extends Feature implements PacketInputListener
{

	public AntiLookUpdateFeature()
	{
		super("AntiLookUpdate", "stop the server from setting your camera angle");
	}

	@Override
	protected void onEnable()
	{
		eventManager.add(PacketInputListener.class, this);
	}

	@Override
	protected void onDisable()
	{
		eventManager.remove(PacketInputListener.class, this);
	}

	@Override
	public void onReceivePacket(PacketInputEvent event)
	{
		if (!(event.getPacket() instanceof PlayerPositionLookS2CPacket && MC.currentScreen == null))
			return;
		event.cancel();
		PlayerPositionLookS2CPacket packet = (PlayerPositionLookS2CPacket) event.getPacket();
		MC.getNetworkHandler().sendPacket(new TeleportConfirmC2SPacket(packet.getTeleportId()));
		MC.player.setPosition(packet.getX(), packet.getY(), packet.getZ());
	}
}
