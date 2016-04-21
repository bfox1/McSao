package io.github.bfox1.SwordArtOnline.common.event;

import io.github.bfox1.SwordArtOnline.common.entity.SaoExtendedProperty;
import io.github.bfox1.SwordArtOnline.common.proxy.ClientProxy;
import io.github.bfox1.SwordArtOnline.common.proxy.CommonProxy;
import io.github.bfox1.SwordArtOnline.common.util.Reference;
import io.github.bfox1.SwordArtOnline.common.world.SAOTeleporter;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.EntityEvent;
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
                ((EntityPlayerMP) e.player).worldObj.setSpawnPoint(new BlockPos(0, y, 0));
				playerMP.mcServer.getConfigurationManager().transferPlayerToDimension(playerMP, Reference.saoDimensionId, new SAOTeleporter(playerMP.mcServer.worldServerForDimension(Reference.saoDimensionId)));
				
				//((EntityPlayerMP) e.player).worldObj.setSpawnPoint(new BlockPos(0, y, 0));
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
}