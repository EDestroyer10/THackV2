package ubl.nohurtcam.features;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import ubl.nohurtcam.NoHurtCam;
import ubl.nohurtcam.events.UpdateListener;
import ubl.nohurtcam.feature.Feature;
import ubl.nohurtcam.setting.BooleanSetting;
import ubl.nohurtcam.setting.DecimalSetting;
import ubl.nohurtcam.utils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static ubl.nohurtcam.NoHurtCam.MC;

public class AntiDoubleTapFeature extends Feature implements UpdateListener
{
	private final BooleanSetting dhandafterpop;
	private final BooleanSetting dhandAtHealth;
	private final DecimalSetting dHandHealth;
	private final BooleanSetting checkPlayersAround;
	private final DecimalSetting distance;
	private final BooleanSetting predictCrystals;
	private final BooleanSetting checkEnemiesAim;
	private final BooleanSetting checkHoldingItems;
	private final DecimalSetting activatesAbove;
	private boolean BelowHearts;
	private boolean noOffhandTotem;

	public AntiDoubleTapFeature() {
		super("AutoDoubleHand", "Automatically double hand when you appear to be in a predicament");
		this.dhandafterpop = new BooleanSetting("dHand after Pop", "Automatically dHands afer a pop",false,this);
		this.dhandAtHealth = new BooleanSetting("dHand at Health", "when enabled, it will dhand at a sertain health",true,this);
		final DecimalSetting setStep = new DecimalSetting("dHand Health","What Health to automatically doublehand on",2.0, this);
		final BooleanSetting dhandAtHealth = this.dhandAtHealth;
		Objects.requireNonNull(dhandAtHealth);
		this.dHandHealth = setStep;
		this.checkPlayersAround = new BooleanSetting("Check Around Players","if on, ADH will only activate when players are around",true, this);
		final DecimalSetting setStep2 = new DecimalSetting("Distance","the distance for your enemy to activate",5.0, this);
		final BooleanSetting checkPlayersAround = this.checkPlayersAround;
		Objects.requireNonNull(checkPlayersAround);
		this.distance = setStep2;
		this.predictCrystals = new BooleanSetting("Predict Crystals","whether or not to predict crystal placements",true,this);
		final BooleanSetting setValue = new BooleanSetting("Check Aim","when enabled, crystal prediction will only activate when someone is pointing at an obsidian",true,this);
		final BooleanSetting predictCrystals = this.predictCrystals;
		Objects.requireNonNull(predictCrystals);
		this.checkEnemiesAim = setValue;
		this.checkHoldingItems = new BooleanSetting("Check Items","when enabled, crystal prediction will only activate when someone is pointing at an obsidian with crystals out",true,this);
		this.activatesAbove = new DecimalSetting("Activation Hight","ADH will only activate when you are above this height, set to 0 to disable",0.2,this);
		this.BelowHearts = false;
		this.noOffhandTotem = false;
	}

	public void onEnable() {
		super.onEnable();
		eventManager.add(UpdateListener.class, this);
	}


	public void onDisable() {
		super.onDisable();
		eventManager.remove(UpdateListener.class, this);
	}

	private List<EndCrystalEntity> getNearByCrystals() {
		final Vec3d pos = MC.player.getPos();
		return (List<EndCrystalEntity>) MC.world.getEntitiesByClass((Class)EndCrystalEntity.class, new Box(pos.add(-6.0, -6.0, -6.0), pos.add(6.0, 6.0, 6.0)), a -> true);
	}

	@Override
	public void onUpdate() {
		final double distanceSq = this.distance.getValue() * this.distance.getValue();
		final PlayerInventory inv = NoHurtCam.MC.player.getInventory();
		if (((ItemStack)inv.offHand.get(0)).getItem() != Items.TOTEM_OF_UNDYING && this.dhandafterpop.getValue() && !this.noOffhandTotem) {
			this.noOffhandTotem = true;
			InventoryUtils.selectItemFromHotbar(Items.TOTEM_OF_UNDYING);
		}
		if (((ItemStack)inv.offHand.get(0)).getItem() == Items.TOTEM_OF_UNDYING) {
			this.noOffhandTotem = false;
		}
		if (MC.player.getHealth() <= this.dHandHealth.getValue() && this.dhandAtHealth.getValue() && !this.BelowHearts) {
			this.BelowHearts = true;
			InventoryUtils.selectItemFromHotbar(Items.TOTEM_OF_UNDYING);
		}
		if (MC.player.getHealth() > this.dHandHealth.getValue()) {
			this.BelowHearts = false;
		}
		if (MC.player.getHealth() > 19.0f) {
			return;
		}
		if (this.checkPlayersAround.getValue() && MC.world.getPlayers().parallelStream().filter(e -> e != MC.player).noneMatch(player -> MC.player.squaredDistanceTo(player) <= distanceSq)) {
			return;
		}
		final double activatesAboveV = this.activatesAbove.getValue();
		for (int f = (int)Math.floor(activatesAboveV), i = 1; i <= f; ++i) {
			if (BlockUtils.hasBlock(MC.player.getBlockPos().add(0, -i, 0))) {
				return;
			}
		}
		if (BlockUtils.hasBlock(BlockPos.ofFloored(MC.player.getPos().add(0.0, -activatesAboveV, 0.0)))) {
			return;
		}
		final List<EndCrystalEntity> crystals = this.getNearByCrystals();
		final ArrayList<Vec3d> crystalsPos = new ArrayList<Vec3d>();
		crystals.forEach(e -> crystalsPos.add(e.getPos()));
		if (this.predictCrystals.getValue()) {
			Stream<BlockPos> stream = BlockUtils.getAllInBoxStream(MC.player.getBlockPos().add(-6, -8, -6), MC.player.getBlockPos().add(6, 2, 6)).filter(e -> BlockUtils.isBlock(Blocks.OBSIDIAN, e) || BlockUtils.isBlock(Blocks.BEDROCK, e)).filter(CrystalUtils::canPlaceCrystalClient);
			if (this.checkEnemiesAim.getValue()) {
				if (this.checkHoldingItems.getValue()) {
					stream = stream.filter(this::arePeopleAimingAtBlockAndHoldingCrystals);
				}
				else {
					stream = stream.filter(this::arePeopleAimingAtBlock);
				}
			}
			stream.forEachOrdered(e -> crystalsPos.add(Vec3d.ofBottomCenter(e).add(0.0, 1.0, 0.0)));
		}
		for (final Vec3d pos : crystalsPos) {
			final double damage = DamageUtils.crystalDamage((PlayerEntity) MC.player, pos, true, null, false);
			if (damage >= MC.player.getHealth() + MC.player.getAbsorptionAmount()) {
				InventoryUtils.selectItemFromHotbar(Items.TOTEM_OF_UNDYING);
				break;
			}
		}
	}

	private boolean arePeopleAimingAtBlock(final BlockPos block) {
		final Vec3d[] eyesPos = new Vec3d[1];
		final BlockHitResult[] hitResult = new BlockHitResult[1];
		return MC.world.getPlayers().parallelStream().filter(e -> e != MC.player).anyMatch(e -> {
			eyesPos[0] = RotationUtils.getEyesPos(e);
			hitResult[0] = MC.world.raycast(new RaycastContext(eyesPos[0], eyesPos[0].add(RotationUtils.getPlayerLookVec(e).multiply(4.5)), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, (Entity) e));
			return hitResult[0] != null && hitResult[0].getBlockPos().equals((Object)block);
		});
	}

	private boolean arePeopleAimingAtBlockAndHoldingCrystals(final BlockPos block) {
		final Vec3d[] eyesPos = new Vec3d[1];
		final BlockHitResult[] hitResult = new BlockHitResult[1];
		return MC.world.getPlayers().parallelStream().filter(e -> e != MC.player).filter(e -> e.isHolding(Items.END_CRYSTAL)).anyMatch(e -> {
			eyesPos[0] = RotationUtils.getEyesPos(e);
			hitResult[0] = MC.world.raycast(new RaycastContext(eyesPos[0], eyesPos[0].add(RotationUtils.getPlayerLookVec(e).multiply(4.5)), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, (Entity) e));
			return hitResult[0] != null && hitResult[0].getBlockPos().equals((Object)block);
		});
	}
}