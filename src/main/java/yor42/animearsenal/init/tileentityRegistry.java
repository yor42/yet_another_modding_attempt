package yor42.animearsenal.init;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import yor42.animearsenal.gameobject.blocks.tileentity.tileentityDimensionalBridge;
import yor42.animearsenal.gameobject.blocks.tileentity.tileentityOriginiumGenerator;
import yor42.animearsenal.gameobject.blocks.tileentity.tileentitybasicalloysmelter;

public class tileentityRegistry {

    private static final ResourceLocation alloysmelter = new ResourceLocation("basic_alloy_smelter");
    private static final ResourceLocation dimentionbridge = new ResourceLocation("dimensionbridge");
    private static final ResourceLocation generator_originium = new ResourceLocation("generator_originium");

    public static void registerTileentities(){
        GameRegistry.registerTileEntity(tileentitybasicalloysmelter.class, alloysmelter);
        GameRegistry.registerTileEntity(tileentityDimensionalBridge.class, dimentionbridge);
        GameRegistry.registerTileEntity(tileentityOriginiumGenerator.class, generator_originium);
    }
}
