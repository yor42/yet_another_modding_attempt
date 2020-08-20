package yor42.animearsenal.gameobject.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import yor42.animearsenal.gameobject.blocks.container.containeralloysmelter;
import yor42.animearsenal.gameobject.blocks.tileentity.tileentitybasicalloysmelter;
import yor42.animearsenal.main;

public class guiBasicAlloySmelter extends GuiContainer {
    private static final ResourceLocation TEXTURE = new ResourceLocation(main.MOD_ID+":"+"textures/gui/basic_alloy_smelter.png");
    private final InventoryPlayer player;
    private final tileentitybasicalloysmelter tileentity;

    public guiBasicAlloySmelter(InventoryPlayer player, tileentitybasicalloysmelter tileentity) {
        super(new containeralloysmelter(player, tileentity));
        this.player = player;
        this.tileentity = tileentity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f,1.0f,1.0f);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop,0,0,this.xSize,this.ySize);

        if(tileentitybasicalloysmelter.isBurning(tileentity)){
            int k = this.getFuelScaled(13);
            this.drawTexturedModalRect(this.guiLeft+8, this.guiTop+54+12-k, 176, 12-k, 14,k);
        }

        int i = this.getCookProgressScaled(24);
        this.drawTexturedModalRect(this.guiLeft+44, this.guiTop+36,176,14,i+1,16);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        String tileName = this.tileentity.getDisplayName().getUnformattedText();
        this.fontRenderer.drawString(tileName, (this.xSize/2-this.fontRenderer.getStringWidth(tileName)/2)+3, 8, 4210752);
        this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(), 122, this.ySize - 96 + 2, 4210752);
    }

    private int getFuelScaled(int pixels){
        int i = this.tileentity.getField(1);
        if(i == 0) i = 200;
        return this.tileentity.getField(0) * pixels / i;
    }

    private int getCookProgressScaled(int pixels)
    {
        int i = this.tileentity.getField(2);
        int j = this.tileentity.getField(3);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }
}
