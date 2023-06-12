package ubl.nohurtcam.features;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.SwordItem;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import ubl.nohurtcam.events.EventTarget;
import ubl.nohurtcam.events.PacketHelper;
import ubl.nohurtcam.events.UpdateListener;
import ubl.nohurtcam.feature.Feature;
import ubl.nohurtcam.mixin.MoveHelper;
import ubl.nohurtcam.setting.BooleanSetting;
import ubl.nohurtcam.setting.DecimalSetting;
import ubl.nohurtcam.setting.EnumSetting;

import static ubl.nohurtcam.NoHurtCam.MC;


public class TriggerBotFeature extends Feature implements UpdateListener {

    private final EnumSetting<Mode> mode = new EnumSetting<>("mode", "the mode it uses", Mode.values(), Mode.All, this);
    public DecimalSetting hitCooldown = new DecimalSetting("Hit cooldown", "cooldown", 0.9, this);
    public DecimalSetting critDistance = new DecimalSetting("Crit distance", "critdistance", 3.0, this);
    public BooleanSetting autoCrit = new BooleanSetting("Smart Sprint", "autocrit", true, this);
    public BooleanSetting players = new BooleanSetting("players", "true", true, this);
    public BooleanSetting monsters = new BooleanSetting("monsters", "mobs", true, this);
    public BooleanSetting invisibles = new BooleanSetting("invisibles", "true", true, this);

    public BooleanSetting block = new BooleanSetting("block", "cancel while blocking", false,this);

    public TriggerBotFeature() {
        super("TriggerBot", "basically just crits better");
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
    };

    @EventTarget
    public void onUpdate() {
        assert MC.player != null;

        if (!MC.player.isBlocking() || !this.block.isEnabled()) {
            if (!MC.player.isUsingItem() || !this.block.isEnabled()) {
                if (!(MC.currentScreen instanceof HandledScreen)) {
                    assert MC.player != null;

                    if (!MC.player.isUsingItem()) {
                        if (this.itemInHand()){

                            HitResult hit = MC.crosshairTarget;

                            assert hit != null;

                            if (hit.getType() == HitResult.Type.ENTITY) {
                                if (!((double) MC.player.getAttackCooldownProgress(0.0F) < this.hitCooldown.getValue())) {
                                    Entity target = ((EntityHitResult)hit).	getEntity();
                                    if (target instanceof PlayerEntity) {
                                        assert MC.interactionManager != null;

                                        MC.interactionManager.attackEntity(MC.player, target);
                                        MC.player.swingHand(Hand.MAIN_HAND);
                                        HitResult var4 = MC.crosshairTarget;
                                        if (var4 instanceof EntityHitResult entityResult) {
                                            if (!this.isValidEntity(entityResult.getEntity())) {
                                                return;
                                            }
                                            if ((MC.player.isOnGround() || (double) MC.player.fallDistance >= critDistance.getValue() || this.hasFlyUtilities()) && this.autoCrit.isEnabled() && !MC.player.isOnGround() && MoveHelper.hasMovement()) {
                                                PacketHelper.sendPacket(new ClientCommandC2SPacket(MC.player, ClientCommandC2SPacket.Mode.STOP_SPRINTING));
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    private boolean isValidEntity(final Entity crossHairTarget) {
        if (crossHairTarget instanceof ClientPlayerEntity) {
            return false;
        }
        if (!players.isEnabled() && crossHairTarget instanceof PlayerEntity) {
            return true;
        }
        if (!monsters.isEnabled() && crossHairTarget instanceof MobEntity) {
            return false;
        }
        return invisibles.isEnabled() || (!crossHairTarget.isInvisible() && !crossHairTarget.isInvisibleTo(MC.player));
    }

    private boolean hasFlyUtilities() {
        assert MC.player != null;
        return MC.player.getAbilities().flying;
    }
    private enum Mode {
        Sword,
        All,
        Any
    }

    private boolean itemInHand() {
        assert MC.player != null;
        final Item item = MC.player.getMainHandStack().getItem();
        {
            if (mode.getValue() == Mode.Sword)
                return item instanceof SwordItem;
            if (mode.getValue() == Mode.All)
                return item instanceof SwordItem || item instanceof AxeItem || item instanceof PickaxeItem;
            if (mode.getValue() == Mode.Any) ;
            return true;
        }
    }
}

