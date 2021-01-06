package yor42.animearsenal.gameobject.item;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import truefantasy.animcolle.entity.projectile.EntityMeguminExplosion;
import yor42.animearsenal.gameobject.entity.projectile.entityRailgunProjectile;
import yor42.animearsenal.init.soundInit;
import yor42.animearsenal.main;

import javax.annotation.Nullable;
import java.util.List;


public class meguStaff extends itembase {

    public meguStaff(String name){
        super(name, main.ANIMEARSENAL_WEAPONS);
    }


    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public ActionResult onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (!playerIn.capabilities.isCreativeMode && playerIn.getFoodStats().getFoodLevel() <= 6.0F || playerIn.experienceLevel < 10)
        {
            playerIn.sendMessage(new TextComponentTranslation(this.getUnlocalizedName()+".unabletocast"));
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);
        }
        else {
            playerIn.setActiveHand(handIn);
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TextComponentTranslation(this.getUnlocalizedName()+".tooltip").getFormattedText());
        tooltip.add(new TextComponentTranslation(this.getUnlocalizedName()+".tooltip2").getFormattedText());
    }

    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }

    protected RayTraceResult TraceTarget(World worldIn, EntityPlayer playerIn, boolean useLiquids)
    {
        float f = playerIn.rotationPitch;
        float f1 = playerIn.rotationYaw;
        double d0 = playerIn.posX;
        double d1 = playerIn.posY + (double)playerIn.getEyeHeight();
        double d2 = playerIn.posZ;
        Vec3d vec3d = new Vec3d(d0, d1, d2);
        float f2 = MathHelper.cos(-f1 * 0.017453292F - (float)Math.PI);
        float f3 = MathHelper.sin(-f1 * 0.017453292F - (float)Math.PI);
        float f4 = -MathHelper.cos(-f * 0.017453292F);
        float f5 = MathHelper.sin(-f * 0.017453292F);
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d3 = 128;
        Vec3d vec3d1 = vec3d.addVector((double)f6 * d3, (double)f5 * d3, (double)f7 * d3);
        return worldIn.rayTraceBlocks(vec3d, vec3d1, useLiquids, !useLiquids, false);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BLOCK;
    }

    public static float getCharge(int charge)
    {
        float f = (float)charge / 20.0F;

        if (f > 5.0F)
        {
            f = 5.0F;
        }

        return f;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
        if (entityLiving instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
            RayTraceResult raytraceresult = this.TraceTarget(worldIn, entityplayer, false);

            int i = this.getMaxItemUseDuration(stack) - timeLeft;
            if ((double)getCharge(i) >= 5.0D)
            {

                if (!worldIn.isRemote)
                {
                    double x, y, z;

                    if (raytraceresult.typeOfHit != null) {
                        switch (raytraceresult.typeOfHit) {
                            case ENTITY:
                                x = raytraceresult.entityHit.posX;
                                y = raytraceresult.entityHit.posY;
                                z = raytraceresult.entityHit.posZ;
                                worldIn.createExplosion(entityplayer, x, y, z, (float) (entityplayer.getFoodStats().getFoodLevel() /2), false);
                                break;
                            case BLOCK:
                                x = raytraceresult.getBlockPos().getX();
                                y = raytraceresult.getBlockPos().getY();
                                z = raytraceresult.getBlockPos().getZ();
                                worldIn.createExplosion(entityplayer, x, y, z, (float) (entityplayer.getFoodStats().getFoodLevel() /2), false);
                                break;
                            case MISS:
                                break;
                        }
                    }
                    if (!entityplayer.isCreative()) {
                        stack.damageItem(1, entityplayer);
                        entityplayer.getFoodStats().setFoodLevel(0);
                        entityplayer.getFoodStats().setFoodSaturationLevel(0);
                        entityplayer.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 1200, 1));
                        entityplayer.addExperienceLevel(-10);
                    }

                }
            }

        }
    }

}
