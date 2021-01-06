package yor42.animearsenal.registry;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import yor42.animearsenal.gameobject.blocks.tileentity.tileentityDimensionalBridge;
import yor42.animearsenal.gameobject.blocks.tileentity.tileentitybasicalloysmelter;

public class tileentityRegistry {

    private static final ResourceLocation alloysmelter = new ResourceLocation("basic_alloy_smelter");
    private static final ResourceLocation dimentionbridge = new ResourceLocation("dimensionbridge");

    public static void registerTileentities(){
        GameRegistry.registerTileEntity(tileentitybasicalloysmelter.class, alloysmelter);
        GameRegistry.registerTileEntity(tileentityDimensionalBridge.class, dimentionbridge);
    }
}
