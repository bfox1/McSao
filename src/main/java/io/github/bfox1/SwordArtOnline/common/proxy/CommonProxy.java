package io.github.bfox1.SwordArtOnline.common.proxy;

import io.github.bfox1.SwordArtOnline.common.blocks.itemblock.SaoItemBlockMetaAbstract;
import io.github.bfox1.SwordArtOnline.common.event.ForgeEventHandler;
import io.github.bfox1.SwordArtOnline.common.player.CapabilitySaoPlayerHandler;
import io.github.bfox1.SwordArtOnline.common.util.Reference;
import io.github.bfox1.SwordArtOnline.common.world.SAOWorldProvider;
import io.github.bfox1.SwordArtOnline.common.world.SAOWorldType;
import io.github.bfox1.SwordArtOnline.init.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.File;

/**
 * Created by bfox1 on 4/2/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class CommonProxy implements SaoProxy
{
	public static WorldType SAO_WORLD_TYPE = new SAOWorldType("saoWorldType");

    @Override
    public void initClientConfig(File file)
    {

    }

    @Override
    public void registerTileEntities()
    {

    }

    @Override
    public void initRenderingAndTexture()
    {

    }

    @Override
    public void registerEventHandlers()
    {
    	//MinecraftForge.EVENT_BUS.register(new SkillBarHandler());
        MinecraftForge.EVENT_BUS.register(new ForgeEventHandler());

    }

    @Override
    public void registerKeyBindings()
    {

    }

    @Override
    public void registerEntityLiving()
    {

    }

    @Override
    public void registerGlobalEntity()
    {

    }

    @Override
    public void registerDimension()
    {

    }

    @Override
    public void addChestLoot()
    {

    }

    @Override
    public void registerRenderers()
    {

    }

    @Override
    public void registerCapabilities()
    {
        CapabilitySaoPlayerHandler.register();
    }

    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        registerCapabilities();
		GameRegistry.registerBlock(BlockInit.aincradCobbleVariation, SaoItemBlockMetaAbstract.class, "AincradCobble");
        GameRegistry.registerBlock(BlockInit.aincradGrassVariation, SaoItemBlockMetaAbstract.class, "AincradGrass");
        GameRegistry.registerBlock(BlockInit.aincradDirtVariation, SaoItemBlockMetaAbstract.class, "AincradDirt");

		
		/*GameRegistry.registerItem(ItemInit.healingCrystal, "Healing Crystal");
        GameRegistry.registerItem(ItemInit.antidoteCrystal, "Antidote Crystal");
        GameRegistry.registerItem(ItemInit.teleportCrystal, "Teleport Crystal");*/
        
		ItemInit.init();

    }

    @Override
    public void init(FMLInitializationEvent event) {
        SAOWorldProvider provider = new SAOWorldProvider();
    	//DimensionManager.registerProviderType(Reference.saoDimensionId, SAOWorldProvider.class, false);
        DimensionInit.init();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
    	registerEventHandlers();
    }
}
