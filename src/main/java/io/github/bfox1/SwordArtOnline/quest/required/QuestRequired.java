package io.github.bfox1.SwordArtOnline.quest.required;

import io.github.bfox1.SwordArtOnline.quest.IQuestRequired;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by bfox1 on 4/19/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public abstract class QuestRequired implements IQuestRequired
{

    @Override
    public boolean checkRequirements(EntityPlayer player)
    {

        return false;
    }

    @Override
    public void notifyMissingRequirements(EntityPlayer player)
    {

    }
}
