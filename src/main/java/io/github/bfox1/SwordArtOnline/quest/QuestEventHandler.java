package io.github.bfox1.SwordArtOnline.quest;

import io.github.bfox1.SwordArtOnline.common.entity.SaoExtendedProperty;
import io.github.bfox1.SwordArtOnline.common.event.questevents.QuestItemPickupEvent;
import io.github.bfox1.SwordArtOnline.common.event.questevents.QuestPlayerInteract;
import io.github.bfox1.SwordArtOnline.common.proxy.ServerProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
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
        SaoExtendedProperty property = (SaoExtendedProperty)event.player.getExtendedProperties(SaoExtendedProperty.IEEP_ID);
        if(property.getInformation().getQuestsList().size() != 0)
        {
            QuestItemPickupEvent event1 = new QuestItemPickupEvent(event.player, event.pickedUp);

            manager.callActives(event1, property);
        }
    }

    @SubscribeEvent
    public void onPlayerInteractEvent(PlayerInteractEvent event)
    {
        SaoExtendedProperty property = (SaoExtendedProperty.getData(event.entityPlayer));

        if(property.getInformation().getQuestsList().size() != 0)
        {

            manager.callActives(new QuestPlayerInteract(event), property);
        }

    }

    @SubscribeEvent
    public void onPlayerJoinEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        if(event.player != null)
        {
            if(event.player.worldObj.isRemote)
            {
                SaoExtendedProperty property = (SaoExtendedProperty.getData(event.player));
                manager.setPlayerQuest(property, "MiscTemplate", "testObjective.lua");
            }

        }
    }

}
