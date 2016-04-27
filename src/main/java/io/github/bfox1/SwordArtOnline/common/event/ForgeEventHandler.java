package io.github.bfox1.SwordArtOnline.common.event;

import co.uk.silvania.rpgcore.RPGCore;
import co.uk.silvania.rpgcore.RegisterSkill;
import co.uk.silvania.rpgcore.network.EquippedSkillsPacket;
import co.uk.silvania.rpgcore.network.LevelPacket;
import co.uk.silvania.rpgcore.skills.EquippedSkills;
import co.uk.silvania.rpgcore.skills.GlobalLevel;
import co.uk.silvania.rpgcore.skills.SkillLevelAgility;
import co.uk.silvania.rpgcore.skills.SkillLevelBase;
import co.uk.silvania.rpgcore.skills.SkillLevelHealth;
import co.uk.silvania.rpgcore.skills.SkillLevelStrength;
import io.github.bfox1.SwordArtOnline.common.entity.SaoExtendedProperty;
import io.github.bfox1.SwordArtOnline.common.proxy.ClientProxy;
import io.github.bfox1.SwordArtOnline.common.proxy.CommonProxy;
import io.github.bfox1.SwordArtOnline.common.util.Reference;
import io.github.bfox1.SwordArtOnline.common.world.SAOTeleporter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

/**
 * Created by bfox1 on 4/16/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class ForgeEventHandler
{


    public ForgeEventHandler()
    {

    }

    /**
     * Annotated SusbribeEvent onRenderHealthBar adds the Health bar Hud and removes the Default health hud.
     * @param event McForge event.
     */
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onRenderHealthBar(RenderGameOverlayEvent.Pre event)
    {
        if(event.type.equals(RenderGameOverlayEvent.ElementType.HEALTH) && event.isCancelable() /*&& mc.thePlayer.worldObj.provider.getDimensionId() == Reference.DIMENSIONID*/)
        {
            event.setCanceled(true);
            ClientProxy.saoHud.drawBase();
            Minecraft mc = Minecraft.getMinecraft();
            ClientProxy.saoHud.drawBar(mc.thePlayer.getHealth() / mc.thePlayer.getMaxHealth());
        }
    }
    /**
     * Annotated SusbribeEvent onPlayerLog adds the Health bar Hud and removes the Default health hud.
     * @param e McForge event.
     */
    @SubscribeEvent
	public void onPlayerLog(PlayerLoggedInEvent e) {
		if(e.player.worldObj.getWorldType() == CommonProxy.saoWorld) {
			if(e.player.dimension != Reference.saoDimensionId) {
				EntityPlayerMP playerMP = (EntityPlayerMP) e.player;
                int y = playerMP.worldObj.getChunkFromChunkCoords(0,0).getHeightValue(0,0);
                playerMP.setPositionAndUpdate(0, y, 0);
				
				playerMP.mcServer.getConfigurationManager().transferPlayerToDimension(playerMP, Reference.saoDimensionId, new SAOTeleporter(playerMP.mcServer.worldServerForDimension(Reference.saoDimensionId)));
				
				((EntityPlayerMP) e.player).worldObj.setSpawnPoint(new BlockPos(0, y, 0));
			}
		}
	}
    /**
     * Annotated SusbribeEvent onPlayerRespawn adds the Health bar Hud and removes the Default health hud.
     * @param e McForge event.
     */
    @SubscribeEvent
    public void onPlayerRespawn(PlayerRespawnEvent e)
    {

    	if(e.player.worldObj.getWorldType() == CommonProxy.saoWorld) {
    		if(e.player.dimension == Reference.saoDimensionId) {
    			EntityPlayerMP playerMP = (EntityPlayerMP) e.player;
				
				BlockPos p = playerMP.worldObj.getSpawnPoint();
				playerMP.setPosition(p.getX(), p.getY(), p.getZ());
    		}
    	}
    }

    @SubscribeEvent
    public void onPlayerBreak(PlayerInteractEvent event)
    {

    }

    @SubscribeEvent
    public void onEntityConstructing(EntityEvent.EntityConstructing event)
    {
        if(event.entity instanceof EntityPlayer)
        if(event.entity.getExtendedProperties(CommonProxy.saoIEEP.getIEEP_ID()) == null)
        {
            event.entity.registerExtendedProperties(CommonProxy.saoIEEP.getIEEP_ID(), CommonProxy.saoIEEP);
        }

    }

    @SubscribeEvent
    public void entityJoinedWorld(EntityJoinWorldEvent event)
    {
        SaoExtendedProperty property = SaoExtendedProperty.getData(event.entity);

        if(property != null)
        {
            property.entitySpawned(event.world, event.entity);
        }
    }

    @SubscribeEvent
    public void playerStartedTracking(PlayerEvent.StartTracking event)
    {
        SaoExtendedProperty property = SaoExtendedProperty.getData(event.target);
        if(property != null)
        {
            property.playerStartedTracking(event.entityPlayer);
        }
    }

    @SubscribeEvent
    public void onClonePlayer(PlayerEvent.Clone event)
    {
        if(event.wasDeath)
        {
            NBTTagCompound compound = new NBTTagCompound();
            SaoExtendedProperty.getData(event.original).saveNBTData(compound);
            SaoExtendedProperty.getData(event.entityPlayer).loadNBTData(compound);
        }
    }
    
    //Everything below this line is RPGCore/Skill related.
    
    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
    	if (event.gui != null && event.gui.getClass().equals(GuiIngameMenu.class)) {
    		event.setCanceled(true);
    		Minecraft mc = Minecraft.getMinecraft();
    		mc.thePlayer.openGui(RPGCore.instance, 5, mc.theWorld, (int) mc.thePlayer.posX, (int) mc.thePlayer.posY, (int) mc.thePlayer.posZ);
    	}
    }
    
	@SubscribeEvent
	public void onRPGCoreClonePlayer(PlayerEvent.Clone event) {
		NBTTagCompound nbt = new NBTTagCompound();
		for (int i = 0; i < RegisterSkill.skillList.size(); i++) {
			SkillLevelBase skillBase = RegisterSkill.skillList.get(i);
			
			skillBase.get(event.original, skillBase.skillId).saveNBTData(nbt);
			skillBase.get(event.entityPlayer, skillBase.skillId).loadNBTData(nbt);
		}
		GlobalLevel.get(event.original).saveNBTData(nbt);
		GlobalLevel.get(event.entityPlayer).loadNBTData(nbt);
		
		EquippedSkills.get(event.original).saveNBTData(nbt);
		EquippedSkills.get(event.entityPlayer).loadNBTData(nbt);
	}
	
	@SubscribeEvent
	public void onRPGCoreEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			event.entity.registerExtendedProperties("equippedSkills", new EquippedSkills());
			event.entity.registerExtendedProperties(GlobalLevel.staticSkillId, new GlobalLevel((EntityPlayer)event.entity, GlobalLevel.staticSkillId));
			event.entity.registerExtendedProperties(SkillLevelAgility.staticSkillId, new SkillLevelAgility((EntityPlayer)event.entity, SkillLevelAgility.staticSkillId));
			event.entity.registerExtendedProperties(SkillLevelStrength.staticSkillId, new SkillLevelStrength((EntityPlayer)event.entity, SkillLevelStrength.staticSkillId));
			event.entity.registerExtendedProperties(SkillLevelHealth.staticSkillId, new SkillLevelHealth((EntityPlayer)event.entity, SkillLevelHealth.staticSkillId));
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
						RPGCore.network.sendTo(new LevelPacket(skill.getXP(), -1, skill.skillId), (EntityPlayerMP) event.player);
					}
				}
				EquippedSkills equippedSkills = (EquippedSkills) EquippedSkills.get(event.player);
				
				System.out.println("Sending global level to client!");
				GlobalLevel glevel = (GlobalLevel) GlobalLevel.get((EntityPlayer) event.player);
				RPGCore.network.sendTo(new LevelPacket((int)(glevel.getXPGlobal()*10), glevel.getSkillPoints(), glevel.skillId), (EntityPlayerMP) event.player);
				
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
		}
	}
}
