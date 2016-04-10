package io.github.bfox1.SwordArtOnline.common.util;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.lwjgl.input.Keyboard;

public class Settings {
	/** Key that needs to be pressed to activate skill use */
	public KeyBinding SKILL_BAR_KEY = new KeyBinding("key.skillBar", Keyboard.KEY_R, "key.categories." + Reference.MODID);
	/** Should the skill bar be a toggle? */
	public boolean SKILL_BAR_TOGGLE = false;
	
	private Configuration config;
	private Property skillBarKey;
	private Property skillBarToggle;
	
	public Settings(FMLPreInitializationEvent event){
		config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		
		skillBarToggle = config.get("general", "skillBarToggle", false, "Controls whether pulling up the skill bar is a toggle");
		
		
		//Actually set the settings/config stuff.
		SKILL_BAR_TOGGLE = skillBarToggle.getBoolean(false);
		
		//Register the keybindings
		ClientRegistry.registerKeyBinding(SKILL_BAR_KEY);
		
		config.save();
	}
	
}
