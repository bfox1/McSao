package co.uk.silvania.rpgcore.client;

import co.uk.silvania.rpgcore.RPGCore;
import co.uk.silvania.rpgcore.network.OpenGuiPacket;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyInputHandler {
	
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if (KeyBindings.openSkills.isPressed()) {
			RPGCore.network.sendToServer(new OpenGuiPacket(0));
		}
	}
}
