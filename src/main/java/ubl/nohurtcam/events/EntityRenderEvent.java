/*
 * Copyright (c) Shadow client, Saturn5VFive and contributors 2022. All rights reserved.
 */

package ubl.nohurtcam.events;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class EntityRenderEvent extends RenderEvent {

    final Entity target;

    public EntityRenderEvent(MatrixStack stack, Entity e) {
        super(stack);
        this.target = e;
    }

    public Entity getEntity() {
        return target;
    }
}