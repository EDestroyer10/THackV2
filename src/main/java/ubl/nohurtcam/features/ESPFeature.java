package ubl.nohurtcam.features;

import java.util.List;
import java.util.ArrayList;

import net.minecraft.entity.player.PlayerEntity;
import ubl.nohurtcam.events.UpdateListener;
import ubl.nohurtcam.feature.Feature;
import ubl.nohurtcam.setting.BooleanSetting;
import net.minecraft.entity.Entity;

import static ubl.nohurtcam.NoHurtCam.MC;

public class ESPFeature extends Feature implements UpdateListener {
    private List<Entity> targetEntities = new ArrayList<>();
    private final BooleanSetting invisibles = new BooleanSetting("Invisibles", "It just renders invis entities", true, this);

    public ESPFeature() {
        super("ESP", "Sense nearby users!");
        get = this;
    }

    @Override
    public void onUpdate() {
        assert MC.world != null;
        for (Entity e : MC.world.getEntities()) {
            if (e instanceof PlayerEntity && !e.isGlowing()) {
                e.setGlowing(true);
            }
        }
        if (invisibles.isEnabled()) {
            MC.world.getEntities().forEach(entity -> {
                if (entity.isInvisible() && !targetEntities.contains(entity)) {
                    targetEntities.add(entity);
                    entity.setInvisible(false);
                }
            });
        }
    }
    @Override
    public void onEnable() {
        assert MC.world != null;
        for (Entity e : MC.world.getEntities()) {
            if (e instanceof PlayerEntity && !e.isGlowing()) {
                e.setGlowing(true);
            }
        }
        eventManager.add(UpdateListener.class, this);
    }

    @Override
    public void onDisable() {
        assert MC.world != null;
        for (Entity e : MC.world.getEntities()) {
            if (e instanceof PlayerEntity && !e.isGlowing()) {
                e.setGlowing(false);
            }
        }
        eventManager.remove(UpdateListener.class, this);
        if (invisibles.isEnabled()) {
            targetEntities.forEach(entity -> {entity.setInvisible(true);});
            targetEntities.clear();
        }
    super.onDisable();
    }


    public boolean shouldRenderEntity(Entity entity) {
        if (!isEnabled()) return false;
        if (entity == null) return false;
        
        return true;
    }

    public static ESPFeature get;
}
