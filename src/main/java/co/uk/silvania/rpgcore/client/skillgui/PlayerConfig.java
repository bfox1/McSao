package co.uk.silvania.rpgcore.client.skillgui;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import co.uk.silvania.rpgcore.RPGCore;
import co.uk.silvania.rpgcore.RPGUtils;
import co.uk.silvania.rpgcore.SkillsContainer;
import co.uk.silvania.rpgcore.network.OpenGuiPacket;
import co.uk.silvania.rpgcore.network.SkillPointPacket;
import co.uk.silvania.rpgcore.skills.EquippedSkills;
import co.uk.silvania.rpgcore.skills.GlobalLevel;
import co.uk.silvania.rpgcore.skills.SkillLevelBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class PlayerConfig extends GuiScreen {

	public GuiButton addSTR;
	public GuiButton removeSTR;
	public GuiButton addAGI;
	public GuiButton removeAGI;
	public GuiButton saveChanges;
	public GuiButton cancelChanges;
	
	int availablePoints;
	
	int addStrVal;
	int addAgiVal;

	public static final ResourceLocation skillConfig = new ResourceLocation(RPGCore.MODID, "textures/gui/blankgui.png");
	
	int xSize = 256;
	int ySize = 256;
	
	@Override
	public void initGui() {
		super.initGui();
		
		GlobalLevel glevel = (GlobalLevel) GlobalLevel.get((EntityPlayer) mc.thePlayer);
		availablePoints = glevel.skillPoints;
		//EquippedSkills equippedSkills = (EquippedSkills) EquippedSkills.get((EntityPlayer) Minecraft.getMinecraft().thePlayer);
		//SkillLevelBase skill = SkillLevelBase.getSkillByID(equippedSkills.getSkillInSlot(slot), mc.thePlayer);
		
		int left = (this.width - this.xSize) / 2;
		int top  = (this.height - this.ySize) / 2;
		
		addSTR = new GuiButton(1, left+93, top+21, 20, 20, "+");
		removeSTR = new GuiButton(2, left+19, top+21, 20, 20, "-");
		addAGI = new GuiButton(3, left+216, top+21, 20, 20, "+");
		removeAGI = new GuiButton(4, left+144, top+21, 20, 20, "-");
		saveChanges = new GuiButton(5, left+7, top+47, 119, 20, "Save Changes");
		cancelChanges = new GuiButton(6, left+130, top+47, 119, 20, "Cancel Changes");

		buttonList.add(addSTR);
		buttonList.add(removeSTR);
		buttonList.add(addAGI);
		buttonList.add(removeAGI);
		buttonList.add(saveChanges);
		buttonList.add(cancelChanges);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseZ, float par3) {
		this.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_BLEND);
		this.mc.getTextureManager().bindTexture(skillConfig); //Main skill pane
		int left = (this.width - this.xSize) / 2;
		int top  = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(left, top, 0, 0, this.xSize, this.ySize);
		
		if (addStrVal <= 0) { removeSTR.enabled = false; } else { removeSTR.enabled = true; }
		if (addAgiVal <= 0) { removeAGI.enabled = false; } else { removeAGI.enabled = true; }
		if (availablePoints <= 0) {
			addSTR.enabled = false;
			addAGI.enabled = false;
		} else {
			addSTR.enabled = true;
			addAGI.enabled = true;
		}

		String pnts = "Skill Points: " + availablePoints;
		
		mc.fontRendererObj.drawString(pnts, left + 249 - mc.fontRendererObj.getStringWidth(pnts), top + 7, 4210752);
		mc.fontRendererObj.drawString("Player Configuration", left + 7, top + 7, 4210752);
		
		String str = "STR: " + SkillLevelBase.getSkillByID("skillStrength", mc.thePlayer).getLevel();
		String agi = "AGI: " + SkillLevelBase.getSkillByID("skillAgility", mc.thePlayer).getLevel();
		
		String strAdd = addStrVal > 0 ? "+" + addStrVal : "";
		String agiAdd = addAgiVal > 0 ? "+" + addAgiVal : "";
		
		mc.fontRendererObj.drawString(str, left + 66-(mc.fontRendererObj.getStringWidth(str+strAdd)/2), top + 27, 4210752);
		mc.fontRendererObj.drawString(strAdd, left + 66+(mc.fontRendererObj.getStringWidth(str)/2), top + 27, 25600);
		
		mc.fontRendererObj.drawString(agi, left + 189-(mc.fontRendererObj.getStringWidth(agi+agiAdd)/2), top + 27, 4210752);
		mc.fontRendererObj.drawString(agiAdd, left + 189+(mc.fontRendererObj.getStringWidth(agi)/2), top + 27, 25600);
		
		super.drawScreen(mouseX, mouseZ, par3);
	}
	
    @Override
    public void actionPerformed(GuiButton button) {
    	switch(button.id) {
    	case 1:
    		addStrVal += 1;
    		availablePoints -= 1;
    		break;
    	case 2:
    		addStrVal -= 1;
    		availablePoints += 1;
    		break;
    	case 3:
    		addAgiVal += 1;
    		availablePoints -= 1;
    		break;
    	case 4:
    		addAgiVal -= 1;
    		availablePoints += 1;
    		break;
    	case 5:
    		System.out.println("Syncing!");
    		RPGCore.network.sendToServer(new SkillPointPacket(addStrVal, addAgiVal));
    		Minecraft.getMinecraft().thePlayer.closeScreen();
    		RPGCore.network.sendToServer(new OpenGuiPacket(0));
    		break;
    	case 6:
    		Minecraft.getMinecraft().thePlayer.closeScreen();
    		RPGCore.network.sendToServer(new OpenGuiPacket(0));
    		break;
    	}
    }
    
    public void configureSkill(int slot) {
    	EntityPlayer player = Minecraft.getMinecraft().thePlayer;
    	if (slot == 999) {
    		player.openGui(RPGCore.instance, 4, Minecraft.getMinecraft().theWorld, (int) player.posX, (int) player.posY, (int) player.posZ);
    	} else {
	    	SkillSelectGui.slotClicked = slot;
	    	player.openGui(RPGCore.instance, 1, Minecraft.getMinecraft().theWorld, (int) player.posX, (int) player.posY, (int) player.posZ);
    	}
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
