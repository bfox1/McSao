package io.github.bfox1.SwordArtOnline.api.item;

import net.minecraft.item.Item;

/**
 * Created by bfox1 on 1/30/2017.
 */
public interface ISaoItem
{
    ISaoItem setRegName(String name);

    Item getItem();
}
