package yor42.animearsenal.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import yor42.animearsenal.gameobject.entity.projectile.entityBullet;
import yor42.animearsenal.main;
import yor42.animearsenal.model.entity.projectile.modelEntityBullet;

import javax.annotation.Nullable;

public class renderBullet extends Render {
    private static final ResourceLocation TEXTURE = new ResourceLocation(main.MOD_ID+":"+"textures/entity/projectile/modelentitybullet.png");

    public renderBullet(RenderManager manager){
        super(manager);
        shadowSize=0.1F;
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

    public void render(entityBullet bullet, double d, double d1, double d2, float f, float f1) {
        bindEntityTexture(bullet);
        GL11.glPushMatrix();
        float f3 = this.getRenderYaw(bullet.prevRotationYaw, bullet.rotationYaw, f1);
        float f4 = bullet.prevRotationPitch + (bullet.rotationPitch - bullet.prevRotationPitch) * f1;
        GL11.glTranslatef((float) d, (float) d1, (float) d2);
        GlStateManager.rotate(90F, 0.0F, 1.0F, 0.0F);
        ModelBase model = new modelEntityBullet();
        model.render(bullet, 0.0F, 0.0F, 0.0F, f3, f4, 0.0625F);
        GlStateManager.enableAlpha();
        GL11.glPopMatrix();
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
        render((entityBullet) entity, d, d1, d2, f, f1);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return TEXTURE;
    }
}
