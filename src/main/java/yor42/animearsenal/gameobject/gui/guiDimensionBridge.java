package yor42.animearsenal.gameobject.gui;

import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import yor42.animearsenal.gameobject.blocks.container.containerDimensionBridge;
import yor42.animearsenal.gameobject.blocks.tileentity.tileentityDimensionalBridge;
import yor42.animearsenal.main;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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

        int currentpower = this.tileentity.getField(1);
        int maxpower = this.tileentity.buffer.getMaxEnergyStored();
        int processtime = this.tileentity.getField(0);

        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
        if(isPointInRegion(157, 6,10,72,mouseX,mouseY)){
            this.drawHoveringText(currentpower+"/"+maxpower+" FE", mouseX,mouseY);
        }
        if(isPointInRegion(104, 6,50,71,mouseX,mouseY)){
            List<String> strings = new ArrayList<String>();

            boolean obstructed = this.tileentity.getField(2)==1;
            boolean outofpower = this.tileentity.getField(1) < this.tileentity.getEnergyusage();
            if(obstructed||outofpower) {
                if (obstructed) {
                    strings.add(new TextComponentTranslation("gui.dimensionbridge.obstructed").getFormattedText());
                }
                if (outofpower) {
                    strings.add(new TextComponentTranslation("gui.imgtmachine.notenoughpower").getFormattedText());
                }
            }
            else {
                if(processtime >= 0){
                    strings.add(new TextComponentTranslation("gui.imgtmachine.active").getFormattedText());
                }
                else {
                    strings.add(new TextComponentTranslation("gui.imgtmachine.ready").getFormattedText());
                }
            }
            this.drawHoveringText(strings, mouseX,mouseY);
        }
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
        this.fontRenderer.drawString(new TextComponentTranslation(getstatuskey()).getFormattedText(), 106, 8, getcolor());
        this.fontRenderer.drawString(this.playerinv.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    private int getcolor(){
        boolean obstructed = this.tileentity.getField(2)==1;
        boolean outofpower = this.tileentity.getField(1) < this.tileentity.getEnergyusage();

        if (obstructed||outofpower){
            return 16711680;
        }
        else return 65280;
    }
    private String getstatuskey(){
        boolean obstructed = this.tileentity.getField(2)==1;
        boolean outofpower = this.tileentity.getField(1) < this.tileentity.getEnergyusage();

        if (obstructed||outofpower){
            return "gui.imgtmachine.error";
        }
        else return "gui.imgtmachine.ok";

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
        this.drawTexturedModalRect(this.guiLeft+157, this.guiTop+6, 176, 31, 10, 72-p);

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
