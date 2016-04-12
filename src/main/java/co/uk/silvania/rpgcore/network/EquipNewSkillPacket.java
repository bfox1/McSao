package co.uk.silvania.rpgcore.network;

import co.uk.silvania.rpgcore.RPGCore;
import co.uk.silvania.rpgcore.skills.EquippedSkills;
import co.uk.silvania.rpgcore.skills.SkillLevelBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class EquipNewSkillPacket implements IMessage {
	
	int slotId;
	String skillId;
	
	public EquipNewSkillPacket() {}
	
	public EquipNewSkillPacket(int slot, String skill) {
		slotId = slot;
		skillId = skill;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		slotId = ByteBufUtils.readVarShort(buf);
		skillId = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeVarShort(buf, slotId);
		ByteBufUtils.writeUTF8String(buf, skillId);
	}

	public static class Handler implements IMessageHandler<EquipNewSkillPacket, IMessage> {

		@Override
		public IMessage onMessage(final EquipNewSkillPacket message, final MessageContext ctx) {
			IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
			EquippedSkills equippedSkills = (EquippedSkills) EquippedSkills.get(ctx.getServerHandler().playerEntity);
			
			mainThread.addScheduledTask(new Runnable() {
				@Override
				public void run() {
					EquippedSkills equippedSkills = (EquippedSkills) EquippedSkills.get(ctx.getServerHandler().playerEntity);
					for (int i = 0; i < equippedSkills.skillSlots; i++) {
						SkillLevelBase skill = SkillLevelBase.getSkillByID(equippedSkills.getSkillInSlot(i), ctx.getServerHandler().playerEntity);
						SkillLevelBase newSkill = SkillLevelBase.getSkillByID(message.skillId, ctx.getServerHandler().playerEntity);
						
						if (skill != null) {
							if (skill.skillId.equals(message.skillId)) {
								System.out.println("Duplicate skill detected. Removing...");
								equippedSkills.setSkill(i, "");
							}
							if (newSkill != null) {
								for (int j = 0; j < newSkill.incompatibleSkills.size(); j++) {
									System.out.println("Iterating. " + j + ": " + newSkill.incompatibleSkills.get(j));
									if (newSkill.incompatibleSkills.get(j).equals(skill.skillId)) {
										System.out.println("Incompatable skill " + newSkill.incompatibleSkills.get(j) + " detected. Incompatible with " + skill.skillId + ". Removing...?");
										System.out.println("Slot: " + equippedSkills.findSkillSlot(newSkill.incompatibleSkills.get(j)));
										equippedSkills.setSkill(equippedSkills.findSkillSlot(newSkill.incompatibleSkills.get(j)), "");
									}
								}
							}
						}
					}
					
					equippedSkills.setSkill(message.slotId, message.skillId);
					
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
							equippedSkills.getSkillInSlot(11)), ctx.getServerHandler().playerEntity);
				}
			});
			return null;
		}
	}
}
