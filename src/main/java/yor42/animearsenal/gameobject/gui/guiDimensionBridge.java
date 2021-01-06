package yor42.animearsenal.gameobject.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import yor42.animearsenal.gameobject.blocks.container.containerDimensionBridge;
import yor42.animearsenal.gameobject.blocks.tileentity.tileentityDimensionalBridge;
import yor42.animearsenal.main;

public class guiDimensionBridge extends GuiContainer {

    private static final ResourceLocation TEXTURE = new ResourceLocation(main.MOD_ID+":"+"textures/gui/dimensiongate_gui.png");
    private final InventoryPlayer playerinv;
    private final tileentityDimensionalBridge tileentity;

    public guiDimensionBridge(InventoryPlayer player, tileentityDimensionalBridge tileentity) {
        super(new containerDimensionBridge(player, tileentity));
        this.playerinv = player;
        this.tileentity = tileentity;
    }

    private int getprogressScaled(int pixels){
        int i = this.tileentity.getField(0);
        int j = this.tileentity.getTotalProcessTime();
        double k = (double) i/j;
        double result = i != 0 ?  k * pixels : 0;
        return (int)result;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    private int getStoredPowerScaled(int pixels){
        int currentpower = this.tileentity.getField(1);
        int maxpower = this.tileentity.buffer.getMaxEnergyStored();

        return currentpower != 0 && maxpower != 0? currentpower*pixels/maxpower:0;
    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = this.tileentity.getDisplayName().getUnformattedText();
        this.fontRenderer.drawString(s, 5, 6, 4210752);
        drawstatusstring();
        this.fontRenderer.drawString(this.playerinv.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    private void drawstatusstring() {
        int obstructed = this.tileentity.getField(2);
        boolean outofpower = this.tileentity.getField(1) < this.tileentity.getEnergyusage();
        if(obstructed==1 || outofpower){
            this.fontRenderer.drawString(new TextComponentTranslation("gui.dimensionbridge.error").getFormattedText(), 106, 8, 16711680);
        }
        else{
            this.fontRenderer.drawString(new TextComponentTranslation("gui.dimensionbridge.ok").getFormattedText(), 106, 8, 65280);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

        boolean obstructed = this.tileentity.getField(2) == 1;
        boolean outofpower = this.tileentity.getField(1) < this.tileentity.getEnergyusage();

        GlStateManager.color(1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0,0,this.xSize,this.ySize);

        int b = this.getprogressScaled(38);
        this.drawTexturedModalRect(this.guiLeft+28,this.guiTop+41,176,11,b+1,20);

        int p = this.getStoredPowerScaled(71);
        this.drawTexturedModalRect(this.guiLeft+157, this.guiTop+6, 176, 31, 10, 71-p);

        if (obstructed||outofpower){
            this.drawTexturedModalRect(this.guiLeft+119, this.guiTop+56, 186, 58, 20, 20);
        }
        else{
            this.drawTexturedModalRect(this.guiLeft+119, this.guiTop+56, 186, 99, 20, 20);
        }

        if(obstructed){
            this.drawTexturedModalRect(this.guiLeft+119, this.guiTop+30, 186, 31, 20, 26);
        }
        else if(!obstructed&&outofpower){
            this.drawTexturedModalRect(this.guiLeft+119, this.guiTop+30, 206, 31, 20, 26);
        }
        else{
            this.drawTexturedModalRect(this.guiLeft+119, this.guiTop+36, 186, 79, 20, 20);
        }
        if(outofpower){
            this.drawTexturedModalRect(this.guiLeft+140, this.guiTop+68, 226, 69, 11, 8);
        }

    }
}
