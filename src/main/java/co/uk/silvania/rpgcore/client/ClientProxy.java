package co.uk.silvania.rpgcore.client;

import co.uk.silvania.rpgcore.CommonProxy;
import co.uk.silvania.rpgcore.network.EquippedSkillsPacket;
import co.uk.silvania.rpgcore.network.LevelPacket;
import co.uk.silvania.rpgcore.skills.EquippedSkills;
import co.uk.silvania.rpgcore.skills.GlobalLevel;
import co.uk.silvania.rpgcore.skills.SkillLevelBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerKeyBinds() {
		FMLCommonHandler.instance().bus().register(new KeyInputHandler());
		KeyBindings.init();
	}
	
	@Override
	public void syncLevelsWithClient(final LevelPacket message, MessageContext ctx) {
		if (ctx.side.isClient()) {
			final Minecraft mc = Minecraft.getMinecraft();
			IThreadListener mainThread = mc;
			
			mainThread.addScheduledTask(new Runnable() {
				@Override
				public void run() {
					EntityPlayerSP player = mc.thePlayer;
					
					if (message.skillId.equalsIgnoreCase(GlobalLevel.staticSkillId)) {
						System.out.println("####### GLOBAL LEVEL RECEIVED. XP: " + message.xp);
						GlobalLevel glevel = (GlobalLevel) GlobalLevel.get((EntityPlayer) player);
						glevel.setXP((message.xp)/10.0F);
						glevel.setSkillPoints(message.val);
					} else {
						SkillLevelBase level = (SkillLevelBase) SkillLevelBase.get((EntityPlayer) player, message.skillId);
						System.out.println("PACKET RECEIVED! SKILLID: " + message.skillId + ", XP: " + message.xp);
						level.setXP(message.xp);
					}
				}
			});
		}
	}
	
	@Override
	public void syncEquippedSkillsWithClient(final EquippedSkillsPacket message, MessageContext ctx) {
		if (ctx.side.isClient()) {
			final Minecraft mc = Minecraft.getMinecraft();
			IThreadListener mainThread = mc;
			
			mainThread.addScheduledTask(new Runnable() {
				@Override
				public void run() {
					if (mc.thePlayer != null) {
						System.out.println("Equipped Skills packet received.");
						EquippedSkills skills = (EquippedSkills) EquippedSkills.get((EntityPlayer) mc.thePlayer);
						System.out.println("[ESP] Skills loaded.");
						
						skills.setSkill(0, message.slot0);
						skills.setSkill(1, message.slot1);
						skills.setSkill(2, message.slot2);
						skills.setSkill(3, message.slot3);
						skills.setSkill(4, message.slot4);
						skills.setSkill(5, message.slot5);
						skills.setSkill(6, message.slot6);
						skills.setSkill(7, message.slot7);
						skills.setSkill(8, message.slot8);
						skills.setSkill(9, message.slot9);
						skills.setSkill(10, message.slot10);
						skills.setSkill(11, message.slot11);
					} else {
						System.out.println("Player is null! This is bad! [ESP]");
						System.out.println("Player is null! This is bad! [ESP]");
						System.out.println("Player is null! This is bad! [ESP]");
						System.out.println("Player is null! This is bad! [ESP]");
						System.out.println("Player is null! This is bad! [ESP]");
						System.out.println("Player is null! This is bad! [ESP]");
						System.out.println("Player is null! This is bad! [ESP]");
						System.out.println("Player is null! This is bad! [ESP]");
						System.out.println("Player is null! This is bad! [ESP]");
						System.out.println("Player is null! This is bad! [ESP]");
					}
				}
			});
		}
	}
}
