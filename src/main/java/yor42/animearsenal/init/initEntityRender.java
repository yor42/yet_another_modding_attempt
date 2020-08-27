package yor42.animearsenal.init;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import yor42.animearsenal.render.renderBullet;
import yor42.animearsenal.gameobject.entity.projectile.entityBullet;

public class initEntityRender {
    public static void RegisterEntityRender(){
        RenderingRegistry.registerEntityRenderingHandler(entityBullet.class, renderManager -> new renderBullet(renderManager));
    }
}
