package yor42.animearsenal.gameobject.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import yor42.animearsenal.init.blockinit;

public class animeArsenalMachines extends CreativeTabs {
    public animeArsenalMachines(String label) {
        super(label);
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(Item.getItemFromBlock(blockinit.DIMENSIONAL_BRIDGE));
    }
}
