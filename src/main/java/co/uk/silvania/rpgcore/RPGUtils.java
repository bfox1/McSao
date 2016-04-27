package co.uk.silvania.rpgcore;

import net.minecraft.client.Minecraft;

public class RPGUtils {
	
	//I have written this class, but everything is considered public domain. Feel free to copy-paste anything from here.
	//as it might come in handy in other mods - but not worth making a lib for.
	//Just comment above the method saying "Nicked this from Flenix" or something ;)
	
	//Get an int from the string provided.
	public static int parseInt(String s) {
		try {
			return Integer.parseInt("" + s);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}
	
	//Used in heavy debug mode, for anything spammy.
	public static void prtln(String str) {
		if (RPGCoreConfig.debugMode) {
			System.out.println("[MCSAO][RPGCore] " + str);
		}
	}
	
	//Used in verbose mode, for player levelups etc.
	public static void verbose(String str) {
		if (RPGCoreConfig.verbose) {
			System.out.println("[MCSAO][RPGCore] " + str);
		}
	}

}
