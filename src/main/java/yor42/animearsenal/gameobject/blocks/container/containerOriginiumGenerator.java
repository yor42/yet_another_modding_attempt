package yor42.animearsenal.gameobject.blocks.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import yor42.animearsenal.gameobject.blocks.tileentity.tileentityOriginiumGenerator;

public class containerOriginiumGenerator extends Container {

    private final tileentityOriginiumGenerator TE;
    private int fueltime, energy, FEpertick, totalFuelTime;

    public containerOriginiumGenerator(InventoryPlayer playerinv, tileentityOriginiumGenerator tileentity) {
        IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        TE = tileentity;

        this.addSlotToContainer(new SlotItemHandler(handler, 0, 22, 34));


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

        for (IContainerListener icontainerlistener : this.listeners) {
            if (this.fueltime != this.TE.getField(0)) {
                icontainerlistener.sendWindowProperty(this, 0, this.TE.getField(0));
            }

            if (this.energy != this.TE.getField(1)) {
                icontainerlistener.sendWindowProperty(this, 1, this.TE.getField(1));
            }

            if (this.FEpertick != this.TE.getField(2)) {
                icontainerlistener.sendWindowProperty(this, 2, this.TE.getField(2));
            }
            if (this.totalFuelTime != this.TE.getField(3)) {
                icontainerlistener.sendWindowProperty(this, 3, this.TE.getField(3));
            }
        }

        this.fueltime = this.TE.getField(0);
        this.energy = this.TE.getField(1);
        this.FEpertick = this.TE.getField(2);
        this.totalFuelTime=this.TE.getField(3);

    }

    public void updateProgressBar(int id, int data)
    {
        this.TE.setField(id,data);
    }

    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return this.TE.isUsableByPlayer(playerIn);
    }

    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 0)
            {
                if (!this.mergeItemStack(itemstack1, 3, 38, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (TE.isItemFuel(itemstack1))
            {
                if (!this.mergeItemStack(itemstack1, 0, 1, false))
                {
                    return ItemStack.EMPTY;
                }
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
