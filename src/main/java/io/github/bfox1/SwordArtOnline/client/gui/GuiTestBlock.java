package io.github.bfox1.SwordArtOnline.client.gui;

import io.github.bfox1.SwordArtOnline.common.util.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

/**
 * Created by bfox1 on 11/5/2016.
 */
public class GuiTestBlock extends GuiContainer
{
    public static final ResourceLocation texture = new ResourceLocation(Reference.MODID, "/textures/gui/decayer.png");
    public GuiTestBlock(Container inventorySlotsIn)
    {
        super(inventorySlotsIn);
       // this.decayer = entity;

        this.xSize = 176;
        this.ySize = 165;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {

    }
}
