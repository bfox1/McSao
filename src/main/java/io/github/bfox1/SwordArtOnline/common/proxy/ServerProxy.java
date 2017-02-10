package io.github.bfox1.SwordArtOnline.common.proxy;

import io.github.bfox1.SwordArtOnline.server.Server;
import io.github.bfox1.SwordArtOnline.server.quest.QuestEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

/**
 * Created by bfox1 on 4/2/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class ServerProxy extends CommonProxy
{
    public static final Server server = new Server();
    @Override
    public void registerEventHandlers()
    {
        super.registerEventHandlers();
        MinecraftForge.EVENT_BUS.register(new QuestEventHandler());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {

    }
}
