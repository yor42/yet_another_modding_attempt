package yor42.animearsenal.gameobject.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class entityBullet extends EntityThrowable {

    public String bulletCalibur = "";

    public entityBullet(World worldIn) {
        super(worldIn);
    }

    public entityBullet(World worldIn, EntityLivingBase shooterIn, double x, double y, double z, String calibur) {
        super(worldIn);
        this.setPosition(x, y, z);
        this.thrower = shooterIn;
        bulletCalibur = calibur;
    }

    public float getDamageFromCalibur(String calibur){
        switch (calibur){
            default:
                return 0;
            case "5.56x45":
                return 5;
            case "45ACP":
                return 3;
        }
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.entityHit != null)
        {

            if (result.entityHit == this.thrower)
            {
                return;
            }
            else
            {
                result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 3.0f);
            }
        }

        if (!this.world.isRemote)
        {
            this.setDead();
        }
    }


}
