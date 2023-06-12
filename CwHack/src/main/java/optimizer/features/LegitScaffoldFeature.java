package optimizer.features;

import optimizer.events.UpdateListener;
import optimizer.feature.Feature;

import static optimizer.Client.MC;

public class LegitScaffoldFeature extends Feature implements UpdateListener {

    public LegitScaffoldFeature() {
        super("LegitScaffold", "Middle Click a player to get their ping.");
    }

    @Override
    public void onEnable() {
        super.onEnable();
        eventManager.add(UpdateListener.class, this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        eventManager.remove(UpdateListener.class, this);
    }

    boolean turn = true;


    @Override
    public void onUpdate() {
        if (MC.world.getBlockState(MC.player.getSteppingPos()).isAir()) {
            if (!MC.player.isOnGround()) return;
            turn = true;
            MC.options.sneakKey.setPressed(true);
        } else if (turn) {
            turn = false;
            MC.options.sneakKey.setPressed(false);
        }
    }
}