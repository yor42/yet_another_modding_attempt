package yor42.animearsenal.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class clientProxy extends commonProxy {
    @Override
    public void registerItemRenderer(Item item, int meta, String ID){
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), ID));
    }
}
