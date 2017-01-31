package io.github.bfox1.SwordArtOnline.common.items;

import io.github.bfox1.SwordArtOnline.client.creativetabs.SaoTabsManager;
import net.minecraft.item.Item;

/**
 * Created by bfox1 on 4/3/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class SaoItemAbstract extends Item implements ISaoItem
{
    public SaoItemAbstract()
    {
        super();
        this.setCreativeTab(SaoTabsManager.SaoItems);
    }


    public int getItemID()
    {
        return Item.getIdFromItem(this);
    }

    public ISaoItem setRegName(String name)
    {
        this.setRegistryName(name);
        return this;
    }

    @Override
    public Item getItem()
    {
        return this;
    }
}
