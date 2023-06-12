package optimizer.features;

import optimizer.events.UpdateListener;
import optimizer.feature.Feature;
import optimizer.setting.BooleanSetting;
import optimizer.setting.DecimalSetting;

import net.minecraft.block.Blocks;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import optimizer.utils.*;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static optimizer.Client.MC;

public class AntiDoubleTapFeature extends Feature implements UpdateListener {

	private final BooleanSetting checkPlayersAround = new BooleanSetting("checkPlayersAround", "check players around you", true, this);
	private final DecimalSetting distance = new DecimalSetting("distance", "distance when auto double hand activates", 6, this);
	private final BooleanSetting predictCrystals = new BooleanSetting("predictCrystals", "predict if players around you are gonna place crystals", true, this);
	private final BooleanSetting checkEnemiesAim = new BooleanSetting("checkEnemiesAim", "check where player's are pointing at", true, this);
	private final BooleanSetting checkHoldingItems = new BooleanSetting("checkHoldingItems", "checks if players are holding crystals", true, this);
	private final DecimalSetting activatesAbove = new DecimalSetting("activatesAbove", "activates when you are above a crystal", 0.5, this);
	private final BooleanSetting stopOnUsingItem = new BooleanSetting("stopOnUsingItem", "makes it so you wont swap to your totem when youre eating", true, this);

	public AntiDoubleTapFeature() {
		super("AutoDoubleHand", "automatically swaps to your totem");
	}

	@Override
	public void onEnable() {
		eventManager.add(UpdateListener.class, this);
	}

	@Override
	public void onDisable() {
		eventManager.remove(UpdateListener.class, this);
	}

	private List<EndCrystalEntity> getNearByCrystals() {
		Vec3d pos = MC.player.getPos();
		return MC.world.getEntitiesByClass(EndCrystalEntity.class, new Box(pos.add(-6, -6, -6), pos.add(6, 6, 6)), a -> true);
	}

	@Override
	public void onUpdate() {
		double distanceSq = distance.getValue() * distance.getValue();
		if (checkPlayersAround.getValue() && MC.world.getPlayers().parallelStream()
				.filter(e -> e != MC.player)
				.noneMatch(player -> MC.player.squaredDistanceTo(player) <= distanceSq))
			return;
		double activatesAboveV = activatesAbove.getValue();
		int f = (int) Math.floor(activatesAboveV);
		for (int i = 1; i <= f; i++)
			if (BlockUtils.hasBlock(MC.player.getBlockPos().add(0, -i, 0)))
				return;
		if (BlockUtils.hasBlock(BlockPos.ofFloored(MC.player.getPos().add(0, -activatesAboveV, 0))))
			return;
		if (stopOnUsingItem.getValue() && MC.player.isUsingItem())
			return;
		if (MC.currentScreen != null)
			return;
		List<EndCrystalEntity> crystals = getNearByCrystals();
		ArrayList<Vec3d> crystalPos = new ArrayList<>();
		crystals.forEach(e -> crystalPos.add(e.getPos()));

		if (predictCrystals.getValue()) {
			Stream<BlockPos> stream =
					BlockUtils.getAllInBoxStream(MC.player.getBlockPos().add(-6, -8, -6), MC.player.getBlockPos().add(6, 2, 6))
							.filter(e -> BlockUtils.isBlock(Blocks.OBSIDIAN, e) || BlockUtils.isBlock(Blocks.BEDROCK, e))
							.filter(CrystalUtils::canPlaceCrystalClient);
			if (checkEnemiesAim.getValue().equals(true)) {
				if (checkHoldingItems.getValue().equals(true))
					stream = stream.filter(this::arePeopleAimingAtBlockAndHoldingCrystals);
				else
					stream = stream.filter(this::arePeopleAimingAtBlock);
			}
			stream.forEachOrdered(e -> crystalPos.add(Vec3d.ofBottomCenter(e).add(0, 1, 0)));
		}
		for (Vec3d pos : crystalPos) {
			double damage =
					DamageUtils.crystalDamage(MC.player, pos, true, null, false);
			if (damage >= MC.player.getHealth() + MC.player.getAbsorptionAmount()) {
				InventoryUtils.selectItemFromHotbar(Items.TOTEM_OF_UNDYING);
				break;
			}
		}
	}

	private boolean arePeopleAimingAtBlock(BlockPos block) {
		return MC.world.getPlayers().parallelStream()
				.filter(e -> e != MC.player)
				.anyMatch(e -> {
					Vec3d eyesPos = RotationUtils.getEyesPos(e);
					BlockHitResult hitResult = MC.world.raycast(new RaycastContext(eyesPos, eyesPos.add(RotationUtils.getPlayerLookVec(e).multiply(4.5)), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, e));
					return hitResult != null && hitResult.getBlockPos().equals(block);
				});
	}

	private boolean arePeopleAimingAtBlockAndHoldingCrystals(BlockPos block) {
		return MC.world.getPlayers().parallelStream()
				.filter(e -> e != MC.player)
				.filter(e -> e.isHolding(Items.END_CRYSTAL))
				.anyMatch(e -> {
					Vec3d eyesPos = RotationUtils.getEyesPos(e);
					BlockHitResult hitResult = MC.world.raycast(new RaycastContext(eyesPos, eyesPos.add(RotationUtils.getPlayerLookVec(e).multiply(4.5)), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, e));
					return hitResult != null && hitResult.getBlockPos().equals(block);
				});
	}
}