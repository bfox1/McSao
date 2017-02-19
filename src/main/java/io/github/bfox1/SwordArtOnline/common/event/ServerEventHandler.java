package io.github.bfox1.SwordArtOnline.common.event;

import io.github.bfox1.SwordArtOnline.common.player.CapabilitySaoPlayerHandler;
import io.github.bfox1.SwordArtOnline.common.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by bfox1 on 2/12/2017.
 */
public class ServerEventHandler
{


    @SubscribeEvent
    public void addCapabilities(AttachCapabilitiesEvent event)
    {
        if(event.getObject() instanceof EntityPlayer)
        {
            Entity e = (Entity) event.getObject();

             if(!e.hasCapability(CapabilitySaoPlayerHandler.PLAYER_HANDLER_PROPERTIES, null))
             {
                 event.addCapability(new ResourceLocation(Reference.MODID),new CapabilitySaoPlayerHandler());
             }
        }
    }
}
