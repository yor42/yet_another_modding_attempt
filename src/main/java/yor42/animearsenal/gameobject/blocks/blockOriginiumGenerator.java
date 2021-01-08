package yor42.animearsenal.gameobject.blocks;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import yor42.animearsenal.gameobject.blocks.tileentity.tileentityDimensionalBridge;
import yor42.animearsenal.gameobject.blocks.tileentity.tileentityOriginiumGenerator;
import yor42.animearsenal.init.blockinit;
import yor42.animearsenal.main;
import yor42.animearsenal.util.reference;

import javax.annotation.Nullable;
import java.util.Random;

public class blockOriginiumGenerator extends blockMachineBase {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyBool ACTIVE = PropertyBool.create("active");

    public blockOriginiumGenerator(String name) {
        super(name);
        this.setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVE, false));
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(blockinit.ORIGINIUM_GENERATOR);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote){
            TileEntity tile = worldIn.getTileEntity(pos);

            if (tile instanceof tileentityOriginiumGenerator){
                playerIn.openGui(main.INSTANCE, reference.GUI_ORIGINIUM_GENERATOR, worldIn,pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    public static void setState(boolean active, World worldIn, BlockPos pos)
    {
        IBlockState state = worldIn.getBlockState(pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if(active) worldIn.setBlockState(pos, blockinit.ORIGINIUM_GENERATOR.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(ACTIVE, true), 3);
        else worldIn.setBlockState(pos, blockinit.ORIGINIUM_GENERATOR.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(ACTIVE, false), 3);

        if(tileentity != null)
        {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state){
        return new tileentityOriginiumGenerator();
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
            if (tileEntity instanceof tileentityOriginiumGenerator)
            {
                ((tileentityOriginiumGenerator)tileEntity).setCustomInventoryName(stack.getDisplayName());
            }
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state){
        tileentityOriginiumGenerator tileentity = (tileentityOriginiumGenerator) worldIn.getTileEntity(pos);
        worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), tileentity.machineItemStacks.getStackInSlot(0)));
        super.breakBlock(worldIn, pos, state);
    }



}
