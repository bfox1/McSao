package io.github.bfox1.SwordArtOnline.init;

import io.github.bfox1.SwordArtOnline.client.creativetabs.SaoTabsManager;
import io.github.bfox1.SwordArtOnline.common.blocks.*;
import io.github.bfox1.SwordArtOnline.common.blocks.itemblock.SaoItemBlockMetaAbstract;
import io.github.bfox1.SwordArtOnline.common.util.Reference;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
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

    public static final HashMap<String, SaoBlockAbstract> saoBlocks = new HashMap<>();


    static
    {
        setBlock("aincrad_cobble", new AincradCobbleVariation(Material.ROCK, 3));
        setBlock("aincrad_grass", new AincradGrassVariation(Material.GRASS, 4));
        setBlock("aincrad_stone", new AincradStoneVariation(Material.ROCK, 4));
        setBlock("aincrad_dirt", new AincradDirtVariation(Material.GROUND,4));
    }


    public static void register()
    {
        for(SaoBlockAbstract block : saoBlocks.values())
        {
            if(block instanceof SaoBlockVariationAbstract)
            {
                registerVariations((SaoBlockVariationAbstract) block);
            }
            else
            {
                block.setUnlocalizedName(block.getRegistryName().toString());
                GameRegistry.register(block);


                ItemBlock iBlock = new ItemBlock(block);
                iBlock.setRegistryName(block.getRegistryName());

                GameRegistry.register(iBlock);
            }

        }

        saoBlocks.forEach((k,v) -> v.setCreativeTab(SaoTabsManager.SaoBlocks) );
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

                    ModelLoader.setCustomModelResourceLocation(ItemBlock.getItemFromBlock(var), i, new ModelResourceLocation(s.replace("sao:", ""), "inventory"));
                    ModelBakery.registerItemVariants(Item.getItemFromBlock(var), new ResourceLocation(Reference.MODID, s.replaceAll("sao:", "")));

                    i++;
                }
                //ModelBakery.registerItemVariants(new SaoItemBlockMetaAbstract(var), new ResourceLocation());
            }
            ModelLoader.setCustomModelResourceLocation(ItemBlock.getItemFromBlock(v), 0, new ModelResourceLocation(ItemBlock.getItemFromBlock(v).getRegistryName(), "inventory"));
        });
    }

    public static void registerItemBlocks()
    {

    }

    private static void registerVariations(SaoBlockVariationAbstract variationAbstract)
    {
        variationAbstract.setSubTypeFullNameList();

        for(String s : variationAbstract.subTypeNamList)
        {
            variationAbstract.setUnlocalizedName(s);
        }
        GameRegistry.register(variationAbstract);
        GameRegistry.register(new SaoItemBlockMetaAbstract(variationAbstract));
        variationAbstract.setUnlocalizedName(variationAbstract.getRegistryName().toString()).setSubTypeFullNameList();
    }

    private static void setBlock(String name, SaoBlockAbstract block)
    {
        saoBlocks.put(name, block.setRegName(name));
    }

    private static void setRegBlock(String name)
    {
        saoBlocks.put(name, new SaoBlockAbstract(Material.GROUND, 1.5F));
    }

    public static SaoBlockAbstract getSaoBlocks(String name)
    {
        return saoBlocks.get(name);
    }


}
