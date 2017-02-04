package io.github.bfox1.SwordArtOnline.common.blocks.itemblock;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

/**
 * Created by bfox1 on 4/2/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public interface ISaoBlock
{
    String getSaoMetaBlockName(ItemStack itemStack);

    Block getBlock();

    ISaoBlock setRegName(String name);
}
