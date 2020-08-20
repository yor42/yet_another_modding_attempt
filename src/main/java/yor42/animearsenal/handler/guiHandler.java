package yor42.animearsenal.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import yor42.animearsenal.gameobject.blocks.container.containeralloysmelter;
import yor42.animearsenal.gameobject.blocks.tileentity.tileentitybasicalloysmelter;
import yor42.animearsenal.gameobject.gui.guiBasicAlloySmelter;
import yor42.animearsenal.util.reference;

import javax.annotation.Nullable;

public class guiHandler implements IGuiHandler {
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == reference.GUI_ALLOYSMELTER){
            return new containeralloysmelter(player.inventory, (tileentitybasicalloysmelter)world.getTileEntity(new BlockPos(x,y,z)));
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == reference.GUI_ALLOYSMELTER){
            return new guiBasicAlloySmelter(player.inventory, (tileentitybasicalloysmelter)world.getTileEntity(new BlockPos(x,y,z)));
        }
        return null;
    }
}
