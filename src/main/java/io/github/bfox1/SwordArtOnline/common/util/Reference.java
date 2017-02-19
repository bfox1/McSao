package io.github.bfox1.SwordArtOnline.common.util;


import net.minecraft.block.Block;

import java.io.File;
import java.nio.file.Paths;

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
    public static final String RESOURCE_DIRECTORY = Paths.get(".").toAbsolutePath().normalize().toString().replaceAll("run", "")+
            File.separatorChar+"src"+File.separatorChar+"main"+File.separatorChar+"resources"+File.separatorChar;
    public static final String SAO_ASSETS_DIRECTORY = RESOURCE_DIRECTORY+"assets"+File.separatorChar+"sao"+File.separatorChar;
    public static final String RPG_CORE_ASSETS_DIRECTORY = RESOURCE_DIRECTORY+"assets"+File.separatorChar+"rpgcore"+File.separatorChar;
    public static final String GUI_TEXTURES_DIRECTORY = RPG_CORE_ASSETS_DIRECTORY+"textures.gui"+File.separatorChar;
    public static final String SCHEMATICS_DIRECTORY = SAO_ASSETS_DIRECTORY+"schematics"+File.separatorChar;
    public static final String TEXTURES_DIRECTORY = SAO_ASSETS_DIRECTORY+"textures"+File.separatorChar;

    public static final int SAO_DIMENSION_ID = 4;
}
