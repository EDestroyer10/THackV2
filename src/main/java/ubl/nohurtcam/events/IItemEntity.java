/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package ubl.nohurtcam.events;

import net.minecraft.util.math.Vec3d;

public interface IItemEntity {
    Vec3d getRotation();
    void setRotation(Vec3d rotation);
}