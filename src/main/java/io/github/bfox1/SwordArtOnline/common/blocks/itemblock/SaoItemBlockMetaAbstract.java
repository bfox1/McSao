package io.github.bfox1.SwordArtOnline.common.blocks.itemblock;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * Created by bfox1 on 4/2/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class SaoItemBlockMetaAbstract extends ItemBlock
{
    public SaoItemBlockMetaAbstract(Block block)
    {
        super(block);
        this.setRegistryName(block.getRegistryName().toString());
        if(!(block instanceof ISaoBlock))
        {
            throw new IllegalArgumentException(String.format("The given Block %s is not an instance of SaoBlock!", block.getUnlocalizedName()));
        }
        this.setHasSubtypes(true);
    }



    @Override
    public int getMetadata(int damage)
    {
        return damage;
    }

    /**
     * Returns the Meta Items Unlocalized Name.
     * @param stack ItemStack
     * @return unlocalized and Modified Name.
     */
    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName(stack) + "." + ((ISaoBlock)this.block).getSaoMetaBlockName(stack);
    }
}
