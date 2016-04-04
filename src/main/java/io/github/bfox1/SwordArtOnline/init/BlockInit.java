package io.github.bfox1.SwordArtOnline.init;

import io.github.bfox1.SwordArtOnline.common.blocks.AincradCobbleVariation;
import io.github.bfox1.SwordArtOnline.common.blocks.SaoBlockVariationAbstract;
import net.minecraft.block.material.Material;

/**
 * Created by bfox1 on 4/3/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class BlockInit
{
    public static final SaoBlockVariationAbstract aincradCobbleVariation;
    static
    {
        aincradCobbleVariation =  new AincradCobbleVariation(Material.rock, 3).setUnlocalizedName("AincradCobble").setSubTypeFullNameList();
    }



}
