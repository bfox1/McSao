package co.uk.silvania.rpgcore.client.skillgui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import co.uk.silvania.rpgcore.RPGCore;
import co.uk.silvania.rpgcore.RegisterSkill;
import co.uk.silvania.rpgcore.network.EquipNewSkillPacket;
import co.uk.silvania.rpgcore.network.OpenGuiPacket;
import co.uk.silvania.rpgcore.skills.EquippedSkills;
import co.uk.silvania.rpgcore.skills.SkillLevelBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class SkillListGui extends GuiScreen {
	
	public static final ResourceLocation skillListTexture  = new ResourceLocation(RPGCore.MODID, "textures/gui/skilllist.png");
	ArrayList<SkillLevelBase> skillList = RegisterSkill.skillList;
	
	int xSize = 256;
	int ySize = 256;
	
	GuiScrollingList_Mod list;
	
	public MultiLineButton buttonCancel;
	public MultiLineButton buttonConfirm;
	public MultiLineButton buttonDetails;
	public MultiLineButton buttonClear;
	public MultiLineButton buttonConfig;
	
	@Override
	public void drawScreen(int mouseX, int mouseZ, float partialTick) {	
		list.drawScreen(mouseX, mouseZ, partialTick);
		
		EquippedSkills equippedSkills = (EquippedSkills) EquippedSkills.get((EntityPlayer) mc.thePlayer);
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_BLEND);
		this.mc.getTextureManager().bindTexture(skillListTexture); //Main skill pane
		int left = (this.width - this.xSize) / 2;
		int top  = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(left, top, 0, 0, this.xSize, this.ySize);
		
		mc.fontRendererObj.drawString("Selecting skill for slot " + SkillSelectGui.slotClicked, left+9, top+15, 4210752);
		if (equippedSkills.getSkillInSlot(SkillSelectGui.slotClicked).length() > 3) {
			mc.fontRendererObj.drawString("Current Skill: \u00A7l" + RegisterSkill.getDisplayNameForSkill(equippedSkills.getSkillInSlot(SkillSelectGui.slotClicked)), left+9, top+25, 4210752);
		}
		
		super.drawScreen(mouseX, mouseZ, partialTick);
		
		//TODO: This currently continues tooltip outside of GUI.
		//Check if mouse is within the list's frame before rendering tooltip.
		
		SkillLevelBase skillBase = skillList.get(list.hoverElement);
		SkillLevelBase skill = (SkillLevelBase) skillBase.get((EntityPlayer) mc.thePlayer, skillBase.skillId);
		if (mouseX >= left+10 && mouseX <= left+51 && mouseZ >= top+48 && mouseZ <= top+212) {
			if (skill != null && skill.description != null) {
				drawHoveringText(skill.description, mouseX, mouseZ, fontRendererObj);
			}
		}
		if (mouseX >= left+169 && mouseX <= left+240 && mouseZ >= top+48 && mouseZ <= top+212) {
			if (skill != null) {
				if ((!skill.incompatibleSkills.isEmpty() && !skill.isSkillCompatable(mc.thePlayer)) || skill.hasUnequippedRequirements(mc.thePlayer)) {
					List str = new ArrayList();
					str.add("\u00A7nCurrent Skill Issues");
					str.add(" ");
					
					if (!skill.incompatibleSkills.isEmpty()) {
						for (int i = 0; i < skill.incompatibleSkills.size(); i++) {
							if (i == 0) { str.add("\u00A7lIncompatabilities:"); }
							if (equippedSkills.isSkillEquipped(skill.incompatibleSkills.get(i))) {
								str.add("\u00A7c" + RegisterSkill.getDisplayNameForSkill(skill.incompatibleSkills.get(i)));
							}
						}
						str.add(" ");
						str.add("Any incompatible skills will be automatically");
						str.add("removed upon equipping this skill.");
					}
					
					if (skill.hasUnequippedRequirements(mc.thePlayer)) {
						for (int i = 0; i < skill.requiredSkills.size(); i++) {
							if (i == 0) { str.add("\u00A7lRequired Skills:"); }
							if (!equippedSkills.isSkillEquipped(skill.requiredSkills.get(i))) {
								str.add("\u00A7c" + RegisterSkill.getDisplayNameForSkill(skill.requiredSkills.get(i)));
							} else {
								str.add("\u00A7a" + RegisterSkill.getDisplayNameForSkill(skill.requiredSkills.get(i)));
							}
						}
					}

					drawHoveringText(str, mouseX, mouseZ, fontRendererObj);
				}
			}
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@Override
	public void initGui() {
		System.out.println("initGui");		
		int left = (this.width - this.xSize) / 2;
		int top  = (this.height - this.ySize) / 2;
		
		EquippedSkills equippedSkills = (EquippedSkills) EquippedSkills.get((EntityPlayer) mc.thePlayer);

		buttonCancel = new MultiLineButton(1, left+173, top+221, 60, 24, "Cancel");
		buttonConfirm = new MultiLineButton(2, left+23, top+221, 60, 24, "Confirm#Selection");
		buttonDetails = new MultiLineButton(3, left+98, top+221, 60, 24, "Skill#Details");
		buttonClear = new MultiLineButton(4, left+209, top+12, 40, 24, "Clear#Slot");
		buttonConfig = new MultiLineButton(5, left+145, top+12, 60, 24, "Configure#Slot");
		
		buttonList.add(buttonCancel);
		buttonList.add(buttonConfirm);
		buttonList.add(buttonDetails);
		buttonList.add(buttonClear);
		buttonList.add(buttonConfig);
		
		buttonDetails.enabled = false;
		buttonConfig.enabled = equippedSkills.getSkillInSlot(SkillSelectGui.slotClicked).length() > 3;
		buttonClear.enabled = equippedSkills.getSkillInSlot(SkillSelectGui.slotClicked).length() > 3;
		
		this.list = new SkillListScrollable(this, this.width, this.height, 256, 256);
		this.list.registerScrollButtons(this.buttonList, 7, 8);
		//buttons n stuff
	}
	
	private int selected = -1;
	private SkillLevelBase selectedSkill;
	ArrayList<SkillLevelBase> skillLevelList = RegisterSkill.skillList;
	
	public void selectModIndex(int index) {
        this.selected = index;
        if (index >= 0 && index <= RegisterSkill.skillList.size()) {
            this.selectedSkill = RegisterSkill.skillList.get(selected);
        } else {
            this.selectedSkill = null;
        }
        
        buttonDetails.enabled = selectedSkill.hasGui();
        
        System.out.println("Selected skill: " + selectedSkill.skillName());
        //cachedLogo = null;
    }

    public boolean modIndexSelected(int index) {
        return index == selected;
    }
    
    @Override
    public void actionPerformed(GuiButton button) {
    	switch(button.id) {
    	case 1:
    		RPGCore.network.sendToServer(new OpenGuiPacket(0));
    		break;
    	case 2:
    		if (selected >= 0) {
	    		RPGCore.network.sendToServer(new EquipNewSkillPacket(SkillSelectGui.slotClicked, skillLevelList.get(selected).skillId));
				RPGCore.network.sendToServer(new OpenGuiPacket(0));
    		}
    		break;
    	case 3:
    		this.selectedSkill.openGui();
    		break;
    	case 4:
    		RPGCore.network.sendToServer(new EquipNewSkillPacket(SkillSelectGui.slotClicked, ""));
			RPGCore.network.sendToServer(new OpenGuiPacket(0));
			break;
    	case 5:
    		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
    		player.openGui(RPGCore.instance, 1, Minecraft.getMinecraft().theWorld, (int) player.posX, (int) player.posY, (int) player.posZ);
    		break;
    	}
    }

}
