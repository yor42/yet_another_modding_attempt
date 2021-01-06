package yor42.animearsenal.init;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import yor42.animearsenal.gameobject.blocks.blockBasicAlloySmelter;
import yor42.animearsenal.gameobject.blocks.blockBase;
import yor42.animearsenal.gameobject.blocks.blockDimensionBridge;

import java.util.ArrayList;
import java.util.List;

public class blockinit {
    public static final List<Block> BLOCKS = new ArrayList<Block>();
    public static final Block ALLOY_SMELTER_BASIC = new blockBasicAlloySmelter("basicalloysmelter");
    public static final Block DIMENSIONAL_BRIDGE = new blockDimensionBridge("dimensionbridge");
}
