package yor42.animearsenal.gameobject.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import yor42.animearsenal.init.iteminit;

public class animeArsenalWeapons extends CreativeTabs {

    public animeArsenalWeapons(String label) {
        super(label);
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(iteminit.MEGU_STAFF);
    }
}
