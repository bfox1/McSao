package io.github.bfox1.SwordArtOnline.common.util;

import io.github.bfox1.SwordArtOnline.common.world.biome.SAOBiomeGenerator;
import net.minecraft.world.biome.BiomeGenBase;

/**
 * Created by bfox1 on 4/2/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 *
 * Simple Reference for all information regarding the Mod.
 */
public class Reference
{
    public static final String MODID = "sao";
    public static final String NAME = "Sword Art Online";
    public static final String VERSION = "0.1-Dev";
    public static final String CLIENTPROXY = "io.github.bfox1.SwordArtOnline.common.proxy.ClientProxy";
    public static final String SERVERPROXY = "io.github.bfox1.SwordArtOnline.common.proxy.ServerProxy";
    public static final String GUI_FACTORY = "io.github.bfox1.SwordArtOnline.client.gui.SaoGuiFactory";
    
    public static final BiomeGenBase saoBiome = new SAOBiomeGenerator(44, true);
    public static final int saoDimensionId = 4;
}
