package ubl.nohurtcam.mixin;

import ubl.nohurtcam.features.ESPFeature;
import net.minecraft.entity.Entity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(EntityRenderDispatcher.class)
public class RenderEntityDispatcherMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public <E extends Entity> void onRenderPre(E entity, double x, double y, double z, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo info) {
        if (ESPFeature.get.shouldRenderEntity(entity)) {
            GL11.glPolygonOffset(1.0f, -1600000.0f);
            GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
        }
    }

    @Inject(method = "render", at = @At("RETURN"))
    public <E extends Entity> void onRenderPost(E entity, double x, double y, double z, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo info) {
        if (ESPFeature.get.shouldRenderEntity(entity)) {
            GL11.glPolygonOffset(1.0f, 1600000.0f);
            GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
        }
    }
}//alright its probably RenderBlockEntityDispatcher