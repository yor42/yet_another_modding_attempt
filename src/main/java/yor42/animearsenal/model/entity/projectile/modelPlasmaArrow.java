package yor42.animearsenal.model.entity.projectile;// Made with Blockbench 3.6.5
// Exported for Minecraft version 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;

public class modelPlasmaArrow extends ModelBase {
	private final ModelRenderer shape1;

	public modelPlasmaArrow() {
		textureWidth = 32;
		textureHeight = 32;

		shape1 = new ModelRenderer(this);
		shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
		shape1.cubeList.add(new ModelBox(shape1, 0, 1, -0.5F, -0.5F, -11.0F, 1, 1, 16, 0.0F, false));
		shape1.cubeList.add(new ModelBox(shape1, 11, 14, -0.5F, -1.5F, -10.0F, 1, 1, 1, 0.0F, false));
		shape1.cubeList.add(new ModelBox(shape1, 19, 14, -0.5F, 0.5F, -10.0F, 1, 1, 1, 0.0F, false));
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