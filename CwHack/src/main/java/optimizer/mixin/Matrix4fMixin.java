package optimizer.mixin;

import optimizer.mixinterface.IMatrix4f;
import optimizer.utils.math.Vec4d;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Matrix4f.class)
public class Matrix4fMixin implements IMatrix4f
{
	protected float a00;
	protected float a10;
protected float a20;
protected float a30;

protected float a01;
protected float a11;
protected float a21;
protected float a31;

protected float a02;
protected float a12;
protected float a22;
protected float a32;

protected float a03;
protected float a13;
protected float a23;
protected float a33;

	@Override
	public Vec4d multiply(Vec4d v) {
		return new Vec4d(
				a00 * v.x + a01 * v.y + a02 * v.z + a03 * v.w,
				a10 * v.x + a11 * v.y + a12 * v.z + a13 * v.w,
				a20 * v.x + a21 * v.y + a22 * v.z + a23 * v.w,
				a30 * v.x + a31 * v.y + a32 * v.z + a33 * v.w
		);
	}

	@Override
	public Vec3d multiply(Vec3d vec) {
		return new Vec3d(
				vec.x * a00 + vec.y * a01 + vec.z * a02 + a03,
				vec.x * a10 + vec.y * a11 + vec.z * a12 + a13,
				vec.x * a20 + vec.y * a21 + vec.z * a22 + a23
		);
	}
}