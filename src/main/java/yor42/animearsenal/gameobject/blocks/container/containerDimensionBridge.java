package yor42.animearsenal.gameobject.blocks.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import truefantasy.animcolle.init.ItemInit;
import yor42.animearsenal.gameobject.blocks.tileentity.tileentityDimensionalBridge;
import yor42.animearsenal.gameobject.recipes.bridgetargets;

public class containerDimensionBridge extends Container {

    private final tileentityDimensionalBridge tileEntity;
    private int progress;
    private int energy;
    private int isobstructed;

    public containerDimensionBridge(InventoryPlayer playerinv, tileentityDimensionalBridge TE) {

        IItemHandler handler = TE.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        this.tileEntity = TE;

        this.addSlotToContainer(new SlotItemHandler(handler, 0, 32, 22));
        this.addSlotToContainer(new SlotItemHandler(handler, 1, 9, 45));


        //playerInv
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerinv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlotToContainer(new Slot(playerinv, k, 8 + k * 18, 142));
        }

    }


    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener icontainerlistener = this.listeners.get(i);

            if (this.progress != this.tileEntity.getField(0))
            {
                icontainerlistener.sendWindowProperty(this, 0, this.tileEntity.getField(0));
            }

            if (this.energy != this.tileEntity.getField(1))
            {
                icontainerlistener.sendWindowProperty(this, 1, this.tileEntity.getField(1));
            }

            if (this.isobstructed != this.tileEntity.getField(2))
            {
                icontainerlistener.sendWindowProperty(this, 2, this.tileEntity.getField(2));
            }
        }

        this.progress = this.tileEntity.getField(0);
        this.energy = this.tileEntity.getField(1);
        this.isobstructed = this.tileEntity.getField(2);
    }

    public void updateProgressBar(int id, int data)
    {
        this.tileEntity.setField(id,data);
    }

    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return this.tileEntity.isUsableByPlayer(playerIn);
    }


    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 2)
            {
                if (!this.mergeItemStack(itemstack1, 3, 39, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (index != 1 && index != 0)
            {
                if (!(bridgetargets.instance().getRecipeResult(itemstack1) == null))
                {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (itemstack1.getItem() == ItemInit.MISC_COMPRESSEDMATTER)
                {
                    if (!this.mergeItemStack(itemstack1, 1, 1, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index >= 3 && index < 30)
                {
                    if (!this.mergeItemStack(itemstack1, 30, 37, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index >= 30 && index < 37 && !this.mergeItemStack(itemstack1, 3, 30, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 3, 37, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }
}
