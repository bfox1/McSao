package io.github.bfox1.SwordArtOnline.common.proxy;

import io.github.bfox1.SwordArtOnline.common.blocks.SaoBlockVariationAbstract;
import io.github.bfox1.SwordArtOnline.common.util.Models;
import io.github.bfox1.SwordArtOnline.common.util.Reference;
import io.github.bfox1.SwordArtOnline.common.util.RegisterUtility;
import io.github.bfox1.SwordArtOnline.init.BlockInit;
import io.github.bfox1.SwordArtOnline.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
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
        OBJLoader.instance.addDomain(Reference.MODID);

        ModelLoader.setCustomModelResourceLocation(ItemInit.healingCrystal, 0, Models.crystalHealing);
        ModelLoader.setCustomModelResourceLocation(ItemInit.antidoteCrystal, 0, Models.crystalAntidote);
        ModelLoader.setCustomModelResourceLocation(ItemInit.teleportCrystal, 0, Models.crystalTeleport);

    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
        blockRenderRegister(BlockInit.aincradCobbleVariation);
        ItemInit.register();
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
        RegisterUtility.registerBlockMetaItem(block, ((SaoBlockVariationAbstract)block).getSubtypeArray());
    }

    /**
     * Registers a single block.
     * @param block The Block to register and render.
     * @param meta The id of the MEta. Should be left as 0, if anything else, use ClientProxy#registerBlockMetaItem
     * @param blockName The Blocks name.
     */

}
