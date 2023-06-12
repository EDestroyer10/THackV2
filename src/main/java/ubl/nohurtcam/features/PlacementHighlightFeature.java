package ubl.nohurtcam.features;
/*
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.RaycastContext;
import org.lwjgl.opengl.GL11;
import ubl.nohurtcam.NoHurtCam;
import ubl.nohurtcam.Rotation;
import ubl.nohurtcam.Rotator;
import ubl.nohurtcam.events.AttackEntityListener;
import ubl.nohurtcam.events.GameRenderListener;
import ubl.nohurtcam.events.UpdateListener;
import ubl.nohurtcam.feature.Feature;
import ubl.nohurtcam.utils.*;

import java.util.Comparator;
import java.util.stream.Stream;

public class PlacementHighlightFeature extends Feature implements UpdateListener, AttackEntityListener, GameRenderListener
{
    private int renderClock = 0;
    private int placeObiClock = -1;
    private BlockPos highlight;
    private Vec3d targetPredictedPos;

    public PlacementHighlightFeature() {
        super("PlacementHighlight", "Highlights optimal placements for obsidians");
    }

    @Override
    public void onEnable() {
        super.onEnable();
        eventManager.add(UpdateListener.class, this);
        eventManager.add(AttackEntityListener.class, this);
        eventManager.add(GameRenderListener.class, this);
        this.renderClock = 0;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        eventManager.remove(UpdateListener.class, this);
        eventManager.remove(AttackEntityListener.class, this);
        eventManager.remove(GameRenderListener.class, this);
    }

    @Override
    public void onUpdate() {
        if (this.renderClock > 0) {
            --this.renderClock;
        }
        if (this.placeObiClock == 0) {
            this.placeObiClock = -1;
            if (this.highlight != null) {
                InventoryUtils.selectItemFromHotbar(Items.OBSIDIAN);
                BlockUtils.placeBlock(this.highlight);
            }
        } else {
            --this.placeObiClock;
        }
    }

    @Override
    public void onAttackEntity(AttackEntityListener.AttackEntityEvent event) {
        if (!(event.getTarget() instanceof PlayerEntity)) {
            return;
        }
        PlayerEntity target = (PlayerEntity)event.getTarget();
        if (NoHurtCam.MC.player.isTouchingWater() || NoHurtCam.MC.player.isInLava()) {
            return;
        }
        if (!target.isOnGround()) {
            return;
        }
        int placeCrystalAfter = 4;
        int breakCrystalAfter = 8;
        int placeObiAfter = 2;
        Vec3d targetKB = this.calcTargetKB((LivingEntity)target);
        int floorY = NoHurtCam.MC.player.getBlockY() - 1;
        Vec3d targetPos = target.getPos();
        Vec3d targetPosAtPlaceCrystal = this.simulatePos(targetPos, targetKB, placeCrystalAfter);
        Vec3d targetPosAtBreakCrystal = this.simulatePos(targetPos, targetKB, breakCrystalAfter);
        Vec3d targetPosAtPlaceObi = this.simulatePos(targetPos, targetKB, placeObiAfter);
        Box targetBoxAtPlaceObi = target.getBoundingBox().offset(targetPosAtPlaceObi.subtract(target.getPos()));
        Box targetBoxAtPlaceCrystal = target.getBoundingBox().offset(targetPosAtPlaceCrystal.subtract(target.getPos()));
        BlockPos blockPos = NoHurtCam.MC.player.getBlockPos();
        Stream<BlockPos> blocks = BlockUtils.getAllInBoxStream(blockPos.add(-4, 0, -4), blockPos.add(4, 0, 4));
        BlockPos placement = blocks.filter(b -> !BlockUtils.hasBlock(b)).filter(b -> BlockUtils.hasBlock(b.add(0, -1, 0))).filter(b -> !Box.of((Vec3d)Vec3d.ofCenter((Vec3i)b), (double)1.0, (double)1.0, (double)1.0).intersects(targetBoxAtPlaceObi)).filter(b -> {
            Vec3d startP = RenderUtils.getCameraPos();
            Vec3d endP = Vec3d.ofBottomCenter((Vec3i)b);
            if (endP.subtract(startP).lengthSquared() > 16.0) {
                return false;
            }
            BlockHitResult result = NoHurtCam.MC.world.raycast(new RaycastContext(RenderUtils.getCameraPos(), endP, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, (Entity) NoHurtCam.MC.player));
            return result.getType() == HitResult.Type.MISS;
        }).filter(b -> CrystalUtils.canPlaceCrystalClientAssumeObsidian(b, targetBoxAtPlaceCrystal)).max(Comparator.comparingDouble(b -> DamageUtils.crystalDamage(target, targetPosAtBreakCrystal, Vec3d.ofCenter((Vec3i)b, (double)1.0), b, false))).orElse(null);
        if (placement == null) {
            return;
        }
        Rotator rotator = NoHurtCam.CWHACK.rotator();
        rotator.stepToward(Vec3d.ofBottomCenter((Vec3i)placement), placeObiAfter, () -> {
            InventoryUtils.selectItemFromHotbar(Items.OBSIDIAN);
            BlockPos neighbor = placement.add(0, -1, 0);
            Direction direction = Direction.UP;
            Vec3d center = Vec3d.ofCenter((Vec3i)neighbor).add(Vec3d.of((Vec3i)direction.getVector()).multiply(0.5));
            ActionResult result = NoHurtCam.MC.interactionManager.interactBlock(NoHurtCam.MC.player, Hand.MAIN_HAND, new BlockHitResult(center, Direction.UP, placement.add(0, -1, 0), false));
            if (result == ActionResult.SUCCESS) {
                NoHurtCam.MC.player.swingHand(Hand.MAIN_HAND);
                rotator.stepToward(new Rotation(0.0f, true, NoHurtCam.MC.player.getPitch() - 15.0f, false), 5, () -> InventoryUtils.selectItemFromHotbar(Items.END_CRYSTAL));
            }
        });
        this.placeObiClock = placeObiAfter;
        this.highlight = placement;
        this.targetPredictedPos = targetPosAtBreakCrystal;
        this.renderClock = 40;
    }

    private Vec3d simulatePos(Vec3d start, Vec3d velocity, int ticks) {
        for (int i = 0; i < ticks; ++i) {
            double j = velocity.getX();
            double k = velocity.getY();
            double l = velocity.getZ();
            if (Math.abs(j) < 0.003) {
                j = 0.0;
            }
            if (Math.abs(k) < 0.003) {
                k = 0.0;
            }
            if (Math.abs(l) < 0.003) {
                l = 0.0;
            }
            velocity = new Vec3d(j, k, l);
            double g = 0.0;
            velocity = velocity.add(0.0, (g -= 0.08) * 0.98, 0.0);
            velocity = velocity.multiply(0.91, 1.0, 0.91);
            start = start.add(velocity);
        }
        return start;
    }

    private Vec3d calcTargetKB(LivingEntity target) {
        float h = NoHurtCam.MC.player.getAttackCooldownProgress(0.5f);
        int i = EnchantmentHelper.getKnockback((LivingEntity) NoHurtCam.MC.player);
        if (NoHurtCam.MC.player.isSprinting() && (double)h > 0.9) {
            ++i;
        }
        double strength = (float)i * 0.5f;
        double x = MathHelper.sin((float)(NoHurtCam.MC.player.getYaw() * ((float)Math.PI / 180)));
        double z = -MathHelper.cos((float)(NoHurtCam.MC.player.getYaw() * ((float)Math.PI / 180)));
        Iterable<ItemStack> armors = target.getArmorItems();
        double kbRes = 0.0;
        for (ItemStack e : armors) {
            ArmorItem armorItem;
            Item item = e.getItem();
            if (!(item instanceof ArmorItem) || (armorItem = (ArmorItem)item).getMaterial() != ArmorMaterials.NETHERITE) continue;
            kbRes += 0.1;
        }
        strength *= 1.0 - target.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE) - kbRes;
        Vec3d result = Vec3d.ZERO;
        if (strength > 0.0) {
            Vec3d vec3d = new Vec3d(target.getX() - target.prevX, target.getY() - target.prevY, target.getZ() - target.prevZ);
            Vec3d vec3d2 = new Vec3d(x, 0.0, z).normalize().multiply(strength);
            result = new Vec3d(vec3d.x / 2.0 - vec3d2.x, target.isOnGround() ? Math.min(0.4, vec3d.y / 2.0 + strength) : vec3d.y, vec3d.z / 2.0 - vec3d2.z);
        }
        return result;
    }

    @Override
    public void onGameRender(MatrixStack matrixStack, float tickDelta) {
        if (this.renderClock == 0 || this.highlight == null) {
            return;
        }
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glEnable(2884);
        GL11.glDisable(2929);
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        RenderSystem.setShaderColor(0.4f, 1.0f, 0.4f, 0.4f);
        matrixStack.push();
        RenderUtils.applyRegionalRenderOffset(matrixStack);
        BlockPos blockPos = RenderUtils.getCameraBlockPos();
        int regionX = (blockPos.getX() >> 9) * 512;
        int regionZ = (blockPos.getZ() >> 9) * 512;
        matrixStack.push();
        matrixStack.translate((float)(this.highlight.getX() - regionX), (float)this.highlight.getY(), (float)(this.highlight.getZ() - regionZ));
        RenderUtils.drawSolidBox(new Box(BlockPos.ORIGIN), matrixStack);
        matrixStack.pop();
        matrixStack.push();
        matrixStack.translate(this.targetPredictedPos.getX() - (double)regionX, this.targetPredictedPos.getY(), this.targetPredictedPos.getZ() - (double)regionZ);
        RenderUtils.drawSolidBox(NoHurtCam.MC.player.getBoundingBox().offset(NoHurtCam.MC.player.getPos().multiply(-1.0)), matrixStack);
        matrixStack.pop();
        matrixStack.pop();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
}*/