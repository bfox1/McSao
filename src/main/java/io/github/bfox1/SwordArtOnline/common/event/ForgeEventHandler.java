package io.github.bfox1.SwordArtOnline.common.event;


import io.github.bfox1.SwordArtOnline.common.player.CapabilitySaoPlayerHandler;
import io.github.bfox1.SwordArtOnline.common.proxy.ClientProxy;
import io.github.bfox1.SwordArtOnline.common.proxy.CommonProxy;
import io.github.bfox1.SwordArtOnline.common.util.Reference;
import io.github.bfox1.SwordArtOnline.common.world.SAOTeleporter;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
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
        if(event.getType().equals(RenderGameOverlayEvent.ElementType.HEALTH) && event.isCancelable() /*&& mc.thePlayer.worldObj.provider.getDimensionId() == Reference.DIMENSIONID*/)
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
	public void onPlayerLog(PlayerLoggedInEvent e)
    {
        System.out.println("Teleporting to Aincrad...");
		if(e.player.worldObj.getWorldType() == CommonProxy.SAO_WORLD_TYPE) {
		    System.out.println("Checking world type.");
			if(e.player.dimension != Reference.saoDimensionId) {
                System.out.println("Teleported to Aincrad");
				EntityPlayerMP playerMP = (EntityPlayerMP) e.player;
                int y = playerMP.worldObj.getChunkFromChunkCoords(0,0).getHeightValue(0,0);
                playerMP.setPositionAndUpdate(0, y, 0);
                ((EntityPlayerMP) e.player).worldObj.setSpawnPoint(new BlockPos(0, y, 0));
				playerMP.getServer().getPlayerList().transferPlayerToDimension(playerMP, Reference.saoDimensionId, new SAOTeleporter(playerMP.mcServer.worldServerForDimension(Reference.saoDimensionId)));
				((EntityPlayerMP) e.player).worldObj.setSpawnPoint(new BlockPos(0, y, 0));
				System.out.println("Teleported to Aincrad");

			}
			else
            {
                System.out.println(e.player.dimension);
                System.out.println(Reference.saoDimensionId);
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

    	if(e.player.worldObj.getWorldType() == CommonProxy.SAO_WORLD_TYPE) {
    		if(e.player.dimension == Reference.saoDimensionId) {
    			EntityPlayerMP playerMP = (EntityPlayerMP) e.player;
				
				BlockPos p = playerMP.worldObj.getSpawnPoint();
				playerMP.setPosition(p.getX(), p.getY(), p.getZ());
    		}
    	}
    }

    @SubscribeEvent
    public void addCapabilities(AttachCapabilitiesEvent event)
    {

        if(event.getObject() instanceof Entity)
        {
            Entity e = (Entity) event.getObject();

            if(!e.hasCapability(CapabilitySaoPlayerHandler.PLAYER_HANDLER_PROPERTIES, null))
            {
                //TODO: FIX CAPABILITIES
               // event.addCapability(new ResourceLocation(Reference.MODID), (ICapabilityProvider) CapabilitySaoPlayerHandler.PLAYER_HANDLER_PROPERTIES);

            }
        }
    }

}
