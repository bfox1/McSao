package io.github.bfox1.SwordArtOnline.common.event;

import io.github.bfox1.SwordArtOnline.common.proxy.CommonProxy;
import io.github.bfox1.SwordArtOnline.common.util.Reference;
import io.github.bfox1.SwordArtOnline.world.SAOTeleporter;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
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
    private Minecraft mc = Minecraft.getMinecraft();

    public ForgeEventHandler()
    {

    }
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onRenderHealthBar(RenderGameOverlayEvent.Pre event)
    {
        if(event.type.equals(RenderGameOverlayEvent.ElementType.HEALTH) && event.isCancelable() /*&& mc.thePlayer.worldObj.provider.getDimensionId() == Reference.DIMENSIONID*/)
        {
            event.setCanceled(true);
            CommonProxy.saoHud.drawBase();
            CommonProxy.saoHud.drawBar(mc.thePlayer.getHealth() / mc.thePlayer.getMaxHealth());
        }
    }
    
    @SubscribeEvent
	public void onLog(PlayerLoggedInEvent e) {
		if(mc.theWorld.getWorldType() == CommonProxy.saoWorld) {
			if(e.player.dimension != Reference.saoDimensionId) {
				EntityPlayerMP playerMP = (EntityPlayerMP) e.player;
				playerMP.mcServer.getConfigurationManager().transferPlayerToDimension(playerMP, Reference.saoDimensionId, new SAOTeleporter(playerMP.mcServer.worldServerForDimension(Reference.saoDimensionId)));
				playerMP.setPositionAndUpdate(0, 61, 0);
			}
		}
	}
    
    @SubscribeEvent
    public void onPlayerRespawn(PlayerRespawnEvent e) {
    	if(mc.theWorld.getWorldType() == CommonProxy.saoWorld) {
    		if(e.player.dimension == Reference.saoDimensionId) {
    			EntityPlayerMP playerMP = (EntityPlayerMP) e.player;
    			playerMP.setPositionAndUpdate(0, 61, 0);
    		}
    	}
    }
}
