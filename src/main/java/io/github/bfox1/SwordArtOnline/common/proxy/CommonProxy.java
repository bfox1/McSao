package io.github.bfox1.SwordArtOnline.common.proxy;

import io.github.bfox1.SwordArtOnline.client.overlay.SaoHUD;
import io.github.bfox1.SwordArtOnline.common.blocks.itemblock.SaoItemBlockMetaAbstract;
import io.github.bfox1.SwordArtOnline.common.event.ForgeEventHandler;
import io.github.bfox1.SwordArtOnline.common.handler.SkillBarHandler;
import io.github.bfox1.SwordArtOnline.common.util.Reference;
import io.github.bfox1.SwordArtOnline.init.BlockInit;
import io.github.bfox1.SwordArtOnline.init.ItemInit;
import io.github.bfox1.SwordArtOnline.world.SAOWorldProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldType;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bfox1 on 4/2/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class CommonProxy implements SaoProxy
{
	
	public static final SaoHUD saoHud = new SaoHUD(Minecraft.getMinecraft());

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
    	MinecraftForge.EVENT_BUS.register(new SkillBarHandler());
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
    public void preInit(FMLPreInitializationEvent event) {
		GameRegistry.registerBlock(BlockInit.aincradCobbleVariation, SaoItemBlockMetaAbstract.class, "AincradCobble");
        GameRegistry.registerBlock(BlockInit.aincradGrassVariation, SaoItemBlockMetaAbstract.class, "AincradGrass");
        GameRegistry.registerBlock(BlockInit.aincradDirtVariation, SaoItemBlockMetaAbstract.class, "AincradDirt");
		
		GameRegistry.registerItem(ItemInit.healingCrystal, "Healing Crystal");
        GameRegistry.registerItem(ItemInit.antidoteCrystal, "Antidote Crystal");
        GameRegistry.registerItem(ItemInit.teleportCrystal, "Teleport Crystal");
        
		ItemInit.init();        
    }

    @Override
    public void init(FMLInitializationEvent event) {
    	DimensionManager.registerProviderType(Reference.saoDimensionId, SAOWorldProvider.class, false);
    	DimensionManager.registerDimension(Reference.saoDimensionId, Reference.saoDimensionId);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
    	registerEventHandlers();
    }
}
