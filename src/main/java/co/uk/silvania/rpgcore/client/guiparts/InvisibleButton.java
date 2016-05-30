package co.uk.silvania.rpgcore.client.guiparts;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class InvisibleButton extends GuiButton {
	
	public InvisibleButton(int id, int posX, int posZ, int sizeX, int sizeZ, String text) {
		super(id, posX, posZ, sizeX, sizeZ, text);
	}
	
	
	//The button is invisible. Do not draw it!
	@Override public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (this.visible) {
			this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int i = this.getHoverState(this.hovered);
		}
	}

}
