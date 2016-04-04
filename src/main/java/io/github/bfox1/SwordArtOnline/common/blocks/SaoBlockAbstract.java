package io.github.bfox1.SwordArtOnline.common.blocks;

import io.github.bfox1.SwordArtOnline.client.creativetabs.SaoTabsManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * Created by bfox1 on 4/2/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public abstract class SaoBlockAbstract extends Block
{

    private boolean canBreak;

    public SaoBlockAbstract(Material p_i46399_1_)
    {
        super(p_i46399_1_);
        this.setCreativeTab(SaoTabsManager.SaoBlocks);
    }


    /**
     * This Method will determine if the Block is capable of being destroyed.
     * Setting Value to True will mean the Block can be broken.
     * @param value boolean value.
     */
    public abstract void setBreakable(boolean value);

    /**
     * All this method does is returns SaoBlockAbstract instead of Block.
     * @param name The name of the Block
     * @return SaoBlockAbstract Object.
     */
    @Override
    public SaoBlockAbstract setUnlocalizedName(String name)
    {
        return (SaoBlockAbstract)super.setUnlocalizedName(name);
    }

}
