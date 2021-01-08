package yor42.animearsenal.gameobject.blocks.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import yor42.animearsenal.gameobject.blocks.blockOriginiumGenerator;
import yor42.animearsenal.init.iteminit;
import yor42.animearsenal.util.CustomEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static yor42.animearsenal.gameobject.blocks.blockOriginiumGenerator.FACING;

public class tileentityOriginiumGenerator extends TileEntity implements ITickable {

    private final int internal_battery = 10000;
    private final int maxoutput = 2500;
    private final int maxreceive = 2500;
    private boolean active=false;
    private int FEpertick;
    private int totalFuelTime;
    private int remainingFuelTime;

    private boolean pausegeneration;

    public int getremainingFuelTime(){
        return remainingFuelTime;
    }

    public CustomEnergyStorage buffer = new CustomEnergyStorage(internal_battery, maxreceive, maxoutput);

    // Itemstacks currently in this tileentity

    public ItemStackHandler machineItemStacks = new ItemStackHandler(1);

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
            return new TextComponentTranslation("container.originiumgenerator");
        }
    }

    /**
     * Get the name of this object. For players this returns their username
     */
    public String getName()
    {
        return this.hasCustomName() ? this.machineCustomName : "container.dimensionalbridge";
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
        this.remainingFuelTime = compound.getInteger("fueltime");
        this.totalFuelTime = compound.getInteger("totalfueltime");
        this.FEpertick = compound.getInteger("fepertick");
        this.buffer.readfromNBT(compound);
        this.active = compound.getBoolean("active");

        if (compound.hasKey("CustomName", 8))
        {
            this.machineCustomName = compound.getString("CustomName");
        }
    }


    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("fueltime", this.remainingFuelTime);
        compound.setInteger("totalfueltime", this.totalFuelTime);
        compound.setInteger("fepertick", this.FEpertick);
        compound.setTag("Inventory", this.machineItemStacks.serializeNBT());
        compound.setInteger("energy", this.buffer.getEnergyStored());
        this.buffer.writetoNBT(compound);
        compound.setBoolean("active", this.active);


        if (this.hasCustomName())
        {
            compound.setString("CustomName", this.machineCustomName);
        }

        return compound;
    }

    public boolean isoutoffuel(){
        return this.remainingFuelTime ==0;
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

    public boolean isActive()
    {
        return this.active;
    }

    @Override
    public void update() {
        boolean flag = this.isActive();
        boolean flag1 = false;

        if (!this.world.isRemote)
        {
            ItemStack fuel = this.machineItemStacks.getStackInSlot(0);

            if(!fuel.isEmpty()||this.remainingFuelTime>=0) {
                if (this.isoutoffuel()&&isItemFuel(fuel)) {
                    this.active= true;
                    this.FEpertick = getFEpertick(fuel);
                    this.totalFuelTime=getFuelTime(fuel);
                    this.remainingFuelTime = this.totalFuelTime;
                    if (this.isActive()) {
                        flag1 = true;
                        fuel.shrink(1);
                    }
                }
                if(this.remainingFuelTime>=0){

                    if(this.buffer.getEnergyStored()>=this.buffer.getMaxEnergyStored()&& this.remainingFuelTime>0){
                        this.active=false;
                        this.pausegeneration = true;
                    }
                    else {
                        this.active=true;
                        this.pausegeneration=false;
                        this.remainingFuelTime--;
                        this.buffer.receiveEnergy(FEpertick, false);
                    }
                }
                else {
                    this.remainingFuelTime=0;
                }
            }
            else{
                this.totalFuelTime=0;
                this.active=false;
                this.FEpertick=0;
            }
            if (flag != this.isActive())
            {
                flag1 = true;
                blockOriginiumGenerator.setState(this.isActive(), this.world, this.pos);
            }
            transferenergy();
        }
        if (flag1)
        {
            this.markDirty();
        }

    }

    private void transferenergy() {
        IBlockState state = getWorld().getBlockState(getPos());
        EnumFacing enumfacing = state.getValue(FACING).getOpposite();
        TileEntity tileEntity = world.getTileEntity(getPos().offset(enumfacing));
        if( tileEntity != null){
            if (tileEntity.hasCapability(CapabilityEnergy.ENERGY, state.getValue(FACING))){
                if(tileEntity.getCapability(CapabilityEnergy.ENERGY, state.getValue(FACING)).canReceive()&&(this.buffer.getEnergyStored()>=0)){
                    this.buffer.extractEnergy(maxoutput, false);
                    tileEntity.getCapability(CapabilityEnergy.ENERGY, state.getValue(FACING)).receiveEnergy(maxoutput, false);
                }
            }
        }
    }

    private int getFEpertick(ItemStack fuel){
        Item item = fuel.getItem();
        if(item == iteminit.PUREORIGINIUM){
            return 500;
        }
        return 0;
    }

    private int getFuelTime(ItemStack fuel) {

        Item item = fuel.getItem();

        if (item == iteminit.PUREORIGINIUM){
            return 2000;
        }
        return 0;
    }

    public boolean isItemFuel(ItemStack stack){
        return getFuelTime(stack)>0;
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

    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
            return isItemFuel(stack);
    }

    public int getField(int id)
    {
        switch (id)
        {
            case 0:
                return this.remainingFuelTime;
            case 1:
                return this.buffer.getEnergyStored();
            case 2:
                return this.FEpertick;
            case 3:
                return this.totalFuelTime;
            default:
                return 0;
        }
    }

    public void setField(int id, int value)
    {
        switch (id)
        {
            case 0:
                this.remainingFuelTime = value;
                break;
            case 1:
                this.buffer.setEnergy(value);
                break;
            case 2:
                this.FEpertick = value;
                break;
            case 3:
                this.totalFuelTime = value;
                break;
            default:
                break;
        }
    }
}
