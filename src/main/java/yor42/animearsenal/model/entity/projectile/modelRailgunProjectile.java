package yor42.animearsenal.model.entity.projectile;// Made with Blockbench 3.6.5
// Exported for Minecraft version 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class modelRailgunProjectile extends ModelBase {
	private final ModelRenderer shape1;

	public modelRailgunProjectile() {
		textureWidth = 32;
		textureHeight = 32;

		shape1 = new ModelRenderer(this);
		shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
		shape1.cubeList.add(new ModelBox(shape1, 1, 1, -0.5F, -0.5F, -4.0F, 1, 1, 8, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		shape1.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}