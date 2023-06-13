package ubl.nohurtcam.mixin;

import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WorldRenderer.class)
public interface WorldRendererAccessor {

	@Accessor
	public abstract Framebuffer getEntityOutlinesFramebuffer();
	
	@Accessor
	public abstract void setEntityOutlinesFramebuffer(Framebuffer framebuffer);
	


	@Accessor
	public abstract Frustum getFrustum();
	
	@Accessor
	public abstract void setFrustum(Frustum frustum);
}