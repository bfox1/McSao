package io.github.bfox1.SwordArtOnline.common.proxy;

import io.github.bfox1.SwordArtOnline.client.overlay.SaoHUD;
import io.github.bfox1.SwordArtOnline.common.blocks.itemblock.SaoItemBlockMetaAbstract;
import io.github.bfox1.SwordArtOnline.init.BlockInit;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
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

    private static final Map<String, NBTTagCompound> extendedEntityData = new HashMap<String, NBTTagCompound>();

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
    	MinecraftForge.EVENT_BUS.register(new SaoHUD(Minecraft.getMinecraft()));
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
    public void preInit(FMLPreInitializationEvent event)
    {
        GameRegistry.registerBlock(BlockInit.aincradCobbleVariation, SaoItemBlockMetaAbstract.class, "AincradCobble");
    }

    @Override
    public void init(FMLInitializationEvent event)
    {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
    	registerEventHandlers();
    }
}
