package io.github.bfox1.SwordArtOnline.init;

import io.github.bfox1.SwordArtOnline.api.block.ISaoBlock;
import io.github.bfox1.SwordArtOnline.client.creativetabs.SaoTabsManager;
import io.github.bfox1.SwordArtOnline.common.blocks.*;
import io.github.bfox1.SwordArtOnline.common.blocks.itemblock.SaoItemBlockMetaAbstract;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.HashMap;

/**
 * Created by bfox1 on 4/3/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class BlockInit
{

    public static final HashMap<String, ISaoBlock> saoBlocks = new HashMap<>();


    static
    {
       // setBlock("aincrad_cobble", new AincradCobbleVariation(Material.ROCK, 3));
       // setBlock("aincrad_grass", new AincradGrassVariation(Material.GRASS, 4));
       // setBlock("aincrad_stone", new AincradStoneVariation(Material.ROCK, 4));
       // setBlock("aincrad_dirt", new AincradDirtVariation(Material.GROUND,4));

        setBlock("aincrad_grass_t1", new AincradGrass());
        setBlock("aincrad_grass_t2", new AincradGrass());
        setBlock("aincrad_wall_t1", new AincradWalls());
        setBlock("aincrad_wall_t2", new AincradWalls());
        setBlock("aincrad_wall_t3", new AincradWalls());
        setBlock("aincrad_dirt_t1", new AincradDirt());
    }

    public static void register()
    {
        for(ISaoBlock block : saoBlocks.values())
        {
            if(block instanceof SaoBlockVariationAbstract)
            {
                registerVariations((SaoBlockVariationAbstract) block);
            }
            else
            {
                block.getBlock().setUnlocalizedName(block.getBlock().getRegistryName().toString());
                GameRegistry.register(block.getBlock());


                ItemBlock iBlock = new ItemBlock(block.getBlock());
                iBlock.setRegistryName(block.getBlock().getRegistryName());

                GameRegistry.register(iBlock);
            }

        }

        saoBlocks.forEach((k,v) -> v.getBlock().setCreativeTab(SaoTabsManager.SaoBlocks) );
    }



    public static void registerRenders()
    {
        saoBlocks.forEach((k,v) ->
        {
            if(v instanceof SaoBlockVariationAbstract)
            {
                SaoBlockVariationAbstract var = (SaoBlockVariationAbstract) v;

                int i =0;
                for(String s: var.getSubtypeArray())
                {
                    ModelLoader.setCustomModelResourceLocation(new SaoItemBlockMetaAbstract(var), i, new ModelResourceLocation(s, "inventory"));
                    i++;
                }
            }
            else
            {

                ModelLoader.setCustomModelResourceLocation(ItemBlock.getItemFromBlock(v.getBlock()), 0, new ModelResourceLocation(ItemBlock.getItemFromBlock(v.getBlock()).getRegistryName(), "inventory"));
            }
        });
    }

    public static void registerItemBlocks()
    {

    }

    private static void registerVariations(SaoBlockVariationAbstract variationAbstract)
    {
        variationAbstract.setUnlocalizedName(variationAbstract.getRegistryName().toString()).setSubTypeFullNameList();
        GameRegistry.register(variationAbstract);
        GameRegistry.register(new SaoItemBlockMetaAbstract(variationAbstract));

    }

    private static void setBlock(String name, SaoBlockAbstract block)
    {
        saoBlocks.put(name, block.setRegName(name));
    }

    private static void setRegBlock(String name)
    {
        saoBlocks.put(name, new SaoBlockAbstract(Material.GROUND, 1.5F));
    }

    public static ISaoBlock getSaoBlocks(String name)
    {
        return saoBlocks.get(name);
    }


}
