package yor42.animearsenal.registry;

import net.minecraftforge.fml.common.registry.GameRegistry;
import yor42.animearsenal.gameobject.blocks.tileentity.tileentitybasicalloysmelter;

public class tileentityRegistry {
    public static void registerTileentities(){
        GameRegistry.registerTileEntity(tileentitybasicalloysmelter.class, "basic_alloy_smelter");
    }
}
