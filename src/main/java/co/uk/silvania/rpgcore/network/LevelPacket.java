package co.uk.silvania.rpgcore.network;

import co.uk.silvania.rpgcore.skills.GlobalLevel;
import co.uk.silvania.rpgcore.skills.SkillLevelBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class LevelPacket implements IMessage {
	
	public float xp;
	public String skillId;
	
	public LevelPacket() {}
	
	public LevelPacket(float xp, String skillId) { 
		this.xp = xp;
		this.skillId = skillId;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		xp = buf.readFloat();
		skillId = ByteBufUtils.readUTF8String(buf);
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(xp);
		ByteBufUtils.writeUTF8String(buf, skillId);
	}
	
	public static class Handler implements IMessageHandler<LevelPacket, IMessage> {
		
		@Override
		public IMessage onMessage(final LevelPacket message, final MessageContext ctx) {
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
						} else {
							SkillLevelBase level = (SkillLevelBase) SkillLevelBase.get((EntityPlayer) player, message.skillId);
							System.out.println("PACKET RECEIVED! SKILLID: " + message.skillId + ", XP: " + message.xp);
							level.setXP(message.xp);
						}
					}
				});
			}
			return null;
		}
	}

}
