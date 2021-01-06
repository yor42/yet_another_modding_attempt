package yor42.animearsenal.gameobject.blocks.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import truefantasy.animcolle.init.ItemInit;
import yor42.animearsenal.gameobject.blocks.blockDimensionBridge;
import yor42.animearsenal.gameobject.blocks.container.containerDimensionBridge;
import yor42.animearsenal.gameobject.recipes.bridgetargets;
import yor42.animearsenal.util.CustomEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Function;

import static yor42.animearsenal.gameobject.blocks.blockDimensionBridge.FACING;

public class tileentityDimensionalBridge extends TileEntity implements ITickable {

    //0 - slot for key item
    //1 - slot for compressed matter

    private Function<World, Entity> Entity;

    private final int internal_battery = 100000;
    private final int maxoutput = 0;
    private final int maxreceive = 5000;
    private boolean active;
    private int isobstructed;
    private final int energyusage = 500;

    public int getTotalProcessTime(){
        return totalProcessTime;
    }

    public CustomEnergyStorage buffer = new CustomEnergyStorage(internal_battery, maxreceive, maxoutput);

    // Itemstacks currently in this tileentity

    public ItemStackHandler machineItemStacks = new ItemStackHandler(2);

    private int processTime;
    private int totalProcessTime = 200;
    private String machineCustomName;

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        IBlockState state = getWorld().getBlockState(getPos());
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
        if(capability == CapabilityEnergy.ENERGY&&facing == state.getValue(FACING).getOpposite()) return true;
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        IBlockState state = getWorld().getBlockState(getPos());
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (T) this.machineItemStacks;
        if(capability == CapabilityEnergy.ENERGY&&facing == state.getValue(FACING).getOpposite()) return (T)this.buffer;
        return super.getCapability(capability, facing);
    }

    @Nullable
    @Override
    public ITextComponent getDisplayName() {
        if (this.hasCustomName()){
            return new TextComponentString(this.machineCustomName);
        }
        else{
            return new TextComponentTranslation("container.dimension_bridge");
        }
    }

    /**
     * Get the name of this object. For players this returns their username
     */
    public String getName()
    {
        return this.hasCustomName() ? this.machineCustomName : "container.dimensionalbridge";
    }

    public int getEnergyusage(){
        return energyusage;
    }

    /**
     * Returns true if this thing is named
     */
    public boolean hasCustomName()
    {
        return this.machineCustomName != null && !this.machineCustomName.isEmpty();
    }

    public void setCustomInventoryName(String name)
    {
        this.machineCustomName = name;
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.machineItemStacks.deserializeNBT(compound.getCompoundTag("Inventory"));
        this.processTime = compound.getInteger("processTime");
        this.totalProcessTime = compound.getInteger("totalProcessTime");
        this.buffer.readfromNBT(compound);
        this.isobstructed = compound.getInteger("isobstructed");

        if (compound.hasKey("CustomName", 8))
        {
            this.machineCustomName = compound.getString("CustomName");
        }
    }


    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("processTime", (short)this.processTime);
        compound.setInteger("totalProcessTime", (short)this.totalProcessTime);
        compound.setTag("Inventory", this.machineItemStacks.serializeNBT());
        compound.setInteger("energy", this.buffer.getEnergyStored());
        compound.setInteger("isobstructed", this.isobstructed);
        this.buffer.writetoNBT(compound);


        if (this.hasCustomName())
        {
            compound.setString("CustomName", this.machineCustomName);
        }

        return compound;
    }

    public int isobstructed(){
        for(int x=1;x<3;x++){
            if(this.world.getBlockState(this.pos.up(x)).getBlock() != Blocks.AIR){
                return 1;
            }
        }
        return 0;
    }

    /**
     * Gets the update packet that is used to sync the TE on load
     */
    @Override
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(getPos(), 1, getUpdateTag());
    }

    /**
     * Gets the update tag send by packets. Contains base data (i.e. position), as well as TE specific data
     */
    @Nonnull
    @Override
    public NBTTagCompound getUpdateTag()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        writeToNBT(nbt);
        return nbt;
    }

    /**
     * is machine running son?
     */
    public boolean isActive()
    {
        return this.active;
    }

    @Override
    public void update() {

        boolean flag = this.isActive();
        boolean flag1 = false;
        this.isobstructed = isobstructed();

        if (!this.world.isRemote)
        {

            //debug code
            //debugenergy();

            ItemStack catalyst = this.machineItemStacks.getStackInSlot(1);
            ItemStack KeyItem = this.machineItemStacks.getStackInSlot(0);

            transferenergy();

            if(this.isobstructed == 0) {
                if (!catalyst.isEmpty() && !KeyItem.isEmpty()) {
                    if (this.foundRecipe() && this.buffer.getEnergyStored() >= energyusage) {
                        ++this.processTime;

                        this.active = true;

                        this.buffer.extractEnergy(energyusage, false);

                        if (this.processTime == this.totalProcessTime) {
                            this.processTime = 0;
                            this.output();
                            flag1 = true;
                        }
                    } else {
                        this.processTime = 0;
                        this.active = false;
                    }
                } else {
                    this.processTime = 0;
                    this.active = false;
                }
            }
            else {
                this.processTime = 0;
                this.active = false;
            }

            if (flag != this.isActive())
            {
                flag1 = true;
                blockDimensionBridge.setState(this.isActive(), this.world, this.pos);
            }
        }

        if (flag1)
        {
            this.markDirty();
        }

    }

    private void debugenergy() {
        if(world.isBlockPowered(pos)){
            this.buffer.receiveEnergy(6000, false);
        }
    }


    private void transferenergy() {

        IBlockState state = getWorld().getBlockState(getPos());
        EnumFacing enumfacing = state.getValue(FACING).getOpposite();
        TileEntity tileEntity = world.getTileEntity(getPos().offset(enumfacing));
        if( tileEntity != null){
            if (tileEntity.hasCapability(CapabilityEnergy.ENERGY, state.getValue(FACING))){
                if(tileEntity.getCapability(CapabilityEnergy.ENERGY, state.getValue(FACING)).canExtract()&&!(this.buffer.getEnergyStored()==this.buffer.getMaxEnergyStored())){
                        this.buffer.receiveEnergy(maxreceive, false);
                        tileEntity.getCapability(CapabilityEnergy.ENERGY, state.getValue(FACING)).extractEnergy(maxreceive, false);
                }
            }
        }

    }

    private boolean foundRecipe()
    {
        if (((ItemStack)this.machineItemStacks.getStackInSlot(0)).isEmpty())
        {
            return false;
        }
        else
        {
            Function<World, Entity> entity = bridgetargets.instance().getRecipeResult(this.machineItemStacks.getStackInSlot(0));

            return !(entity == null);
        }
    }

    public boolean isUsableByPlayer(EntityPlayer player)
    {
        if (this.world.getTileEntity(this.pos) != this)
        {
            return false;
        }
        else
        {
            return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    private void output(){

        //0 - slot for key item
        //1 - slot for compressed matter
        ItemStack itemstack = this.machineItemStacks.getStackInSlot(0);
        ItemStack itemstack1 = this.machineItemStacks.getStackInSlot(1);
        this.Entity = bridgetargets.instance().getRecipeResult(this.machineItemStacks.getStackInSlot(0));
        if(!this.world.isRemote) {
            Entity mob = this.Entity.apply(this.world);

            mob.setLocationAndAngles(getPos().getX()+0.5, getPos().getY()+1.1, getPos().getZ()+0.5, 0,0);

            world.spawnEntity(mob);
            //Im too lazy to do this shit
            //dospawneffect()
        }



        itemstack.shrink(1);
        itemstack1.shrink(1);
    }

    public int getField(int id)
    {
        switch (id)
        {
            case 0:
                return this.processTime;
            case 1:
                return this.buffer.getEnergyStored();
            case 2:
                return this.isobstructed;
            default:
                return 0;
        }
    }

    public void setField(int id, int value)
    {
        switch (id)
        {
            case 0:
                this.processTime = value;
                break;
            case 1:
                this.buffer.setEnergy(value);
                break;
            case 2:
                this.isobstructed = value;
            default:
                break;
        }
    }

}
