package optimizer.features;

import optimizer.Client;
import optimizer.event.EventTarget;
import optimizer.setting.BooleanSetting;
import optimizer.setting.DecimalSetting;
import optimizer.setting.EnumSetting;
import optimizer.utils.EntityUtils;
import optimizer.utils.MoveHelper;
import optimizer.utils.PacketHelper;
import optimizer.events.UpdateListener;
import optimizer.feature.Feature;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;


public class TriggerBotFeature extends Feature implements UpdateListener {

    private final EnumSetting<Mode> mode = new EnumSetting<>("mode", "the mode it uses", Mode.values(), Mode.All, this);
    public DecimalSetting hitCooldown = new DecimalSetting("Hit cooldown", "cooldown", 0.9, this);
    public DecimalSetting critDistance = new DecimalSetting("Crit distance", "critdistance", 3.0, this);
    public BooleanSetting autoCrit = new BooleanSetting("Smart Sprint", "autocrit", true, this);
    public BooleanSetting players = new BooleanSetting("players", "true", true, this);
    public BooleanSetting monsters = new BooleanSetting("monsters", "mobs", true, this);
    public BooleanSetting animals = new BooleanSetting("animals", "mobs", true, this);
    public BooleanSetting villagers = new BooleanSetting("villagers", "mobs", false, this);
    public BooleanSetting invisibles = new BooleanSetting("invisibles", "true", true, this);

    public BooleanSetting block = new BooleanSetting("block", "cancel while blocking", false,this);

    private Entity target;

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
        assert Client.MC.player != null;

        if (!Client.MC.player.isBlocking() || !this.block.isEnabled()) {
            if (!Client.MC.player.isUsingItem() || !this.block.isEnabled()) {
                if (!(Client.MC.currentScreen instanceof HandledScreen)) {
                    assert Client.MC.player != null;

                    if (!Client.MC.player.isUsingItem()) {
                        if (this.itemInHand()){

                            HitResult hit = Client.MC.crosshairTarget;

                            assert hit != null;

                            if (hit.getType() == HitResult.Type.ENTITY) {
                                if (!((double) Client.MC.player.getAttackCooldownProgress(0.0F) < this.hitCooldown.getValue())) {
                                    Entity target = ((EntityHitResult)hit).	getEntity();
                                    if (target instanceof PlayerEntity) {
                                        assert Client.MC.interactionManager != null;

                                        Client.MC.interactionManager.attackEntity(Client.MC.player, target);
                                        Client.MC.player.swingHand(Hand.MAIN_HAND);
                                        HitResult var4 = Client.MC.crosshairTarget;
                                        if (var4 instanceof EntityHitResult) {
                                            EntityHitResult entityResult = (EntityHitResult)var4;
                                            if (!this.isValidEntity(entityResult.getEntity())) {
                                                return;
                                            }
                                            if ((Client.MC.player.isOnGround() || (double) Client.MC.player.fallDistance >= critDistance.getFloatValue() || this.hasFlyUtilities()) && this.autoCrit.isEnabled() && !Client.MC.player.isOnGround() && MoveHelper.hasMovement()) {
                                                PacketHelper.sendPacket(new ClientCommandC2SPacket(Client.MC.player, ClientCommandC2SPacket.Mode.STOP_SPRINTING));
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
            return false;
        }
        if (!animals.isEnabled() && EntityUtils.isAnimal(crossHairTarget)) {
            return false;
        }
        if (!monsters.isEnabled() && crossHairTarget instanceof MobEntity) {
            return false;
        }
        if (!villagers.isEnabled() && crossHairTarget instanceof VillagerEntity) {
            return false;
        }
        return invisibles.isEnabled() || (!crossHairTarget.isInvisible() && !crossHairTarget.isInvisibleTo(PacketHelper.mc.player));
    }

    private boolean hasFlyUtilities() {
        return PacketHelper.mc.player.getAbilities().flying;
    }
    private enum Mode {
        Sword,
        All,
        Any
    }

    private boolean itemInHand() {
        final Item item = Client.MC.player.getMainHandStack().getItem();
        {
            if (mode.getValue() == Mode.Sword)
                return item instanceof SwordItem;
            if (mode.getValue() == Mode.All)
                return item instanceof SwordItem || item instanceof AxeItem || item instanceof PickaxeItem || item instanceof ShovelItem || item instanceof HoeItem;
            if (mode.getValue() == Mode.Any) ;
            return true;
        }
    }
}

