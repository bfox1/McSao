package io.github.bfox1.SwordArtOnline.common.event;

import io.github.bfox1.SwordArtOnline.server.questevents.QuestItemPickupEvent;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by bfox1 on 4/19/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class SaoEventFactory
{

    public static void fireQuestPickupEvent(QuestItemPickupEvent event)
    {
        MinecraftForge.EVENT_BUS.post(event);
    }
}
