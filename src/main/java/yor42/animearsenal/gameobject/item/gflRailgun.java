package yor42.animearsenal.gameobject.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import truefantasy.animcolle.Main;

import javax.annotation.Nullable;

import static yor42.animearsenal.init.iteminit.RAILGUN_AMMO;

public class gflRailgun extends itembase {

    protected int magMaxCapacity;
    protected int magContents;

    public gflRailgun(String name){
        super(name, Main.animcolleweapon);
        this.maxStackSize = 1;
        this.magMaxCapacity = 5;
        this.magContents = 5;

        this.addPropertyOverride(new ResourceLocation("open"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                if (entityIn == null)
                {
                    return 0.0F;
                }
                else
                {
                    return !(entityIn.getActiveItemStack().getItem() instanceof gflRailgun) ? 0.0F : (float)(stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0F;
                }
            }
        });
        this.addPropertyOverride(new ResourceLocation("charging"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
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
        return (double)this.getAmmoCount(stack) / (double)this.getMaxAmmo(stack);
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean updateItemStackNBT(NBTTagCompound nbt) {
        super.updateItemStackNBT(nbt);
        this.magMaxCapacity = nbt.getInteger("magMaxCapacity");
        this.magContents = nbt.getInteger("magContents");
        return true;
    }

    public void setMaxAmmo(ItemStack stack, int maxAmmoCount){
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        stack.getTagCompound().setInteger("magMaxCapacity", maxAmmoCount);
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
        if (stack.getTagCompound() == null || !stack.getTagCompound().hasKey("magContents")) {
            return 0;
        }
        else return stack.getTagCompound().getInteger("magContents");
    }

    protected int addAmmo(ItemStack stack, int count){
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        if (stack.getTagCompound() == null || !stack.getTagCompound().hasKey("magContents")) {
            return 0;
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

    public boolean isMagEnpty(ItemStack stack){
        return getAmmoCount(stack) == 0;
    }

    @Override
    public ActionResult onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        boolean flag = !this.findAmmo(playerIn).isEmpty();

        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, flag);
        if (ret != null) return ret;

        if ((!playerIn.capabilities.isCreativeMode && !flag)|| this.magContents == 0)
        {
            return flag ? new ActionResult<>(EnumActionResult.PASS, itemstack) : new ActionResult<>(EnumActionResult.FAIL, itemstack);
        }
        else if (this.isMagEnpty(itemstack) && flag && !playerIn.capabilities.isCreativeMode){
            int spareammo = itemstack.getCount();

            ItemStack stack = playerIn.getHeldItem(handIn);

            if (spareammo > this.getMaxAmmo(stack)){
                spareammo = this.getMaxAmmo(stack);
            }

            this.addAmmo(stack, spareammo);
            itemstack.shrink(spareammo);

            playerIn.getCooldownTracker().setCooldown(this, 20*spareammo);
        }
        else
        {
            playerIn.setActiveHand(handIn);
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
        }
        return new ActionResult<>(EnumActionResult.FAIL, itemstack);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
        if (entityLiving instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
            boolean isPlayerCreative = entityplayer.capabilities.isCreativeMode;
            ItemStack itemstack = this.findAmmo(entityplayer);

            int i = this.getMaxItemUseDuration(stack) - timeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, entityplayer, i, !itemstack.isEmpty() || isPlayerCreative);
            if (i < 0) return;

            if (!itemstack.isEmpty() || isPlayerCreative)
            {
                if (itemstack.isEmpty())
                {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float charge = getCharge(i);

                if ((double)charge >= 1.0D)
                {
                    boolean flag1 = entityplayer.capabilities.isCreativeMode || (itemstack.getItem() instanceof ItemArrow && ((ItemArrow) itemstack.getItem()).isInfinite(itemstack, stack, entityplayer));

                    if (!worldIn.isRemote)
                    {

                        //currently fires arrow for debugging.

                        ItemArrow itemarrow = (ItemArrow)(itemstack.getItem() instanceof ItemArrow ? itemstack.getItem() : Items.ARROW);
                        EntityArrow entityarrow = itemarrow.createArrow(worldIn, itemstack, entityplayer);
                        entityarrow.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, charge * 3.0F, 1.0F);

                        worldIn.spawnEntity(entityarrow);
                        this.useAmmo(stack);
                    }

                    worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + charge * 0.5F);

                    if (!flag1 && !entityplayer.capabilities.isCreativeMode)
                    {
                        itemstack.shrink(1);

                        if (itemstack.isEmpty())
                        {
                            entityplayer.inventory.deleteStack(itemstack);
                        }
                    }
                }
            }
        }
    }
}
