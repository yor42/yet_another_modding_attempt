package yor42.animearsenal.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class recipeInit {

    public static void initializeFurnaceRecipes(){
        GameRegistry.addSmelting(iteminit.D32_SANDWICH_INGOT, new ItemStack(iteminit.D32_STEEL_INGOT), 1.5F);
    }

    public static void initializeShincolleCompat(){

    }

}
