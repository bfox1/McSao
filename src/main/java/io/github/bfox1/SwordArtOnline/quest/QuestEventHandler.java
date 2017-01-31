package io.github.bfox1.SwordArtOnline.quest;

import io.github.bfox1.SwordArtOnline.common.event.questevents.QuestItemPickupEvent;
import io.github.bfox1.SwordArtOnline.common.event.questevents.QuestPlayerInteract;
import io.github.bfox1.SwordArtOnline.common.player.CapabilitySaoPlayerHandler;
import io.github.bfox1.SwordArtOnline.common.player.PlayerPropertyHandler;
import io.github.bfox1.SwordArtOnline.common.proxy.ServerProxy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

/**
 * Created by bfox1 on 4/24/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class QuestEventHandler
{

    private QuestManager manager = ServerProxy.manager;

    @SubscribeEvent
    public void onItemPickupEvent(PlayerEvent.ItemPickupEvent event)
    {

        //SaoExtendedProperty property = (SaoExtendedProperty)event.player.getExtendedProperties(SaoExtendedProperty.IEEP_ID);
        PlayerPropertyHandler property = event.player.getCapability(CapabilitySaoPlayerHandler.PLAYER_HANDLER_PROPERTIES, null);
        if(property.getQuestsList().size() != 0)
        {
            QuestItemPickupEvent event1 = new QuestItemPickupEvent(event.player, event.pickedUp);

            manager.callActives(event1, property);
        }
    }

    @SubscribeEvent
    public void onPlayerInteractEvent(PlayerInteractEvent event)
    {
        PlayerPropertyHandler property = event.getEntity().getCapability(CapabilitySaoPlayerHandler.PLAYER_HANDLER_PROPERTIES, null);

        if(property.getQuestsList() != null)
        if(property.getQuestsList().size() != 0)
        {

            manager.callActives(new QuestPlayerInteract(event), property);
        }

    }

    @SubscribeEvent
    public void onPlayerJoinEvent(AttachCapabilitiesEvent event)
    {
        if(event.getObject() != null && event.getObject() instanceof EntityPlayer)
        {
            Entity e = (Entity) event.getObject();
            if(!e.worldObj.isRemote)
            {
               // SaoExtendedProperty property = (SaoExtendedProperty.getData(event.entity));
                manager.setPlayerQuest(event, "MiscTemplate", "testObjective.lua");
            }

        }
    }

}
