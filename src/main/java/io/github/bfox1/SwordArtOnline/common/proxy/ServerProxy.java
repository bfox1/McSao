package io.github.bfox1.SwordArtOnline.common.proxy;

import io.github.bfox1.SwordArtOnline.quest.QuestEventHandler;
import io.github.bfox1.SwordArtOnline.quest.QuestManager;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by bfox1 on 4/2/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class ServerProxy extends CommonProxy
{
    public static final QuestManager manager = new QuestManager();

    @Override
    public void registerEventHandlers()
    {
        MinecraftForge.EVENT_BUS.register(new QuestEventHandler());
    }
}
