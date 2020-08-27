package yor42.animearsenal;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import truefantasy.animcolle.util.Reference;
import yor42.animearsenal.gameobject.entity.projectile.entityBullet;
import yor42.animearsenal.handler.guiHandler;
import yor42.animearsenal.init.*;
import yor42.animearsenal.proxy.commonProxy;
import yor42.animearsenal.registry.tileentityRegistry;
import yor42.animearsenal.util.reference;

@Mod(
        modid = main.MOD_ID,
        name = main.MOD_NAME,
        version = main.VERSION,
        dependencies = "required-after:animcolle"
)
public class main {

    public static final String MOD_ID = "animearsenal";
    public static final String MOD_NAME = "Anime Arsenal";
    public static final String VERSION = "2020.8-0.1.0";

    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static main INSTANCE;

    @SidedProxy(clientSide = reference.Client, serverSide = reference.Server)
    public static commonProxy proxy;

    /**
     * This is the first initialization event. Register tile entities here.
     * The registry events below will have fired prior to entry to this method.
     */
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        initEntityRender.RegisterEntityRender();
    }

    /**
     * This is the second initialization event. Register custom recipes
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    NetworkRegistry.INSTANCE.registerGuiHandler(main.INSTANCE, new guiHandler());
        recipeInit.initializeFurnaceRecipes();
    }

    /**
     * This is the final initialization event. Register actions from other mods here
     */
    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {

    }

    /**
     * Forge will automatically look up and bind blocks to the fields in this class
     * based on their registry name.
     */
    @GameRegistry.ObjectHolder(MOD_ID)
    public static class Blocks {
      /*
          public static final MySpecialBlock mySpecialBlock = null; // placeholder for special block below
      */
    }

    /**
     * Forge will automatically look up and bind items to the fields in this class
     * based on their registry name.
     */
    @GameRegistry.ObjectHolder(MOD_ID)
    public static class Items {
      /*
          public static final ItemBlock mySpecialBlock = null; // itemblock for the block above
          public static final MySpecialItem mySpecialItem = null; // placeholder for special item below
      */
    }

    /**
     * This is a special class that listens to registry events, to allow creation of mod blocks and items at the proper time.
     */
    @Mod.EventBusSubscriber
    public static class ObjectRegistryHandler {
        /**
         * Listen for the register event for creating custom items
         */
        @SubscribeEvent
        public static void addItems(RegistryEvent.Register<Item> event) {
           event.getRegistry().registerAll(iteminit.ITEMS.toArray(new Item[0]));
        }

        /**
         * Listen for the register event for creating custom blocks
         */
        @SubscribeEvent
        public static void addBlocks(RegistryEvent.Register<Block> event) {
            event.getRegistry().registerAll(blockinit.BLOCKS.toArray(new Block[0]));
            tileentityRegistry.registerTileentities();
        }

        @SubscribeEvent
        public static void addModels(ModelRegistryEvent event) {
            for(Item item : iteminit.ITEMS){
                main.proxy.registerItemRenderer(item, 0, "inventory");
            }
            for(Block block : blockinit.BLOCKS){
                main.proxy.registerItemRenderer(Item.getItemFromBlock(block), 0, "inventory");
            }
        }
    }
    /* EXAMPLE ITEM AND BLOCK - you probably want these in separate files
    public static class MySpecialItem extends Item {

    }

    public static class MySpecialBlock extends Block {

    }
    */
}
