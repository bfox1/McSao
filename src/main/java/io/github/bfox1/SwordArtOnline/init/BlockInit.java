package io.github.bfox1.SwordArtOnline.init;

import io.github.bfox1.SwordArtOnline.client.creativetabs.SaoTabsManager;
import io.github.bfox1.SwordArtOnline.common.blocks.*;
import io.github.bfox1.SwordArtOnline.common.blocks.itemblock.SaoItemBlockMetaAbstract;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistry;

import java.util.HashMap;

/**
 * Created by bfox1 on 4/3/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
//@GameRegistry.ObjectHolder(Reference.MODID)
public class BlockInit
{

    public static final HashMap<String, SaoBlockAbstract> saoBlocks = new HashMap<>();


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

    @Mod.EventBusSubscriber
    public static class RegistrationHandler
    {
        @SubscribeEvent
        public void onBlockRegistration(RegistryEvent.Register<Block> blockRegistryEvent)
        {
            final IForgeRegistry<Block> registry = blockRegistryEvent.getRegistry();

            for(SaoBlockAbstract saoBlockAbstract : saoBlocks.values())
            {
                saoBlockAbstract = registerBlocks(registry, saoBlockAbstract);
                System.out.println(saoBlockAbstract.getRegistryName());
            }

        }

        private static <T extends Block> T registerBlocks(IForgeRegistry registry, T block)
        {
            return BlockInit.register(block);
        }

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

    public static <T extends Block> T register(T block)
    {
        block.setUnlocalizedName(block.getRegistryName().toString());
        GameRegistry.register(block);

        ItemBlock iBlock = new ItemBlock(block);
        iBlock.setRegistryName(block.getRegistryName());

        GameRegistry.register(iBlock);
        block.setCreativeTab(SaoTabsManager.SaoBlocks);
        return block;
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
                    System.out.println(s);
                    System.out.println(var.getRegistryName());
                    ModelLoader.setCustomModelResourceLocation(new SaoItemBlockMetaAbstract(var), i, new ModelResourceLocation(s, "inventory"));
                    i++;
                }
            }
            else
            {
                System.out.println(ItemBlock.getItemFromBlock(v).getRegistryName());
                ModelLoader.setCustomModelResourceLocation(ItemBlock.getItemFromBlock(v), 0, new ModelResourceLocation(ItemBlock.getItemFromBlock(v).getRegistryName(), "inventory"));
            }
        });
    }

    public static void registerItemBlocks()
    {

    }

    private static void registerVariations(SaoBlockVariationAbstract variationAbstract)
    {
        //variationAbstract.setSubTypeFullNameList();
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

    public static SaoBlockAbstract getSaoBlocks(String name)
    {
        return saoBlocks.get(name);
    }


}
