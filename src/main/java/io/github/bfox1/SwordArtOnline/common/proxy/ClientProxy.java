package io.github.bfox1.SwordArtOnline.common.proxy;

import io.github.bfox1.SwordArtOnline.client.overlay.SaoHUD;
import io.github.bfox1.SwordArtOnline.common.util.Reference;
import io.github.bfox1.SwordArtOnline.common.util.Settings;
import io.github.bfox1.SwordArtOnline.init.BlockInit;
import io.github.bfox1.SwordArtOnline.init.ItemInit;
import net.minecraft.client.Minecraft;
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
    public static final SaoHUD saoHud = new SaoHUD(Minecraft.getMinecraft());
    public static Settings settings;


    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
        settings = new Settings(event);
        OBJLoader.INSTANCE.addDomain(Reference.MODID);
        //TODO: Create Item Model registry here
        //ModelLoader.setCustomModelResourceLocation(ItemInit.healingCrystal, 0, Models.crystalHealing);
        //ModelLoader.setCustomModelResourceLocation(ItemInit.antidoteCrystal, 0, Models.crystalAntidote);
        //ModelLoader.setCustomModelResourceLocation(ItemInit.teleportCrystal, 0, Models.crystalTeleport);
        BlockInit.registerRenders();

    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
        ItemInit.register();
    }
    
    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
    	super.postInit(event);
    }


}
