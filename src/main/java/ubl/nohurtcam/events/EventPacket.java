/*
 * This file is part of the BleachHack distribution (https://github.com/BleachDev/BleachHack/).
 * Copyright (c) 2021 Bleach and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package ubl.nohurtcam.events;

import ubl.nohurtcam.event.Event;
import net.minecraft.network.packet.Packet;

import java.util.ArrayList;

/**
 * @author sl
 */
public class EventPacket extends Event {

	private Packet<?> packet;

	public EventPacket(Packet<?> packet) {
		this.packet = packet;
	}

	public Packet<?> getPacket() {
		return packet;
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

	public static class Read extends EventPacket {

		public Read(Packet<?> packet) {
			super(packet);
		}

	}
	
	public static class Send extends EventPacket {

		public Send(Packet<?> packet) {
			super(packet);
		}
		
	}
}