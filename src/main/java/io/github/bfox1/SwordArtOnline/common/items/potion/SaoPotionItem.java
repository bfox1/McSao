package io.github.bfox1.SwordArtOnline.common.items.potion;

import io.github.bfox1.SwordArtOnline.common.items.SaoItemAbstract;

/**
 * Created by bfox1 on 4/14/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class SaoPotionItem extends SaoItemAbstract
{
    private final PotionType type;

    public SaoPotionItem(PotionType type)
    {
        this.type = type;
    }

    private enum PotionType
    {
        HEALING(0, "Healing"),
        REGENERATION(1, "Regeneration"),
        ATTACKBUFF(2, "Attack Buff");

        private int id;
        private String name;
        PotionType(int id, String name)
        {
            this.id = id;
            this.name = name;
        }
    }
}
