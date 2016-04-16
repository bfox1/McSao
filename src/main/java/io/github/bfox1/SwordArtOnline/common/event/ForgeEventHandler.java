package io.github.bfox1.SwordArtOnline.common.event;

import io.github.bfox1.SwordArtOnline.common.proxy.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
}
