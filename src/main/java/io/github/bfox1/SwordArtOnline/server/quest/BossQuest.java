package io.github.bfox1.SwordArtOnline.server.quest;

/**
 * Created by bfox1 on 4/23/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class BossQuest extends Quests
{
    public static final String templateName = "BossTemplate";
    public static final String questPath = "SaoQuest/BossQuests";

    public BossQuest() {
        super(templateName, questPath);
    }
}
