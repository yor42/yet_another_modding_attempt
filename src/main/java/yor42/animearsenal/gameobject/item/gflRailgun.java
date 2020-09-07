package yor42.animearsenal.gameobject.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import truefantasy.animcolle.Main;
import yor42.animearsenal.gameobject.entity.projectile.entityBullet;
import yor42.animearsenal.gameobject.entity.projectile.entityRailgunProjectile;
import yor42.animearsenal.init.soundInit;
import yor42.animearsenal.main;

import javax.annotation.Nullable;

import java.util.List;

import static yor42.animearsenal.init.iteminit.RAILGUN_AMMO;

public class gflRailgun extends itembase {

    public gflRailgun(String name){
        super(name, main.ANIMEARSENAL_WEAPONS);
        this.maxStackSize = 1;

        this.setMaxAmmo(new ItemStack(this), 5);
        this.addAmmo(new ItemStack(this), 5);

        this.addPropertyOverride(new ResourceLocation("charging"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TextComponentTranslation(this.getUnlocalizedName()+".ammocount").getFormattedText()+" "+getAmmoCount(stack)+"/"+getMaxAmmo(stack));

    }

    protected ItemStack findAmmo(EntityPlayer player)
    {
        if (this.isAmmo(player.getHeldItem(EnumHand.OFF_HAND)))
        {
            return player.getHeldItem(EnumHand.OFF_HAND);
        }
        else if (this.isAmmo(player.getHeldItem(EnumHand.MAIN_HAND)))
        {
            return player.getHeldItem(EnumHand.MAIN_HAND);
        }
        else
        {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
            {
                ItemStack itemstack = player.inventory.getStackInSlot(i);

                if (this.isAmmo(itemstack))
                {
                    return itemstack;
                }
            }

            return ItemStack.EMPTY;
        }
    }

    protected boolean isAmmo(ItemStack stack)
    {
        return stack.getItem() == RAILGUN_AMMO;
    }

    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }

    public static float getCharge(int charge)
    {
        float f = (float)charge / 40.0F;
        f = (f * f + f * 2.0F) / 3.0F;

        if (f > 1.0F)
        {
            f = 1.0F;
        }

        return f;
    }

    public double getDurabilityForDisplay(ItemStack stack)
    {
        return 1.0-(double)this.getAmmoCount(stack) / (double)this.getMaxAmmo(stack);
    }

    /**
     * TODO add custom animation
     */
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    public void setMaxAmmo(ItemStack stack, int maxAmmoCount){
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        stack.getTagCompound().setInteger("magMaxCapacity", maxAmmoCount);
    }

    public void setMagContents(ItemStack stack, int ammoCount) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        stack.getTagCompound().setInteger("magContents", ammoCount);
    }

    public int getMaxAmmo(ItemStack stack){
        if (stack.getTagCompound() == null || !stack.getTagCompound().hasKey("magMaxCapacity")){
            return 0;
        }
        return stack.getTagCompound().getInteger("magMaxCapacity");
    }
    public int getAmmoCount(ItemStack stack){
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        if(stack.getTagCompound() != null && stack.getTagCompound().hasKey("magContents")){
            return stack.getTagCompound().getInteger("magContents");
        }
        return 0;
    }

    protected int addAmmo(ItemStack stack, int count){
        if (!stack.hasTagCompound() || stack.getTagCompound() == null) {
            stack.setTagCompound(new NBTTagCompound());
        }
        int currentCount = stack.getTagCompound().getInteger("magContents");

        currentCount += count;
        stack.getTagCompound().setInteger("magContents", currentCount);
        return currentCount;
    }

    public void useAmmo(ItemStack stack){
        useAmmo(stack, 1);
    }

    public int useAmmo(ItemStack stack, int count){
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }

        if (stack.getTagCompound() == null || !stack.getTagCompound().hasKey("magContents")) {
            return 0;
        }

        int currentCount = stack.getTagCompound().getInteger("magContents");

        currentCount -= count;
        stack.getTagCompound().setInteger("magContents", currentCount);
        return currentCount;
    };

    public boolean isMagEmpty(ItemStack stack){
        return this.getAmmoCount(stack) == 0;
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        super.onCreated(stack, worldIn, playerIn);
        setMaxAmmo(stack, 5);
        setMagContents(stack,5);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public ActionResult onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        int remainingAmmoInInv = findAmmo(playerIn).getCount();

        //pre-adding support for force reload
        boolean isReloadable = remainingAmmoInInv >= this.getMaxAmmo(itemstack) - this.getAmmoCount(itemstack);

        if (!playerIn.capabilities.isCreativeMode && (!isReloadable && this.isMagEmpty(itemstack)))
        {
            worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, soundInit.railgun_noammo, SoundCategory.PLAYERS, 1.0F, 1.0F);
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);
        }
        else if (this.isMagEmpty(itemstack) && isReloadable && !playerIn.capabilities.isCreativeMode){

            if (remainingAmmoInInv > this.getMaxAmmo(itemstack)){
                remainingAmmoInInv = this.getMaxAmmo(itemstack);
            }

            this.addAmmo(itemstack, remainingAmmoInInv);
            this.findAmmo(playerIn).shrink(remainingAmmoInInv);

            playerIn.getCooldownTracker().setCooldown(itemstack.getItem(), 20*remainingAmmoInInv);
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
        }
        else {
            worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, soundInit.railgun_charge, SoundCategory.PLAYERS, 1.0F, 1.0F);
            playerIn.setActiveHand(handIn);
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
        }
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
        if (entityLiving instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
            boolean isPlayerCreative = entityplayer.capabilities.isCreativeMode;

            int i = this.getMaxItemUseDuration(stack) - timeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, entityplayer, i, !this.isMagEmpty(stack) || isPlayerCreative);
            if (i < 0) return;

            if (!this.isMagEmpty(stack) || isPlayerCreative)
            {

                float charge = getCharge(i);

                if ((double)charge >= 1.0D)
                {

                    if (!worldIn.isRemote)
                    {
                        entityRailgunProjectile bullet = new entityRailgunProjectile(worldIn, entityLiving);
                        bullet.shoot(entityLiving, entityLiving.rotationPitch,entityLiving.rotationYaw,0.0F, 6.0F, 1.0F);
                        worldIn.spawnEntity(bullet);

                        if (!isPlayerCreative) {
                            this.useAmmo(stack);
                        }
                    }
                    worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, soundInit.railgun_fire, SoundCategory.PLAYERS, 1.0F, 1.0F);
                }
            }
        }
    }
    /*
    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        if (slotChanged) {return true;}
        return getAmmoCount(oldStack) == 0;
    }

     */
}
