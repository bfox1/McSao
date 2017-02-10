package io.github.bfox1.SwordArtOnline.common.blocks;

import io.github.bfox1.SwordArtOnline.api.block.ISaoBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

/**
 * Created by bfox1 on 4/2/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class SaoBlockAbstract extends Block implements ISaoBlock
{

    private boolean canBreak;
    private final float HARDNESS_VALUE;

    public SaoBlockAbstract(Material p_i46399_1_, float hardnessValue)
    {
        super(p_i46399_1_);
       // this.setCreativeTab(SaoTabsManager.SaoBlocks);
        this.HARDNESS_VALUE = hardnessValue;
        this.setBreakable(false);
    }


    /**
     * This Method will determine if the Block is capable of being destroyed.
     * Setting Value to True will mean the Block can be broken.
     * @param value boolean value.
     */
    public void setBreakable(boolean value)
    {
        if(!value)
        {
            this.setBlockUnbreakable();
        }
        else
        {
            this.setHardness(HARDNESS_VALUE);
        }
    }


    @Override
    public String getSaoMetaBlockName(ItemStack itemStack) {
        return "";
    }

    @Override
    public Block getBlock() {
        return this;
    }

    @Override
    public SaoBlockAbstract setRegName(String name)
    {
        this.setRegistryName(name);
        return this;
    }

}
