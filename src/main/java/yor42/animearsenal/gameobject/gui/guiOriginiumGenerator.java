package yor42.animearsenal.gameobject.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import yor42.animearsenal.gameobject.blocks.container.containerOriginiumGenerator;
import yor42.animearsenal.gameobject.blocks.tileentity.tileentityDimensionalBridge;
import yor42.animearsenal.gameobject.blocks.tileentity.tileentityOriginiumGenerator;
import yor42.animearsenal.main;

import java.util.ArrayList;
import java.util.List;

public class guiOriginiumGenerator extends GuiContainer {

    private static final ResourceLocation TEXTURE = new ResourceLocation(main.MOD_ID+":"+"textures/gui/originiumgenerator_gui.png");
    private final InventoryPlayer playerinv;
    private final tileentityOriginiumGenerator tileentity;

    public guiOriginiumGenerator(InventoryPlayer playerinv, tileentityOriginiumGenerator tileentity) {
        super(new containerOriginiumGenerator(playerinv,tileentity));
        this.playerinv = playerinv;
        this.tileentity = tileentity;
    }

    private int getStoredPowerScaled(int pixels){
        int currentpower = this.tileentity.getField(1);
        int maxpower = this.tileentity.buffer.getMaxEnergyStored();

        return currentpower != 0 && maxpower != 0? currentpower*pixels/maxpower:0;
    }

    private int getFuelScaled(int pixels){
        int remainingFueltime = this.tileentity.getField(0);
        int maxFuelTime = this.tileentity.getField(3);

        return remainingFueltime != 0 && maxFuelTime != 0? remainingFueltime*pixels/maxFuelTime:0;
    }

    private int getcolor(){
        boolean outoffuel = this.tileentity.getField(2)==0;
        boolean inactive = this.tileentity.getField(3)==0;

        if (outoffuel&&inactive){
            return 16711680;
        }
        else return 65280;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        int currentpower = this.tileentity.getField(1);
        int maxpower = this.tileentity.buffer.getMaxEnergyStored();
        boolean pausegeneration = currentpower >= this.tileentity.buffer.getMaxEnergyStored();
        boolean outoffuel = this.tileentity.getField(2)==0;
        int FEpertick = this.tileentity.getField(2);

        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);

        if(isPointInRegion(157, 6,10,72,mouseX,mouseY)){
            this.drawHoveringText(currentpower+"/"+maxpower+" FE", mouseX,mouseY);
        }

        if(isPointInRegion(55, 6,99,71,mouseX,mouseY)){
            List<String> strings = new ArrayList<String>();

            if(pausegeneration||outoffuel) {
                if (pausegeneration) {
                    strings.add(new TextComponentTranslation("gui.generator.tooltip.paused").getFormattedText());
                }
                if (outoffuel) {
                    strings.add(new TextComponentTranslation("gui.generator.tooltip.nofuel").getFormattedText());
                }
            }
            else {
                strings.add(new TextComponentTranslation("gui.imgtmachine.active").getFormattedText());
                strings.add(new TextComponentTranslation("gui.imgtmachine.generating").getFormattedText()+" "+FEpertick+" FE/t");
            }
            this.drawHoveringText(strings, mouseX,mouseY);
        }

    }

    private String getstatuskey(){
        boolean outoffuel = this.tileentity.getField(2)==0;
        boolean inactive = this.tileentity.getField(3)==0;
        boolean pausegeneration = this.tileentity.getField(1) >= this.tileentity.buffer.getMaxEnergyStored();

        if (outoffuel&&inactive){
            return "gui.imgtmachine.nofuel";
        }
        else if(pausegeneration){
            return "gui.imgtmachine.generationpaused";
        }
        else return "gui.imgtmachine.ok";

    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = this.tileentity.getDisplayName().getUnformattedText();
        this.fontRenderer.drawString(s, 57, 8, getcolor());
        this.fontRenderer.drawString(new TextComponentTranslation(getstatuskey()).getFormattedText(), 57, 18, getcolor());
        this.fontRenderer.drawString(this.playerinv.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0,0,this.xSize,this.ySize);
        //power
        int p = this.getStoredPowerScaled(71);
        this.drawTexturedModalRect(this.guiLeft+157, this.guiTop+6, 176, 31, 10, 72-p);

        int f = this.getFuelScaled(32);
        this.drawTexturedModalRect(this.guiLeft+58, this.guiTop+44, 186, 31, 32, 32-f);
    }
}
