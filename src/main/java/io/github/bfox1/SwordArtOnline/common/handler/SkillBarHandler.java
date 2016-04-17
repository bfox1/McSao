package io.github.bfox1.SwordArtOnline.common.handler;

//code referenced from https://github.com/kulttuuri/quick-hotbar-mod/blob/1.8.9/src/main/java/com/kulttuuri/quickhotbar/QuickHotbarEventHandler.java#L270

import io.github.bfox1.SwordArtOnline.common.proxy.ClientProxy;
import io.github.bfox1.SwordArtOnline.init.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class SkillBarHandler {
	private static final ResourceLocation WIDGETS = new ResourceLocation("textures/gui/widgets.png");
	private static final RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();

	private static boolean skillBarOn = false;

	private static boolean isNumberKeyDown = false;
	private static boolean isSkillBarButtonDown = false;

	private int getNumberKeyIsDown() {
		if (Keyboard.isKeyDown(1))
			return 1;
		else if (Keyboard.isKeyDown(2))
			return 2;
		else if (Keyboard.isKeyDown(3))
			return 3;
		else if (Keyboard.isKeyDown(4))
			return 4;
		else if (Keyboard.isKeyDown(5))
			return 5;
		else if (Keyboard.isKeyDown(6))
			return 6;
		else if (Keyboard.isKeyDown(7))
			return 7;
		else if (Keyboard.isKeyDown(8))
			return 8;
		else if (Keyboard.isKeyDown(9))
			return 9;
		else
			return 0;
	}

	@SubscribeEvent
	public void clientJoinedEvent(ClientConnectedToServerEvent event) {
		skillBarOn = false;
	}

	@SubscribeEvent
	public void handleKeyBoardPresses(RenderGameOverlayEvent.Pre event) {
		if (Minecraft.getMinecraft().currentScreen != null)
			return;

		if (!ClientProxy.settings.SKILL_BAR_KEY.isKeyDown())
			isSkillBarButtonDown = false;
		if (getNumberKeyIsDown() == 0)
			isNumberKeyDown = false;
		
		if(!isSkillBarButtonDown && ClientProxy.settings.SKILL_BAR_KEY.isKeyDown())
		{
			isSkillBarButtonDown = true;
		}
	}
	
	@SubscribeEvent
	public void hideInGameHotbarWhenSkillBarIsOpen(RenderGameOverlayEvent.Pre event)
	{
		if(isSkillBarButtonDown)
		{
			if(event.type == event.type.HOTBAR)
			{
				event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if(isSkillBarButtonDown){//change this to be true when the skillbar is open
        	if(getNumberKeyIsDown() != 0)
        	{
        		 if (Keyboard.getEventKeyState()) {
        			 int kb = Keyboard.getEventKey();
        			 for (KeyBinding b : Minecraft.getMinecraft().gameSettings.keyBindsHotbar) {
        				 if (kb == b.getKeyCode() && b.isPressed()) {
        					 KeyBinding.setKeyBindState(kb, false);
        					 //ToDo: activate skill placed in slot dictated by b.getKeyCode()
        					 System.out.println("Activated skill: " + b.getKeyCode());
        					 return;
        				 }
        			 }
        		}        		
        	}
        }
    }

	@SubscribeEvent
	public void handleGameUpdate(RenderGameOverlayEvent.Pre event) {
		//if (!SwordArtOnline.settings.SKILL_BAR_TOGGLE) {
			if (ClientProxy.settings.SKILL_BAR_KEY.isKeyDown()) {
				if (Minecraft.getMinecraft().ingameGUI == null || !Minecraft.getMinecraft().inGameHasFocus)
					return;
				Minecraft mc = Minecraft.getMinecraft();
				mc.gameSettings.heldItemTooltips = false;
				
				ScaledResolution res = new ScaledResolution(mc);
				int width = res.getScaledWidth();
				int height = res.getScaledHeight();
				renderHotbar(mc.ingameGUI, width, height);
				
			} else if (!ClientProxy.settings.SKILL_BAR_KEY.isKeyDown()) {
				Minecraft.getMinecraft().gameSettings.heldItemTooltips = true;
			}
		//} else {
		//	if(SwordArtOnline.settings.SKILL_BAR_KEY.isPressed()) skillBarOn = !skillBarOn;
		//}
	}

	/**
	 * Renders the hotbar. Code copied from GuiIngameForge.
	 * 
	 * @param gui
	 * @param width
	 * @param height

	 * @since 1.7.2
	 */
	private void renderHotbar(GuiIngame gui, int width, int height) {
		Minecraft mc = Minecraft.getMinecraft();
		mc.mcProfiler.startSection("actionBar");

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
		mc.renderEngine.bindTexture(WIDGETS);
		
		InventoryPlayer inv = mc.thePlayer.inventory;
		GL11.glPushMatrix();
		GL11.glTranslatef(1f, 1f, 100f);
		gui.drawTexturedModalRect(width / 2 - 92, height - 23, 0, 0, 182, 22);
		// gui.drawTexturedModalRect(width / 2 - 91 - 1 + inv.currentItem * 20, height - yPosFromBottom - 1, 0, 22, 24, 22); 
		//Renders the selected slot, we don't want that.
		GL11.glPopMatrix();

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.enableGUIStandardItemLighting();

		// modify this to render each skill's icon
		for (int i = 0; i < 9; ++i) {
			int x = width / 2 - 90 + i * 20 + 2;
			int z = height - 23;
			int renderSlot = i;
			renderSlot(renderSlot, x, z);
		}

		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		mc.mcProfiler.endSection();
	}

	// modify this to get the image of the skill at the specified slot.
	/**
	 * Renders the specified item of the inventory slot at the specified
	 * location. Args: slot, x, y, partialTick. Code copied from GuiInGame
	 * class.
	 * 
	 * @since 1.7.2
	 */
	private void renderSlot(int par1, int par2, int par3) {
		Minecraft mc = Minecraft.getMinecraft();
		//ItemStack itemstack = mc.thePlayer.inventory.mainInventory[par1];
		ItemStack itemstack = new ItemStack(ItemInit.healingCrystal, 2);
		
		if (itemstack != null) {
			float f1 = (float) itemstack.animationsToGo - 0;

			if (f1 > 0.0F) {
				GL11.glPushMatrix();
				float f2 = 1.0F + f1 / 5.0F;
				GL11.glTranslatef((float) (par2 + 8), (float) (par3 + 12), 0.0F);
				GL11.glScalef(1.0F / f2, (f2 + 1.0F) / 2.0F, 1.0F);
				GL11.glTranslatef((float) (-(par2 + 8)), (float) (-(par3 + 12)), 0.0F);
			}
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
			// itemRenderer.renderItemAndEffectIntoGUI(mc.fontRendererObj, mc.getTextureManager(), itemstack, par2, par3+1);
			itemRenderer.renderItemAndEffectIntoGUI(itemstack, par2, par3 + 4);

			if (f1 > 0.0F) {
				GL11.glPopMatrix();
			}

			// itemRenderer.renderItemOverlayIntoGUI(mc.fontRendererObj, mc.getTextureManager(), itemstack, par2, par3+1);
			itemRenderer.renderItemOverlayIntoGUI(mc.fontRendererObj, itemstack, par2, par3 + 4, null);
		}
	}

}
