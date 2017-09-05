package io.github.bfox1.SwordArtOnline.common;

import io.github.bfox1.SwordArtOnline.api.block.ISaoBlock;
import io.github.bfox1.SwordArtOnline.api.item.ISaoItem;
import io.github.bfox1.SwordArtOnline.init.BlockInit;
import io.github.bfox1.SwordArtOnline.init.ItemInit;

import java.util.HashMap;

/**
 * Created by bfox1 on 2/9/2017.
 */
public class CommonAccessor
{
    public static HashMap<String, ISaoBlock> getSaoBlockList()
    {
        return BlockInit.saoBlocks;
    }

    public static HashMap<String, ISaoItem> getSaoItemList()
    {
        return ItemInit.saoItems;
    }
}
