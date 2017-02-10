package io.github.bfox1.SwordArtOnline.server;

import io.github.bfox1.SwordArtOnline.server.quest.QuestManager;

/**
 * Created by bfox1 on 2/9/2017.
 */
public class Server
{
    private final QuestManager manager;

    public Server()
    {
        this.manager = new QuestManager();
    }


    public final QuestManager getQuestManager()
    {
        return manager;
    }
}
