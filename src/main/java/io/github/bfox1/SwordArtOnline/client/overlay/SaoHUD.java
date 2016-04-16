package io.github.bfox1.SwordArtOnline.client.overlay;

import io.github.bfox1.SwordArtOnline.common.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class SaoHUD extends Gui
{
	private static final ResourceLocation BACKGROUND = new ResourceLocation(Reference.MODID, "textures/hud/baseBackground.png");
	private static final ResourceLocation FOREGROUND = new ResourceLocation(Reference.MODID, "textures/hud/baseForeground.png");
	private static final ResourceLocation ORIGINAL = new ResourceLocation("textures/gui/icons.png");
	
	private static final double XPOS = 5;
	private static final double YPOS = 5;
	private static final double SCALE = 5;
	private final double widthBack, heightBack, widthFor, heightFor;
	
	private double currentPercent;
	
	private Minecraft mc;
	private ResourceLocation location = new ResourceLocation(Reference.MODID, "font/SwordArtOnline.otf");
	
	public SaoHUD(Minecraft mc)
	{
		super();
		
		this.mc = mc;
		
		widthBack = 700 / SCALE; // Change these to constants eventually
		heightBack = 122 / SCALE;
		widthFor = 408 / SCALE;
		heightFor = 34 / SCALE;
	}
	
	//@SubscribeEvent(priority = EventPriority.NORMAL)
	//public void onRenderHealthBar(RenderGameOverlayEvent.Pre event)
	//{
	//	if(event.type.equals(RenderGameOverlayEvent.ElementType.HEALTH) && event.isCancelable() /*&& mc.thePlayer.worldObj.provider.getDimensionId() == Reference.DIMENSIONID*/)
	//	{
		//	event.setCanceled(true);
	//		drawBase();
	//		drawBar(mc.thePlayer.getHealth() / mc.thePlayer.getMaxHealth());
	//	}
	//}
	
	public final void drawBar(double percent)
	{		
		GL11.glPushMatrix();
		mc.renderEngine.bindTexture(FOREGROUND);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor3d(2 * (1 - percent), 2 * percent, 0);
		
		Tessellator t = Tessellator.getInstance();
		WorldRenderer w = t.getWorldRenderer();
		w.begin(7, DefaultVertexFormats.POSITION_TEX);
		w.pos(XPOS + 241 / SCALE, YPOS + 27 / SCALE, this.zLevel).tex(0, 0).endVertex();;
        w.pos(XPOS + 241 / SCALE, YPOS + 27 / SCALE + heightFor, this.zLevel).tex(0, 1).endVertex();;
        w.pos(XPOS + 241 / SCALE + (widthFor * currentPercent), YPOS + 27 / SCALE + heightFor, this.zLevel).tex(currentPercent, 1).endVertex();;
        w.pos(XPOS + 241 / SCALE + (widthFor * currentPercent), YPOS + 27 / SCALE, this.zLevel).tex(currentPercent, 0).endVertex();
        t.draw();
        
        
        GL11.glColor3f(255, 255, 255);
        GL11.glPopMatrix();
        
        mc.renderEngine.bindTexture(ORIGINAL);
        
        if(currentPercent != percent)
        	currentPercent -= (double)(currentPercent - percent) / 15;
	}
	
	public final void drawBase()
	{		
		GL11.glPushMatrix();
		mc.renderEngine.bindTexture(BACKGROUND);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		
		Tessellator t = Tessellator.getInstance();
		WorldRenderer w = t.getWorldRenderer();
		w.begin(7, DefaultVertexFormats.POSITION_TEX);
		w.pos(XPOS, YPOS, this.zLevel).tex(0, 0).endVertex();;
        w.pos(XPOS, YPOS + heightBack, this.zLevel).tex(0, 1).endVertex();;
        w.pos(XPOS + widthBack, YPOS + heightBack, this.zLevel).tex(1, 1).endVertex();;
        w.pos(XPOS + widthBack, YPOS, this.zLevel).tex(1, 0).endVertex();
        t.draw();
        
        GL11.glPopMatrix();
        
        mc.renderEngine.bindTexture(ORIGINAL);

	}


}
