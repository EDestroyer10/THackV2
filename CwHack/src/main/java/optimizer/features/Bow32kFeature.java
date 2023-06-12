package optimizer.features;

import optimizer.events.SendMovementPacketsListener;
import optimizer.events.StopUsingItemListener;
import optimizer.feature.Feature;
import optimizer.setting.IntegerSetting;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

import static optimizer.Client.MC;

public class Bow32kFeature extends Feature implements StopUsingItemListener, SendMovementPacketsListener
{

	private final IntegerSetting power = new IntegerSetting("power", "the power of the hack", 50, this);

	public Bow32kFeature()
	{
		super("Bow32k", "one shot someone with bow");
	}

	@Override
	protected void onEnable()
	{
		eventManager.add(StopUsingItemListener.class, this);
		eventManager.add(SendMovementPacketsListener.class, this);
	}

	@Override
	protected void onDisable()
	{
		eventManager.remove(StopUsingItemListener.class, this);
		eventManager.remove(SendMovementPacketsListener.class, this);
	}

	@Override
	public void onStopUsingItem(StopUsingItemEvent event)
	{
		if (!MC.player.getActiveItem().isOf(Items.BOW))
			return;
		int powerI = power.getValue();
		for (int i = 0; i < powerI; i++)
		{
			MC.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(MC.player.getX(), MC.player.getY(), MC.player.getZ(), true));
			MC.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(MC.player.getX(), MC.player.getY() + 0.01, MC.player.getZ(), false));
		}
	}

	@Override
	public void onSendMovementPackets(SendMovementPacketsEvent event)
	{
		if (!MC.player.getActiveItem().isOf(Items.BOW))
			return;
		MC.player.setSprinting(true);
	}
}
