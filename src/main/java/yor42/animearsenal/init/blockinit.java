package yor42.animearsenal.init;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import yor42.animearsenal.gameobject.blocks.basicalloysmelter;
import yor42.animearsenal.gameobject.blocks.blockBase;

import java.util.ArrayList;
import java.util.List;

public class blockinit {
    public static final List<Block> BLOCKS = new ArrayList<Block>();

    public static final Block TEST_BLOCK = new blockBase("testblock", Material.ROCK, CreativeTabs.BUILDING_BLOCKS);
    public static final Block ALLOY_SMELTER_BASIC = new basicalloysmelter("basicalloysmelter");
}
