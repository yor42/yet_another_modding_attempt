package yor42.animearsenal;

import net.minecraftforge.common.config.Config;

@Config(modid = main.MOD_ID)
public class configuration {

    public static machines MACHINES = new machines();
    public static weapons WEAPONS = new weapons();


    public static class machines{

        @Config.Comment("Internal Battery size of dimension bridge. (Default = 5000)")
        public int buffer_dimension_bridge = 5000;

        @Config.Comment("Maximum FE per tick that dimension bridge can take. (Default = 600)")
        public int maxPowerInput_dimensionbridge = 600;

        @Config.Comment("Power consumption of dimension bridge. (Default = 500)")
        public int powerConsumption_DimensionBridge = 500;
    }

    public static class weapons{
        //I wanted to adjust value for each weapon, but im too lazy. i'm sorry.
        public int sakurablossom_durability = 759;
        public float sakurablossom_damage = 4;
        public int sakurablossom_enchantability = 15;
        public double sakurablossom_attackspeed = -1.5;

        public int soulumsword_durability = 2154;
        public float soulumsword_damage = 12;
        public int soulumsword_enchantability = 8;
        public double soulumsword_attackspeed = -3.5;

        public int chixiao_durability = 1572;
        public float chixiao_damage = 6;
        public int chixiao_enchantability = 15;
        public double chixiao_attackspeed = -2.8;

        public int originiumBlade_durability = 130;
        public float originiumBlade_damage = 8;
        public int originiumBlade_enchantability = 12;
        public double originiumBlade_attackspeed = -1.3;
        public int originiumblade_effectduration = 400;

        public int lordchaldeas_durability = 2500;

        public int megustaff_durability = 600;
        public int megustaff_levelcost = 10;
        public boolean megustaff_drainhunger = true;
        public boolean megustaff_applydebuffs = true;


    }

    public static class misc{
        @Config.Comment("This mod gives specific --weeby-- Item to developer(yor42 guy) on initial spawn. this turns it off completely\nI won't judge but let me just say this. How could you :c (default=\"FALSE\")")
        public boolean bad_dev_no_cookie = false;
    }

}
