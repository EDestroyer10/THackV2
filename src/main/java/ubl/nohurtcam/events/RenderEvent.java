/*
 * Copyright (c) Shadow client, Saturn5VFive and contributors 2022. All rights reserved.
 */

package ubl.nohurtcam.events;

import ubl.nohurtcam.event.Event;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;

public class RenderEvent extends Event {

    final MatrixStack stack;

    public RenderEvent(MatrixStack stack) {
        this.stack = stack;
    }

    public MatrixStack getStack() {
        return stack;
    }

    @Override
    public void fire(ArrayList listeners) {

    }

    @Override
    public Class getListenerType() {
        return null;
    }
}