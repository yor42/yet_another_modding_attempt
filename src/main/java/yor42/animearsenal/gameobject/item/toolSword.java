package yor42.animearsenal.gameobject.item;

import com.google.common.collect.Multimap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import yor42.animearsenal.init.iteminit;
import yor42.animearsenal.main;

import javax.annotation.Nullable;
import java.util.List;

import static truefantasy.animcolle.Main.animcolleweapon;

public class toolSword extends ItemSword {

    private double attackspeed;

    public toolSword(String name, ToolMaterial material) {
        this(name, material, -2.4000000953674316D);
    }

    public toolSword(String name, ToolMaterial material, double attackspeed) {
        super(material);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(main.ANIMEARSENAL_WEAPONS);
        this.attackspeed = attackspeed;
        iteminit.ITEMS.add(this);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TextComponentTranslation(this.getUnlocalizedName()+".tooltip").getFormattedText());
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.getAttackDamage(), 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", this.attackspeed, 0));
        }

        return multimap;
    }
}
