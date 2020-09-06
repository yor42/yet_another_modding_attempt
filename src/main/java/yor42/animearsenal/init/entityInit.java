package yor42.animearsenal.init;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import truefantasy.animcolle.util.Reference;
import yor42.animearsenal.gameobject.entity.projectile.entityBullet;
import yor42.animearsenal.gameobject.entity.projectile.entityPlasmaArrow;
import yor42.animearsenal.gameobject.entity.projectile.entityRailgunProjectile;
import yor42.animearsenal.main;

@Mod.EventBusSubscriber(modid = main.MOD_ID)
public class entityInit {

    @SubscribeEvent
    public static void registerEntity(final RegistryEvent.Register<EntityEntry> event){
        final IForgeRegistry<EntityEntry> registry = event.getRegistry();

        initProjectiles(registry);

    }

    private static void initProjectiles(IForgeRegistry<EntityEntry> registry){
        createEntityEntry("bullet", entityBullet.class, registry, 512);
        createEntityEntry("plasmaarrow", entityPlasmaArrow.class, registry, 513);
        createEntityEntry("railgunProjectile", entityRailgunProjectile.class, registry, 514);
    }

    private static <T extends Entity> void createEntityEntry(String name, Class<T> cls, IForgeRegistry<EntityEntry> registry, int id) {
        EntityEntryBuilder<T> builder = EntityEntryBuilder.create();
        builder.entity(cls);
        builder.name(Reference.MODID + "." + name);
        builder.id(new ResourceLocation(Reference.MODID, name),id);
        builder.tracker(64, 3, true);
        registry.register(builder.build());
    }
}
