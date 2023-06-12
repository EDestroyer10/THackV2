package optimizer.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.SnowGolemEntity;

import java.util.ArrayList;
import java.util.List;

import static optimizer.Client.MC;

public class EntityUtils {

    @SuppressWarnings("unchecked")
    public static <T extends Entity> List<T> findEntities(Class<T> entityClass) {
        List<T> entities = new ArrayList<>();
        for (Entity entity : MC.world.getEntities()) {
            if (entity.equals(MC.player)) continue;
            if (entityClass.isAssignableFrom(entity.getClass())) {
                entities.add((T) entity);
            }
        }
        return entities;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Entity> T findClosest(Class<T> entityClass, float range) {
        for (Entity entity : MC.world.getEntities()) {
            if (entityClass.isAssignableFrom(entity.getClass()) && !entity.equals(MC.player) && entity.distanceTo(MC.player) <= range) {
                return (T) entity;
            }
        }
        return null;
    }


    public static boolean isAnimal(Entity e) {
        return e instanceof PassiveEntity
                || e instanceof AmbientEntity
                || e instanceof WaterCreatureEntity
                || e instanceof IronGolemEntity
                || e instanceof SnowGolemEntity;
    }
}