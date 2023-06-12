package optimizer.features;

import optimizer.events.UpdateListener;
import optimizer.feature.Feature;
import optimizer.setting.DecimalSetting;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import static optimizer.Client.MC;

public class ElytraBoostFeature extends Feature implements UpdateListener
{

	private final DecimalSetting speed = new DecimalSetting("speed", "speed", 1.0, this);

	public ElytraBoostFeature()
	{
		super("ElytraBoost", "fly me to the moon");
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
		if (!equippedElytra())
			return;

		if (MC.player.isFallFlying())
		{
			Vec3d v = MC.player.getVelocity();

			if(MC.options.jumpKey.isPressed())
				MC.player.setVelocity(v.x, v.y + 0.08, v.z);
			else if(MC.options.sneakKey.isPressed())
				MC.player.setVelocity(v.x, v.y - 0.04, v.z);

			float yaw = (float)Math.toRadians(MC.player.getYaw());
			Vec3d forward = new Vec3d(-MathHelper.sin(yaw), 0, MathHelper.cos(yaw)).normalize()
					.multiply(0.05).multiply(speed.getValue());

			v = MC.player.getVelocity();

			if(MC.options.forwardKey.isPressed())
				MC.player.setVelocity(v.add(forward));
			return;
		}

//		if (ElytraItem.isUsable(MC.player.getEquippedStack(EquipmentSlot.CHEST)) && MC.options.keyJump.isPressed())
//			sendStartStopPacket();
	}

	private boolean equippedElytra()
	{
		return MC.player.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.ELYTRA;
	}

	private void sendStartStopPacket()
	{
		ClientCommandC2SPacket packet = new ClientCommandC2SPacket(MC.player, ClientCommandC2SPacket.Mode.START_FALL_FLYING);
		MC.player.networkHandler.sendPacket(packet);
	}
}
