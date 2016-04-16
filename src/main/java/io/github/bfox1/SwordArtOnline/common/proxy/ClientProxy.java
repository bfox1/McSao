package io.github.bfox1.SwordArtOnline.common.proxy;

import io.github.bfox1.SwordArtOnline.common.blocks.SaoBlockVariationAbstract;
import io.github.bfox1.SwordArtOnline.common.util.Reference;
import io.github.bfox1.SwordArtOnline.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by bfox1 on 4/2/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class ClientProxy extends CommonProxy
{

    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);

    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
        blockRenderRegister(BlockInit.aincradCobbleVariation);
    }
    
    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
    	super.postInit(event);
    }

    /**
     * Method is used to not only register but render the block and itemBlock.
     * @param block
     */
    public void blockRenderRegister(Block block)
    {

        //registerBlockMetaItem(block, "sao:AincradCobble_block_one","sao:AincradCobble_block_two","sao:AincradCobble_block_three","sao:AincradCobble_block_four");
        registerBlockMetaItem(block, ((SaoBlockVariationAbstract)block).getSubtypeArray());
    }

    /**
     * Registers a single block.
     * @param block The Block to register and render.
     * @param meta The id of the MEta. Should be left as 0, if anything else, use ClientProxy#registerBlockMetaItem
     * @param blockName The Blocks name.
     */
    private  void registerBlockItem(Block block, int meta, String blockName)
    {
        ModelBakery.addVariantName(Item.getItemFromBlock(block), blockName);

        regItem(block, meta, blockName);
    }

    /**
     * Registers Blocks with different BlockStates or as they were called MetaData.
     * @param block The Block to register and render.
     * @param blockNames The Array of Stringed names of the Blocks.
     */
    private void registerBlockMetaItem(Block block, String... blockNames)
    {
        ModelBakery.addVariantName(Item.getItemFromBlock(block), blockNames);

        for(int i = 0; i < blockNames.length; i++)
        {
            regItem(block, i, blockNames[i].replaceAll("sao:", ""));
        }
    }
    private  void regItem(Block block , int meta, String file)
    {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), meta, new ModelResourceLocation(Reference.MODID + ":" + file, "inventory"));
    }


}
