package io.github.bfox1.SwordArtOnline.quest.required;

import io.github.bfox1.SwordArtOnline.common.event.AbstractStepTriggerEvent;
import io.github.bfox1.SwordArtOnline.common.event.SaoEventFactory;
import io.github.bfox1.SwordArtOnline.init.ItemInit;
import io.github.bfox1.SwordArtOnline.quest.AbstractQuestStep;
import io.github.bfox1.SwordArtOnline.quest.Quests;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

/**
 * Created by bfox1 on 4/19/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class QuestRightClickElucidator extends Quests
{

    public QuestRightClickElucidator(String questName)
    {
        super(questName);
    }

    private class ElucidatorStep extends AbstractQuestStep
    {

        public ElucidatorStep(Quests parentQuest) {
            super(parentQuest);
        }

        @Override
        public void abstractStartStep() {

        }

        @Override
        public void abstractCompletedStep() {

        }

        @Override
        public void abstractEndStep() {

        }
    }


    private void setFirstStep()
    {
        ElucidatorStep step = new ElucidatorStep(this)
        {
          @SubscribeEvent
            public void onInventoryEvent(PlayerEvent.ItemPickupEvent event)
          {
              if(event.pickedUp.getEntityItem().getItem() == ItemInit.saoSwordItem[2])
              {
                  SaoEventFactory.triggerStepEvent(new AbstractStepTriggerEvent((EntityPlayerMP)event.player, this, true, ));
              }
          }
        };
    }
}
