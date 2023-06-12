package ubl.nohurtcam.events;

import ubl.nohurtcam.event.Event;
import net.minecraft.network.packet.Packet;

import java.util.ArrayList;

public class EventPacketReceive extends Event {

    private Packet<?> packet;
    private final Mode mode;

    public EventPacketReceive(Packet<?> packet, Mode mode) {
        this.packet = packet;
        this.mode = mode;
    }

    public Packet<?> getPacket() {
        return packet;
    }

    public Mode getMode() {
        return mode;
    }

    public void setPacket(Packet<?> packet) {
        this.packet = packet;
    }

    @Override
    public void fire(ArrayList listeners) {

    }

    @Override
    public Class getListenerType() {
        return null;
    }

    public enum Mode {
        PRE, POST
    }
}