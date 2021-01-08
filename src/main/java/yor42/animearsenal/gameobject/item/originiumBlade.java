package yor42.animearsenal.gameobject.item;

import com.google.common.collect.Multimap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import yor42.animearsenal.configuration;

public class originiumBlade extends toolSword{
    public originiumBlade(String name, ToolMaterial material) {
        super(name, material, configuration.WEAPONS.originiumBlade_attackspeed);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        super.hitEntity(stack, target, attacker);
        if (stack.getItem() instanceof originiumBlade){
            target.addPotionEffect(new PotionEffect(MobEffects.WITHER, configuration.WEAPONS.originiumblade_effectduration));
        }
        return true;
    }
}
