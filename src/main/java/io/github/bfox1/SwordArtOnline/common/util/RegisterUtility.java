package io.github.bfox1.SwordArtOnline.common.util;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

/**
 * Created by bfox1 on 4/14/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
@Deprecated
public class RegisterUtility
{
    @Deprecated
    public static void registerBlockItem(Block block, int meta, String blockName)
    {
        ModelBakery.registerItemVariants(Item.getItemFromBlock(block), new ResourceLocation(Reference.MODID, "texture/blocks/" + blockName));

        registerBlock(block, meta, blockName);
    }

    /**
     * Registers Blocks with different BlockStates or as they were called MetaData.
     * @param block The Block to register and render.
     * @param blockNames The Array of Stringed names of the Blocks.
     */
    @Deprecated
    public static void registerBlockMetaItem(Block block, String... blockNames)
    {



        for(int i = 0; i < blockNames.length; i++)
        {
            registerBlock(block, i, blockNames[i].replaceAll("sao:", ""));
            ModelBakery.registerItemVariants(Item.getItemFromBlock(block), new ResourceLocation(Reference.MODID, "texture/blocks/" + blockNames));
        }
    }
    @Deprecated
    public static void registerBlock(Block block , int meta, String file)
    {
        registerItem(Item.getItemFromBlock(block), meta, file);
    }
    @Deprecated
    public static void registerItem(Item item, String file)
    {
        registerItem(item, 0, file);
    }
    @Deprecated
    public static void registerItem(Item item, int meta, String file)
    {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation(Reference.MODID + ":" + file, "inventory"));

    }
}
