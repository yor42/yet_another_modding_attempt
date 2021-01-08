package yor42.animearsenal.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Loader;
import scala.xml.PrettyPrinter;
import truefantasy.animcolle.Main;
import yor42.animearsenal.configuration;
import yor42.animearsenal.gameobject.item.*;
import yor42.animearsenal.main;

import java.util.ArrayList;
import java.util.List;

import static truefantasy.animcolle.Main.animcolleweapon;

public class iteminit {

    public static final List<Item> ITEMS = new ArrayList<Item>();

    //RESOURCE
    public static final Item PUREORIGINIUM = new itembase("pureoriginium", CreativeTabs.MISC);
    public static final Item WISDOMCUBE = new itembase("wisdomcube", CreativeTabs.MISC);
    public static final Item D32_STEEL_INGOT = new itembase("d32_ingot", CreativeTabs.MATERIALS);
    public static final Item MANGANESE_INGOT = new itembase("manganese_ingot", CreativeTabs.MATERIALS);
    public static final Item RMA_70_24_INGOT = new itembase("rma70_24_ingot", CreativeTabs.MATERIALS);
    public static final Item D32_SANDWICH_INGOT = new itembase("rma7024_manganese_sandwich", CreativeTabs.MATERIALS);
    public static final Item CARBON_FIBER = new itembase("carbonfiber", CreativeTabs.MATERIALS);


    //disc
    public static final Item VITALITY_DISC = new itemRecords("vitality_record", CreativeTabs.MISC, soundInit.vitality);

    //ammo
    public static final Item RAILGUN_AMMO = new itembase("railgun_shell", main.ANIMEARSENAL_WEAPONS);


    public static final Item.ToolMaterial YAE_EQUIP = EnumHelper.addToolMaterial("yae_equip", 0, configuration.WEAPONS.sakurablossom_durability, 3.0F, configuration.WEAPONS.sakurablossom_damage, configuration.WEAPONS.sakurablossom_enchantability);
    public static final Item.ToolMaterial HARU_EQUIP = EnumHelper.addToolMaterial("haru_equip", 0, configuration.WEAPONS.soulumsword_durability, 3.0F, configuration.WEAPONS.soulumsword_damage, configuration.WEAPONS.soulumsword_enchantability);
    public static final Item.ToolMaterial CHEN_EQUIP = EnumHelper.addToolMaterial("chen_equip", 0, configuration.WEAPONS.chixiao_durability, 3.0F, configuration.WEAPONS.chixiao_damage, configuration.WEAPONS.chixiao_enchantability);
    public static final Item.ToolMaterial TEXAS_EQUIP = EnumHelper.addToolMaterial("texas_equip", 0, configuration.WEAPONS.originiumBlade_durability, 3.0F, configuration.WEAPONS.originiumBlade_damage, configuration.WEAPONS.originiumBlade_enchantability);

    public static final Item AQUA_FAN = new itembase("aquafan", main.ANIMEARSENAL_WEAPONS);
    public static final Item EXU_VECTOR = new gunVector("exuvector", main.ANIMEARSENAL_WEAPONS);
    public static final Item SOULUM_SWORD = new toolSword("soulumsword", HARU_EQUIP, configuration.WEAPONS.chixiao_attackspeed);
    public static final Item SAKURA_BLOSSOM = new toolSword("sakurablossom", YAE_EQUIP, configuration.WEAPONS.sakurablossom_attackspeed);
    public static final Item SHIELD_LORDCHALDEAS = new toolShield("lordchaldeas", CreativeTabs.COMBAT, configuration.WEAPONS.lordchaldeas_durability);
    public static final Item MEGU_STAFF = new meguStaff("megustaff");
    public static final Item CV6_BOW = new compoundBow("compoundbow");
    public static final Item CHIXIAO = new toolSword("chixiao", CHEN_EQUIP, configuration.WEAPONS.soulumsword_attackspeed);
    public static final Item ORIGINIUM_SWORD = new originiumBlade("originiumsword", TEXAS_EQUIP);
    public static final Item SF6024 = new gflRailgun("railgun");

}
