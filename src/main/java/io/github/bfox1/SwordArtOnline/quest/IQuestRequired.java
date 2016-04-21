package io.github.bfox1.SwordArtOnline.quest;

import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by bfox1 on 4/19/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public interface IQuestRequired
{
    boolean checkRequirements(EntityPlayer player);

    void notifyMissingRequirements(EntityPlayer player);


}
