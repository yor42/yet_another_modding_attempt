package yor42.animearsenal.gameobject.blocks;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import yor42.animearsenal.gameobject.blocks.tileentity.tileentityDimensionalBridge;
import yor42.animearsenal.init.blockinit;
import yor42.animearsenal.main;
import yor42.animearsenal.util.reference;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class blockDimensionBridge extends blockMachineBase {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyBool ACTIVE = PropertyBool.create("active");


    public blockDimensionBridge(String name) {
        super(name);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(new TextComponentTranslation(this.getUnlocalizedName()+".tooltip").getFormattedText());
        tooltip.add(new TextComponentTranslation(this.getUnlocalizedName()+".tooltip2").getFormattedText());
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(blockinit.DIMENSIONAL_BRIDGE);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote){
            TileEntity tile = worldIn.getTileEntity(pos);

            if (tile instanceof tileentityDimensionalBridge){
                playerIn.openGui(main.INSTANCE, reference.GUI_DIMENSIONBRIDGE, worldIn,pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    public static void setState(boolean active, World worldIn, BlockPos pos)
    {
        IBlockState state = worldIn.getBlockState(pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if(active) worldIn.setBlockState(pos, blockinit.DIMENSIONAL_BRIDGE.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(ACTIVE, true), 3);
        else worldIn.setBlockState(pos, blockinit.DIMENSIONAL_BRIDGE.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(ACTIVE, false), 3);

        if(tileentity != null)
        {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state){
        return new tileentityDimensionalBridge();
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new tileentityDimensionalBridge();
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos,this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()),2);
        if(stack.hasDisplayName()){
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof tileentityDimensionalBridge)
            {
                ((tileentityDimensionalBridge)tileEntity).setCustomInventoryName(stack.getDisplayName());
            }
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state){
        tileentityDimensionalBridge tileentity = (tileentityDimensionalBridge) worldIn.getTileEntity(pos);
        worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), tileentity.machineItemStacks.getStackInSlot(0)));
        worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), tileentity.machineItemStacks.getStackInSlot(1)));
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }
}
