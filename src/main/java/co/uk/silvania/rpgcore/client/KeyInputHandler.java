package co.uk.silvania.rpgcore.client;

import co.uk.silvania.rpgcore.RPGCore;
import co.uk.silvania.rpgcore.network.OpenGuiPacket;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyInputHandler {
	
	Minecraft mc = Minecraft.getMinecraft();
	
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if (KeyBindings.openSkills.isPressed()) {
			//TODO check if player is in Aincrad and if not, whether they can open it based on config.
			//RPGCore.network.sendToServer(new OpenGuiPacket(0));
			mc.thePlayer.openGui(RPGCore.instance, 5, mc.theWorld, (int) mc.thePlayer.posX, (int) mc.thePlayer.posY, (int) mc.thePlayer.posZ);
		}
	}
}
