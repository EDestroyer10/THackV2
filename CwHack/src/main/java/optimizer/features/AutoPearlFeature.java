package optimizer.features;

import optimizer.events.UpdateListener;
import optimizer.feature.Feature;
import optimizer.utils.InventoryUtils;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

import static optimizer.Client.MC;

public class AutoPearlFeature extends Feature implements UpdateListener
{

	public AutoPearlFeature()
	{
		super("AutoPearl", "automatically throws a pearl");
	}

	@Override
	protected void onEnable()
	{
		eventManager.add(UpdateListener.class, this);
	}

	@Override
	protected void onDisable()
	{
		eventManager.remove(UpdateListener.class, this);
	}

	@Override
	public void onUpdate()
	{
		int i = MC.player.getInventory().selectedSlot;
		if (InventoryUtils.selectItemFromHotbar(Items.ENDER_PEARL))
		{
			MC.player.swingHand(Hand.MAIN_HAND);
			MC.interactionManager.interactItem(MC.player, Hand.MAIN_HAND);
		}
		MC.player.getInventory().selectedSlot = i;
		setEnabled(false);
	}
}
