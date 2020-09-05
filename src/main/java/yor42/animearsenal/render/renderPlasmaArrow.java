package yor42.animearsenal.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import yor42.animearsenal.gameobject.entity.projectile.entityBullet;
import yor42.animearsenal.gameobject.entity.projectile.entityPlasmaArrow;
import yor42.animearsenal.main;
import yor42.animearsenal.model.entity.projectile.modelEntityBullet;
import yor42.animearsenal.model.entity.projectile.modelPlasmaArrow;

import javax.annotation.Nullable;

public class renderPlasmaArrow extends Render {
    public renderPlasmaArrow(RenderManager renderManagerIn) {
        super(renderManagerIn);
        shadowSize = 0.1F;
    }

    private float getRenderYaw(float p_82400_1_, float p_82400_2_, float p_82400_3_)
    {
        float f;

        for (f = p_82400_2_ - p_82400_1_; f < -180.0F; f += 360.0F)
        {
            ;
        }

        while (f >= 180.0F)
        {
            f -= 360.0F;
        }

        return p_82400_1_ + p_82400_3_ * f;
    }

    public void render(Entity bullet, double d, double d1, double d2, float f, float f1) {

        bindEntityTexture(bullet);
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d, (float) d1, (float) d2);

        float f3 = this.getRenderYaw(bullet.prevRotationYaw, bullet.rotationYaw, f1);
        float f4 = bullet.prevRotationPitch + (bullet.rotationPitch - bullet.prevRotationPitch) * f1;

        ModelBase model = new modelPlasmaArrow();
        model.render(bullet, 0.0F, 0.0F, 0.0F, f3, f4, 0.1F);
        GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(bullet.prevRotationYaw + (bullet.rotationYaw - bullet.prevRotationYaw) * f1 - 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(bullet.prevRotationPitch + (bullet.rotationPitch - bullet.prevRotationPitch) * f1, 0.0F, 0.0F, 1.0F);

        GlStateManager.translate(-4.0F, 0.0F, 0.0F);
        GlStateManager.enableAlpha();
        GL11.glPopMatrix();
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks)
    {
        this.render((entityPlasmaArrow) entity, x, y, z, yaw, partialTicks);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return new ResourceLocation(main.MOD_ID+":textures/entity/projectile/plasmaarrow.png");
    }

}
