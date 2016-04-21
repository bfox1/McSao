package io.github.bfox1.SwordArtOnline.quest;

import io.github.bfox1.SwordArtOnline.quest.required.QuestRequired;

/**
 * Created by bfox1 on 4/17/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class Quests
{
    private final DoublyLinkedListImpl<AbstractQuestStep> stepList;
    private final String questName;
    private QuestRequired questRequired;

    public Quests(String questName)
    {
        this.stepList = new DoublyLinkedListImpl<AbstractQuestStep>();
        this.questName = questName;

    }

    public void addFirst(AbstractQuestStep step)
    {
        stepList.addFirst(step);
    }

    public void addToList(AbstractQuestStep step)
    {
        this.stepList.addLast(step);
    }

    public void addSteps(AbstractQuestStep firstStep, AbstractQuestStep... steps)
    {
        this.stepList.addFirst(firstStep);
        for(AbstractQuestStep step : steps)
        {
            this.stepList.addLast(step);
        }
    }

    public String getQuestName() {
        return questName;
    }
}
