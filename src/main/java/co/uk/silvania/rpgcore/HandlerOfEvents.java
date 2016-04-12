package co.uk.silvania.rpgcore;

import co.uk.silvania.rpgcore.network.EquippedSkillsPacket;
import co.uk.silvania.rpgcore.network.LevelPacket;
import co.uk.silvania.rpgcore.skills.EquippedSkills;
import co.uk.silvania.rpgcore.skills.GlobalLevel;
import co.uk.silvania.rpgcore.skills.SkillLevelBase;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HandlerOfEvents {
	
	GuiConfig config = new GuiConfig();
	
	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone event) {
		((SkillLevelBase) SkillLevelBase.get(event.entityPlayer, SkillLevelBase.staticId)).copy((SkillLevelBase) SkillLevelBase.get(event.original, SkillLevelBase.staticId));
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent.Pre event) {
		Minecraft mc = Minecraft.getMinecraft();
		
		if (mc.thePlayer != null) {
			int totalEquipped = 0;
	
			GlobalLevel glevel = (GlobalLevel) GlobalLevel.get((EntityPlayer) mc.thePlayer);
			if (glevel != null) {
				mc.fontRendererObj.drawString("Global Level: " + (int) glevel.getLevel() + ", XP: " + (int) glevel.getXPGlobal(), 2, 2, 16777215);
			}		
			for (int i = 0; i < RegisterSkill.skillList.size(); i++) {
				SkillLevelBase skillBase = RegisterSkill.skillList.get(i);
				SkillLevelBase skill = (SkillLevelBase) skillBase.get((EntityPlayer) mc.thePlayer, skillBase.skillId);
				EquippedSkills equippedSkills = (EquippedSkills) EquippedSkills.get((EntityPlayer) Minecraft.getMinecraft().thePlayer);
				
				if (skill != null) {
					if (equippedSkills.isSkillEquipped(skill.skillId)) {
						int slot = equippedSkills.findSkillSlot(skill.skillId);
						
						if (config.getShowXp(slot)) {
							mc.fontRendererObj.drawString("Name: " + skill.skillName() + ", XP: " + (int) skill.getXP(), 2, ((totalEquipped)*10)+12, 16777215);
							totalEquipped++;
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerLoginClient(PlayerLoggedInEvent event) {
		if (event.player != null) {
			if (!event.player.worldObj.isRemote) {
				for (int i = 0; i < RegisterSkill.skillList.size(); i++) {
					SkillLevelBase skillBase = RegisterSkill.skillList.get(i);
					System.out.println("skillID being sent to client: " + skillBase.skillId);
					SkillLevelBase skill = (SkillLevelBase) skillBase.get(event.player, skillBase.skillId);
					if (skill != null) {
						System.out.println("Sending data to client!");
						RPGCore.network.sendTo(new LevelPacket(skill.getXP(), skill.skillId), (EntityPlayerMP) event.player);
					}
				}
				EquippedSkills equippedSkills = (EquippedSkills) EquippedSkills.get(event.player);
				
				System.out.println("Sending global level to client!");
				GlobalLevel glevel = (GlobalLevel) GlobalLevel.get((EntityPlayer) event.player);
				RPGCore.network.sendTo(new LevelPacket((int)(glevel.getXPGlobal()*10), glevel.skillId), (EntityPlayerMP) event.player);
				
				System.out.println("Sending equipped skills to client!");
				System.out.println("Slot 0: " + equippedSkills.getSkillInSlot(0));
				RPGCore.network.sendTo(new EquippedSkillsPacket(
						equippedSkills.getSkillInSlot(0), 
						equippedSkills.getSkillInSlot(1), 
						equippedSkills.getSkillInSlot(2), 
						equippedSkills.getSkillInSlot(3), 
						equippedSkills.getSkillInSlot(4), 
						equippedSkills.getSkillInSlot(5), 
						equippedSkills.getSkillInSlot(6),
						equippedSkills.getSkillInSlot(7),
						equippedSkills.getSkillInSlot(8),
						equippedSkills.getSkillInSlot(9),
						equippedSkills.getSkillInSlot(10),
						equippedSkills.getSkillInSlot(11)), (EntityPlayerMP) event.player);
			}
		} else {
			System.out.println("Player is null! This is bad! [HoE]");
			System.out.println("Player is null! This is bad! [HoE]");
			System.out.println("Player is null! This is bad! [HoE]");
			System.out.println("Player is null! This is bad! [HoE]");
			System.out.println("Player is null! This is bad! [HoE]");
			System.out.println("Player is null! This is bad! [HoE]");
			System.out.println("Player is null! This is bad! [HoE]");
			System.out.println("Player is null! This is bad! [HoE]");
			System.out.println("Player is null! This is bad! [HoE]");
			System.out.println("Player is null! This is bad! [HoE]");
		}
	}

}
