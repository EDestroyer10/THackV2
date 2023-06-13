package ubl.nohurtcam.features;

import net.minecraft.block.entity.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;
import ubl.nohurtcam.feature.Feature;
import ubl.nohurtcam.setting.BooleanSetting;
import ubl.nohurtcam.setting.IntegerSetting;
import ubl.nohurtcam.utils.QuadColor;
import ubl.nohurtcam.utils.RenderUtils;
import ubl.nohurtcam.utils.WorldUtil;

public class StorageESPFeature extends Feature {

	public IntegerSetting width;
	float[] color  = new float[] {127f, 0f, 127f};
	public BooleanSetting chest;
	public BooleanSetting eChest;
	public BooleanSetting shulker;
	public BooleanSetting furnace;
	public BooleanSetting hopper;

	public StorageESPFeature() {
		super("StorageESP", "Allows you to see storage block");
		this.width = new IntegerSetting("Width", "a", 1, this);
		this.chest = new BooleanSetting("Chest", "a", true, this);
		this.eChest = new BooleanSetting("E-Chest", "a", true, this);
		this.shulker = new BooleanSetting("Shulker", "a", true, this);
		this.furnace = new BooleanSetting("Furnace", "a", true, this);
		this.hopper = new BooleanSetting("Hopper", "a", true, this);
	}

	@Override
	public void onWorldRender(MatrixStack matrices) {
		for (BlockEntity block : WorldUtil.getBlockEntities()) {
			if(chest.isEnabled()) {
				if (block instanceof ChestBlockEntity) {
					RenderUtils.drawBoxBoth(new Box(
							block.getPos().getX()+0.06,
							block.getPos().getY(),
							block.getPos().getZ()+0.06,
							block.getPos().getX()+0.94,
							block.getPos().getY()+0.88,
							block.getPos().getZ()+0.94), QuadColor.single(1f, 1f, 0f, 0.3f), 1);
				}
			}
			if(eChest.isEnabled()) {
				RenderUtils.drawBoxBoth(new Box(
						block.getPos().getX()+0.5,
						block.getPos().getY()+0.5,
						block.getPos().getZ()+0.5,
						block.getPos().getX()+0.0,
						block.getPos().getY()+0.0,
						block.getPos().getZ()+0.0), QuadColor.single(1f, 0f, 1f, 0.3f), 1);
			}
			if(shulker.isEnabled()) {
				if (block instanceof ShulkerBoxBlockEntity) {
					RenderUtils.drawBoxBoth(new Box(
							block.getPos().getX()+0.06,
							block.getPos().getY(),
							block.getPos().getZ()+0.06,
							block.getPos().getX()+0.94,
							block.getPos().getY()+0.88,
							block.getPos().getZ()+0.94), QuadColor.single(1f, 1f, 1f, 0.3f), 1);
				}
			}
			if(furnace.isEnabled()) {
				if (block instanceof FurnaceBlockEntity) {
					RenderUtils.drawBoxBoth(new Box(
							block.getPos().getX()+0.06,
							block.getPos().getY(),
							block.getPos().getZ()+0.06,
							block.getPos().getX()+0.94,
							block.getPos().getY()+0.88,
							block.getPos().getZ()+0.94), QuadColor.single(1f, 0f, 0f, 0.3f), 1);
				}
			}
			if(hopper.isEnabled()) {
				if (block instanceof HopperBlockEntity) {
					RenderUtils.drawBoxBoth(new Box(
							block.getPos().getX()+0.06,
							block.getPos().getY(),
							block.getPos().getZ()+0.06,
							block.getPos().getX()+0.94,
							block.getPos().getY()+0.88,
							block.getPos().getZ()+0.94), QuadColor.single(0f, 0f, 1f, 0.3f), 1);
				}
			}

		}
	}
}


