package yor42.animearsenal.gameobject.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import truefantasy.animcolle.entity.projectile.EntityBullet;
import yor42.animearsenal.gameobject.entity.projectile.entityBullet;
import yor42.animearsenal.init.iteminit;


import javax.annotation.Nullable;
import java.util.List;

public class gunVector extends Item {

    public gunVector(String name, CreativeTabs tab){
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(tab);


        iteminit.ITEMS.add(this);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TextComponentTranslation(this.getUnlocalizedName()+".tooltip").getFormattedText());
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack item = playerIn.getHeldItem(handIn);
        Vec3d look = playerIn.getLookVec();
        entityBullet bullet = new entityBullet(worldIn, playerIn, playerIn.posX+look.x*1.5D, playerIn.posY+look.y*1.5D, playerIn.posZ+look.z*1.5D, "45ACP");
        bullet.shoot(playerIn, playerIn.rotationPitch,playerIn.rotationYaw,0.0F, 5.0F, 1.0F);
        worldIn.spawnEntity(bullet);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
    }
}
