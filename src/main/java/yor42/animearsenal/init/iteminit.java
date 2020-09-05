package yor42.animearsenal.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;
import scala.xml.PrettyPrinter;
import truefantasy.animcolle.Main;
import yor42.animearsenal.gameobject.item.*;

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

    public static final Item RAILGUN_AMMO = new itembase("railgun_shell", animcolleweapon);

    public static final Item.ToolMaterial MEGUMIN_WEAPON = EnumHelper.addToolMaterial("megumin_weapon", 0, 15000, 2.0F, 24.0F, 40);
    public static final Item.ToolMaterial YAE_EQUIP = EnumHelper.addToolMaterial("yae_equip", 0, 15350, 3.0F, 24.0F, 20);
    public static final Item.ToolMaterial HARU_EQUIP = EnumHelper.addToolMaterial("haru_equip", 0, 15350, 3.0F, 24.0F, 20);
    public static final Item.ToolMaterial CHEN_EQUIP = EnumHelper.addToolMaterial("chen_equip", 0, 15350, 3.0F, 24.0F, 20);
    public static final Item.ToolMaterial TEXAS_EQUIP = EnumHelper.addToolMaterial("texas_equip", 0, 512, 3.0F, 24.0F, 20);

    public static final Item AQUA_FAN = new itembase("aquafan", Main.animcolleweapon);
    public static final Item EXU_VECTOR = new gunVector("exuvector", Main.animcolleweapon);
    public static final Item SOULUM_SWORD = new toolSword("soulumsword", HARU_EQUIP);
    public static final Item SAKURA_BLOSSOM = new toolSword("sakurablossom", YAE_EQUIP);
    public static final Item SHIELD_LORDCHALDEAS = new toolShield("lordchaldeas", CreativeTabs.COMBAT, 1200);
    public static final Item MEGU_STAFF = new toolSword("megustaff", MEGUMIN_WEAPON);
    public static final Item CV6_BOW = new compoundBow("compoundbow");
    public static final Item CHIXIAO = new toolSword("chixiao", CHEN_EQUIP);
    public static final Item ORIGINIUM_SWORD = new toolSword("originiumsword", TEXAS_EQUIP);
    public static final Item SF6024 = new gflRailgun("railgun");

}
