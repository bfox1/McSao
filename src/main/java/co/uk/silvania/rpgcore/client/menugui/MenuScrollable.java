package co.uk.silvania.rpgcore.client.menugui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import co.uk.silvania.rpgcore.RPGCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.GuiScrollingList;

public class MenuScrollable extends GuiScrollingList{
	
	public static final ResourceLocation menuIcons  = new ResourceLocation(RPGCore.MODID, "textures/gui/menuicons.png");
	
	int width;
	int height;
	int xSize;
	int ySize;
	
	MenuGui parent;
	
	int selectedIndex = -1;
	Minecraft mc = Minecraft.getMinecraft();

	public MenuScrollable(MenuGui parent, int width, int height, int xSize, int ySize) {
		//				  w   h       h   ww  wh
		super(parent.mc, 90, 256, ((height - ySize) / 2) + 44, ((height - ySize) / 2) + 212, 80, 80, width, height);
		//Width, height-trim top?, offset-top?, ?, widget width, widget height, screen width, screen height
		
		this.width = width;
		this.height = height;
		
		this.xSize = xSize;
		this.ySize = ySize;
		
		this.parent = parent;
	}

	
	@Override
	protected int getSize() {
		return 5;
	}

	@Override
	protected void elementClicked(int index, boolean doubleClick) {
		parent.selectModIndex(index);
	}

	@Override
	protected boolean isSelected(int index) {
		return this.parent.modIndexSelected(index);
	}

	@Override protected void drawBackground() {}

	Gui gui = new Gui();
	
	@Override
	protected void drawSlot(int listIndex, int width, int height, int var4, Tessellator var5) {
		GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_FOG);
        GL11.glColor4f(1,1,1,1);
        
        int backOffset = 0;
        
        if (!isSelected(listIndex)) {
        	backOffset = 70;
        }

        mc.getTextureManager().bindTexture(menuIcons);
        gui.drawTexturedModalRect(((this.width - xSize) / 2) + 5, height+5, 0+backOffset, 0, 70, 70);
		
        if (listIndex == 1) {
        	if (isSelected(listIndex)) {
        		gui.drawTexturedModalRect(((this.width - xSize) / 2) + 26, height+28, 28, 74, 28, 24);
        	} else {
        		gui.drawTexturedModalRect(((this.width - xSize) / 2) + 26, height+28, 0,  74, 28, 24);
        	}
        } else if (listIndex == 2) {
        	if (isSelected(listIndex)) {
        		gui.drawTexturedModalRect(((this.width - xSize) / 2) + 20, height+27, 40, 101, 40, 26);
        	} else {
        		gui.drawTexturedModalRect(((this.width - xSize) / 2) + 20, height+27, 0,  101, 40, 26);
        	}
        } else if (listIndex == 3) {
        	if (isSelected(listIndex)) {
        		gui.drawTexturedModalRect(((this.width - xSize) / 2) + 24, height+26, 32, 128, 32, 28);
        	} else {
        		gui.drawTexturedModalRect(((this.width - xSize) / 2) + 24, height+26, 0,  128, 32, 28);
        	}
        } else if (listIndex == 4) {
        	if (isSelected(listIndex)) {
        		gui.drawTexturedModalRect(((this.width - xSize) / 2) + 23, height+24, 34, 157, 34, 32);
        	} else {
        		gui.drawTexturedModalRect(((this.width - xSize) / 2) + 23, height+24, 0,  157, 34, 32);
        	}
        } else if (listIndex == 5) {
        	if (isSelected(listIndex)) {
        		gui.drawTexturedModalRect(((this.width - xSize) / 2) + 28, height+28, 58, 73, 24, 24);
        	} else {
        		gui.drawTexturedModalRect(((this.width - xSize) / 2) + 28, height+28, 83, 73, 24, 24);
        	}
        }
	}
}
