package yor42.animearsenal.init;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yor42.animearsenal.gameobject.entity.projectile.entityPlasmaArrow;
import yor42.animearsenal.gameobject.entity.projectile.entityRailgunProjectile;
import yor42.animearsenal.render.renderBullet;
import yor42.animearsenal.gameobject.entity.projectile.entityBullet;
import yor42.animearsenal.render.renderPlasmaArrow;
import yor42.animearsenal.render.renderRailgunProjectile;

@SideOnly(Side.CLIENT)
public class initEntityRender {
    public static void RegisterEntityRender(){
        RenderingRegistry.registerEntityRenderingHandler(entityBullet.class, renderBullet::new);
        RenderingRegistry.registerEntityRenderingHandler(entityPlasmaArrow.class, renderPlasmaArrow::new);
        RenderingRegistry.registerEntityRenderingHandler(entityRailgunProjectile.class, renderRailgunProjectile::new);
    }

    public static void RegisterItemRenderer(){}
}
