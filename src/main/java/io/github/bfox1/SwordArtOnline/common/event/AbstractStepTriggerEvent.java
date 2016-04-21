package io.github.bfox1.SwordArtOnline.common.event;

import io.github.bfox1.SwordArtOnline.quest.AbstractQuestStep;
import io.github.bfox1.SwordArtOnline.quest.Quests;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * Created by bfox1 on 4/19/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class AbstractStepTriggerEvent extends Event
{
    private final EntityPlayerMP playerMP;
    private final Quests quests;
    private final boolean completed;
    private final AbstractQuestStep step;

    public AbstractStepTriggerEvent(EntityPlayerMP playerMP, Quests quests, boolean completed, AbstractQuestStep step)
    {
        this.playerMP = playerMP;
        this.quests = quests;
        this.completed = completed;
        this.step = step;
    }

    public EntityPlayerMP getPlayerMP() {
        return playerMP;
    }

    public Quests getQuests() {
        return quests;
    }

    public boolean isCompleted() {
        return completed;
    }

    public AbstractQuestStep getStep() {
        return step;
    }
}
