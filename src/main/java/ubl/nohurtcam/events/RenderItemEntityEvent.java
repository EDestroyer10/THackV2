/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package ubl.nohurtcam.events;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.random.Random;

public class RenderItemEntityEvent extends Cancellable {
    private static final RenderItemEntityEvent CWHACK = new RenderItemEntityEvent();

    public ItemEntity itemEntity;
    public float f;
    public float tickDelta;
    public MatrixStack matrixStack;
    public VertexConsumerProvider vertexConsumerProvider;
    public int light;
    public Random random;
    public ItemRenderer itemRenderer;

    public static RenderItemEntityEvent get(ItemEntity itemEntity, float f, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, Random random, ItemRenderer itemRenderer) {
        RenderItemEntityEvent.CWHACK.setCancelled(false);
        RenderItemEntityEvent.CWHACK.itemEntity = itemEntity;
        RenderItemEntityEvent.CWHACK.f = f;
        RenderItemEntityEvent.CWHACK.tickDelta = tickDelta;
        RenderItemEntityEvent.CWHACK.matrixStack = matrixStack;
        RenderItemEntityEvent.CWHACK.vertexConsumerProvider = vertexConsumerProvider;
        RenderItemEntityEvent.CWHACK.light = light;
        RenderItemEntityEvent.CWHACK.random = random;
        RenderItemEntityEvent.CWHACK.itemRenderer = itemRenderer;
        return CWHACK;
    }
}