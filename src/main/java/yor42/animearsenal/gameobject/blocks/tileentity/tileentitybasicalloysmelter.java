package yor42.animearsenal.gameobject.blocks.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import yor42.animearsenal.gameobject.blocks.basicalloysmelter;
import yor42.animearsenal.recipes.AlloyRecipe;

public class tileentitybasicalloysmelter extends TileEntity implements ITickable {
    //0,1 = input, 2= fuel, 3 = output
    private ItemStackHandler handler = new ItemStackHandler(4);
    private String customName;

    private int burnTime;
    private int currentBurnTime;
    private int cookTime;
    private int totalCookTime = 200;

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (T) this.handler;
        return super.getCapability(capability, facing);
    }

    public boolean hasCustomName()
    {
        return this.customName != null && !this.customName.isEmpty();
    }

    public void setCustomName(String customName)
    {
        this.customName = customName;
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return this.hasCustomName() ? new TextComponentString(this.customName) : new TextComponentTranslation("container.basic_alloy_smelter");
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.handler.deserializeNBT(compound.getCompoundTag("Inventory"));
        this.burnTime = compound.getInteger("BurnTime");
        this.cookTime = compound.getInteger("CookTime");
        this.totalCookTime = compound.getInteger("CookTimeTotal");
        this.currentBurnTime = getItemBurnTime(this.handler.getStackInSlot(2));

        if(compound.hasKey("CustomName", 8)) this.setCustomName(compound.getString("CustomName"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("BurnTime", (short)this.burnTime);
        compound.setInteger("CookTime", (short)this.cookTime);
        compound.setInteger("CookTimeTotal", (short)this.totalCookTime);
        compound.setTag("Inventory", this.handler.serializeNBT());

        if(this.hasCustomName()) compound.setString("CustomName", this.customName);
        return compound;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isBurning(tileentitybasicalloysmelter te)
    {
        return te.getField(0) > 0;
    }

    public void update()
    {
        boolean flag = isBurning(this);
        boolean flag1 = false;

        if (isBurning(this))
        {
            --this.burnTime;
        }

        if (!this.world.isRemote)
        {
            ItemStack fuelstack = this.handler.getStackInSlot(2);

            if (isBurning(this) || !fuelstack.isEmpty() && !handler.getStackInSlot(0).isEmpty() && !handler.getStackInSlot(1).isEmpty())
            {
                if (!isBurning(this))
                {
                    this.burnTime = getItemBurnTime(fuelstack);
                    this.currentBurnTime = this.burnTime;

                    if (isBurning(this))
                    {
                        flag1 = true;

                        if (!fuelstack.isEmpty())
                        {
                            Item item = fuelstack.getItem();
                            fuelstack.shrink(1);

                            if (fuelstack.isEmpty())
                            {
                                ItemStack item1 = item.getContainerItem(fuelstack);
                                this.handler.setStackInSlot(2, item1);
                            }
                        }
                    }
                }

                if (isBurning(this) && this.canSmelt())
                    //causes issue that processing stops when player takes out result item
                    //cansmelt() is the source of issue
                {
                    ++this.cookTime;

                    if (this.cookTime == 200)
                    {
                        this.cookTime = 0;
                        processitem();
                        flag1 = true;
                    }
                }
                else if(handler.getStackInSlot(0) == ItemStack.EMPTY || handler.getStackInSlot(0) == ItemStack.EMPTY )
                {
                    this.cookTime = 0;
                }
            }
            else if (!isBurning(this) && this.cookTime > 0)
            {
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);
            }

            if (flag != isBurning(this))
            {
                flag1 = true;
                basicalloysmelter.setState(isBurning(this), this.world, this.pos);
            }
        }

        if (flag1)
        {
            this.markDirty();
        }
    }

    public void processitem()
    {
        if (this.canSmelt())
        {
            ItemStack input1 = handler.getStackInSlot(0);
            ItemStack input2 = handler.getStackInSlot(1);
            ItemStack result = AlloyRecipe.getInstance().getalloyingResult(input1, input2);
            ItemStack itemstack2 = handler.getStackInSlot(3);


            if (itemstack2.isEmpty())
            {
                handler.insertItem(3, result, false);
            }
            else if (itemstack2.getItem() == result.getItem())
            {
                itemstack2.grow(1);
            }

            input1.shrink(1);
            input2.shrink(1);
        }
    }

    private boolean canSmelt()
    {
        ItemStack input1 = handler.getStackInSlot(0);
        ItemStack input2 = handler.getStackInSlot(1);
        if (input1.isEmpty() || input2.isEmpty())
        {
            return false;
        }
        else
        {
            ItemStack output = AlloyRecipe.getInstance().getalloyingResult(input1, input2);

            if (output.isEmpty())
            {
                return false;
            }
            else
            {
                ItemStack outputslotstack = this.handler.getStackInSlot(3);

                if (outputslotstack.isEmpty())
                {
                    return true;
                }
                else if (!outputslotstack.isItemEqual(output))
                {
                    return false;
                }
                else if (outputslotstack.getCount() + output.getCount() <= output.getMaxStackSize() && outputslotstack.getCount() + output.getCount() <= outputslotstack.getMaxStackSize())  // Forge fix: make furnace respect stack sizes in furnace recipes
                {
                    return true;
                }
                else
                {
                    return outputslotstack.getCount() + output.getCount() <= output.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
                }
            }
        }
    }

    public static int getItemBurnTime(ItemStack fuel)
    {
        if(fuel.isEmpty()) return 0;
        else
        {
            Item item = fuel.getItem();

            if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR)
            {
                Block block = Block.getBlockFromItem(item);

                if (block == Blocks.WOODEN_SLAB) return 150;
                if (block.getDefaultState().getMaterial() == Material.WOOD) return 300;
                if (block == Blocks.COAL_BLOCK) return 16000;
            }

            if (item instanceof ItemTool && "WOOD".equals(((ItemTool)item).getToolMaterialName())) return 200;
            if (item instanceof ItemSword && "WOOD".equals(((ItemSword)item).getToolMaterialName())) return 200;
            if (item instanceof ItemHoe && "WOOD".equals(((ItemHoe)item).getMaterialName())) return 200;
            if (item == Items.STICK) return 100;
            if (item == Items.COAL) return 1600;
            if (item == Items.LAVA_BUCKET) return 20000;
            if (item == Item.getItemFromBlock(Blocks.SAPLING)) return 100;
            if (item == Items.BLAZE_ROD) return 2400;

            return GameRegistry.getFuelValue(fuel);
        }
    }
    public static boolean isItemFuel(ItemStack fuel)
    {
        return getItemBurnTime(fuel) > 0;
    }

    public boolean isUsableByPlayer(EntityPlayer player)
    {
        return this.world.getTileEntity(this.pos) == this && player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }

    public int getField(int id)
    {
        switch(id)
        {
            case 0:
                return this.burnTime;
            case 1:
                return this.currentBurnTime;
            case 2:
                return this.cookTime;
            case 3:
                return this.totalCookTime;
            default:
                return 0;
        }
    }

    public void setField(int id, int value)
    {
        switch(id)
        {
            case 0:
                this.burnTime = value;
                break;
            case 1:
                this.currentBurnTime = value;
                break;
            case 2:
                this.cookTime = value;
                break;
            case 3:
                this.totalCookTime = value;
        }
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        int metadata = getBlockMetadata();
        return new SPacketUpdateTileEntity(this.pos, metadata, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return nbt;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        this.readFromNBT(tag);
    }

    @Override
    public NBTTagCompound getTileData() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return nbt;
    }

}
