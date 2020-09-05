package yor42.animearsenal.init;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import yor42.animearsenal.gameobject.entity.projectile.entityPlasmaArrow;
import yor42.animearsenal.render.renderBullet;
import yor42.animearsenal.gameobject.entity.projectile.entityBullet;
import yor42.animearsenal.render.renderPlasmaArrow;

public class initEntityRender {
    public static void RegisterEntityRender(){
        RenderingRegistry.registerEntityRenderingHandler(entityBullet.class, renderBullet::new);
        RenderingRegistry.registerEntityRenderingHandler(entityPlasmaArrow.class, renderPlasmaArrow::new);
    }
}
