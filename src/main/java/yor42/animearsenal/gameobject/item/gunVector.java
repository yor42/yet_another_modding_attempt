package yor42.animearsenal.gameobject.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import yor42.animearsenal.gameobject.entity.projectile.entityBullet;
import yor42.animearsenal.init.iteminit;


import javax.annotation.Nullable;
import java.util.List;

public class gunVector extends Item {

    public World world;

    public gunVector(String name, CreativeTabs tab){
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(tab);

        iteminit.ITEMS.add(this);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TextComponentTranslation(this.getUnlocalizedName()+".tooltip").getFormattedText());
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack item = playerIn.getHeldItem(handIn);
        world = worldIn;
        entityBullet bullet = new entityBullet(world,  playerIn);
        bullet.shoot(playerIn, playerIn.rotationPitch,playerIn.rotationYaw,0.0F, 3.0F, 1.0F);
        world.spawnEntity(bullet);
        return new ActionResult<ItemStack>(EnumActionResult.PASS, item);
    }

}
