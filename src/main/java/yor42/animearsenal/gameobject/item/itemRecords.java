package yor42.animearsenal.gameobject.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import yor42.animearsenal.init.iteminit;

import javax.annotation.Nullable;
import java.util.List;

public class itemRecords extends ItemRecord {

    public itemRecords(String name, CreativeTabs tab, SoundEvent track){
        super(name, track);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(tab);

        iteminit.ITEMS.add(this);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TextComponentTranslation(this.getUnlocalizedName()+".tooltip").getFormattedText());
    }
}
