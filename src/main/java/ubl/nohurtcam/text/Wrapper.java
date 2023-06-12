package ubl.nohurtcam.text;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;

public interface Wrapper {
		
	Tessellator TESSELLATOR = Tessellator.getInstance();
	BufferBuilder BUILDER = TESSELLATOR.getBuffer();
	
}