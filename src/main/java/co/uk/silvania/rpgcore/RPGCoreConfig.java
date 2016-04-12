package co.uk.silvania.rpgcore;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class RPGCoreConfig {
	
	public static File rpgCoreConfigFile;
	
	public static String[] factions;
	
	public static void init(String configPath) {
		rpgCoreConfigFile = new File(configPath + "RPGCore.cfg");
		
		initConfig(rpgCoreConfigFile);
	}
	
	public static Configuration config;
	
	public static void initConfig(File configFile) {
		config = new Configuration(configFile);
						
		try {
			config.load();
			config.addCustomCategoryComment(Configuration.CATEGORY_GENERAL, "This is a test.");
			
			factions = config.getStringList("Factions", Configuration.CATEGORY_GENERAL, new String[] {"Caelum", "Mortalitas"}, "Add new factions for players to select.");
		} catch (Exception e) {
			System.out.println("### WARNING! RPGCore could not load it's config files! ###");
		} finally {
			if (config.hasChanged()) {
				config.save();
			}
		}
	}
}
