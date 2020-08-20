package yor42.animearsenal.gameobject.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import yor42.animearsenal.init.blockinit;
import yor42.animearsenal.init.iteminit;

public class blockBase extends Block {

    public blockBase(String name, Material materialIn, CreativeTabs tabs) {
        super(materialIn);
        setUnlocalizedName(name);
        setRegistryName(name);
        blockinit.BLOCKS.add(this);
        setCreativeTab(tabs);
        iteminit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }
}
