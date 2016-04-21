package io.github.bfox1.SwordArtOnline.quest;

import io.github.bfox1.SwordArtOnline.common.event.AbstractStepTriggerEvent;
import io.github.bfox1.SwordArtOnline.common.event.SaoEventFactory;

/**
 * Created by bfox1 on 4/19/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public abstract class AbstractQuestStep
{
    private final Quests parentQuest;

    private AbstractStepTriggerEvent event;

    public AbstractQuestStep(Quests parentQuest)
    {
        this.parentQuest = parentQuest;
    }
    public abstract void abstractStartStep();

    public abstract void abstractCompletedStep();

    public abstract void abstractEndStep();

    private void startStep()
    {
        abstractStartStep();

    }

    public void setEventTrigger(AbstractStepTriggerEvent event)
    {
        this.event = event;
    }


    private void completedStep()
    {
        abstractCompletedStep();

        SaoEventFactory.triggerStepEvent(event);
    }


    public Quests getParentQuest()
    {
        return parentQuest;
    }
}
