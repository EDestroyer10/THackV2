/*
 * Copyright (c) Shadow client, Saturn5VFive and contributors 2022. All rights reserved.
 */

package ubl.nohurtcam.events;

import ubl.nohurtcam.event.Event;
import net.minecraft.network.packet.Packet;

import java.util.ArrayList;

public class PacketEvent extends Event {

    private final Packet<?> packet;

    public PacketEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
    }

    @Override
    public void fire(ArrayList listeners) {

    }

    @Override
    public Class getListenerType() {
        return null;
    }
}