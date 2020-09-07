package yor42.animearsenal.gameobject.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.List;

public class entityRailgunProjectile extends EntityThrowable {
    public entityRailgunProjectile(World worldIn) {
        super(worldIn);
    }

    public entityRailgunProjectile(World worldIn, EntityLivingBase shooterIn) {
        super(worldIn, shooterIn);
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
                result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 30.0f);
            }
        }

        if (!this.world.isRemote)
        {
            this.setDead();
            if(result.entityHit != null){
                this.world.createExplosion(this, result.entityHit.posX, result.entityHit.posY, result.entityHit.posZ, 2.0F, false);
            }
            else {
                this.world.createExplosion(this, result.getBlockPos().getX(), result.getBlockPos().getY(), result.getBlockPos().getZ(), 1.5F, false);
            }
        }
    }
}
