package co.uk.silvania.rpgcore.client.menugui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import co.uk.silvania.rpgcore.RPGCore;
import co.uk.silvania.rpgcore.client.guiparts.InvisibleButton;
import io.github.bfox1.SwordArtOnline.client.font.CustomFont;
import io.github.bfox1.SwordArtOnline.common.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.GuiScrollingList;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class MenuGui extends GuiScreen {
	
	int scroll;
	
	public static final ResourceLocation menuIcons  = new ResourceLocation(RPGCore.MODID, "textures/gui/menuicons.png");
	private CustomFont saoFont;
	
	GuiScrollingList list;
	
	InvisibleButton button1;
	InvisibleButton button2;
	InvisibleButton button3;
	InvisibleButton button4;
	InvisibleButton button5;
	
	InvisibleButton subButton1;
	InvisibleButton subButton2;
	InvisibleButton subButton3;
	InvisibleButton subButton4;
	
	boolean slotActive = false;
	
	int w;
	int h;
	
	String inv = "Inventory/Equipment";
	String friends = "Friends/Guild";
	String comms = "Communications";
	String maps = "Maps/Quests";
	String settings = "Settings/Main Menu";
	
	int invIcon = 36;
	int friendsIcon = 72;
	int commsIcon = 108;
	int mapsIcon = 144;
	int settingsIcon = 180;
	
	String slot1 = inv;
	String slot2 = friends;
	String slot3 = comms;
	String slot4 = maps;
	String slot5 = settings;
	
	int icon1 = invIcon;
	int icon2 = friendsIcon;
	int icon3 = commsIcon;
	int icon4 = mapsIcon;
	int icon5 = settingsIcon;
	
	public MenuGui() {
		super();
		Minecraft mc = Minecraft.getMinecraft();
		this.mc = mc;
		saoFont = new CustomFont(mc, new ResourceLocation(Reference.MODID, "textures/gui/font/SAOUITT-Regular.ttf"), 24);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseZ, float partialTick) {
		GL11.glEnable(GL11.GL_BLEND);
     	OpenGlHelper.glBlendFunc(770, 771, 1, 0);
     	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
     	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		//list.drawScreen(mouseX, mouseZ, partialTick);
		super.drawScreen(mouseX, mouseZ, partialTick);
		
		mc.getTextureManager().bindTexture(menuIcons);
		
		int mouseMoveY = Mouse.getDY();
		if (mouseMoveY > 0 && scroll < 20)  { scroll += 2; }
		if (mouseMoveY < 0 && scroll > -75) { scroll -= 2; }
		
		int button1Lit = (button1.isMouseOver() || slotActive) ? 0 : 36;
		int button2Lit = button2.isMouseOver() ? 0 : 36;
		int button3Lit = button3.isMouseOver() ? 0 : 36;
		int button4Lit = button4.isMouseOver() ? 0 : 36;
		int button5Lit = button5.isMouseOver() ? 0 : 36;
		
		this.drawTexturedModalRect(w - 18, h-18+scroll,  button1Lit, icon1, 36, 36);
		this.drawTexturedModalRect(w - 18, h+22+scroll,  button2Lit, icon2, 36, 36);
		this.drawTexturedModalRect(w - 18, h+62+scroll,  button3Lit, icon3, 36, 36);
		this.drawTexturedModalRect(w - 18, h+102+scroll, button4Lit, icon4, 36, 36);
		this.drawTexturedModalRect(w - 18, h+142+scroll, button5Lit, icon5, 36, 36);
		
		if (slot1.equals(inv) && slotActive) {
			draw3Buttons("Items", "Skills", "Equipment");
		}
		if (slot1.equals(friends) && slotActive) {
			draw3Buttons("Party", "Friends", "Guild");
		}
		if (slot1.equals(comms) && slotActive) {
			draw4Buttons("Befriend", "Trade", "Duel", "Marriage");
		}
		if (slot1.equals(maps) && slotActive) {
			draw3Buttons("Field Map", "Dungeon Map", "Quest");
		}
		if (slot1.equals(settings) && slotActive) {
			draw3Buttons("Options", "Help", "Log Out");
		}
		
		button1.yPosition = h-18+scroll;
		button2.yPosition = h+22+scroll;
		button3.yPosition = h+62+scroll;
		button4.yPosition = h+102+scroll;
		button5.yPosition = h+142+scroll;
	}
	
	public void draw3Buttons(String str1, String str2, String str3) {
		int button6Lit = subButton1.isMouseOver() ? 96 : 72;
		int button7Lit = subButton2.isMouseOver() ? 96 : 72;
		int button8Lit = subButton3.isMouseOver() ? 96 : 72;
		
		int icon1Lit = subButton1.isMouseOver() ? 16 : 0;
		int icon2Lit = subButton2.isMouseOver() ? 16 : 0;
		int icon3Lit = subButton3.isMouseOver() ? 16 : 0;
		
		subButton1.yPosition = h-38+scroll;
		subButton2.yPosition = h-12+scroll;
		subButton3.yPosition = h+14+scroll;
		
		subButton4.enabled = false;
		subButton4.visible = false;
		
		this.drawTexturedModalRect(w + 23, h-38+scroll, 72, button6Lit, 103, 24);
		this.drawTexturedModalRect(w + 23, h-12+scroll, 72, button7Lit, 103, 24);
		this.drawTexturedModalRect(w + 23, h+14+scroll, 72, button8Lit, 103, 24);
		
		this.drawTexturedModalRect(w + 39, h-34+scroll, iconX()+icon1Lit, iconZ(str1), 16, 16);
		this.drawTexturedModalRect(w + 39, h-8+scroll,  iconX()+icon2Lit, iconZ(str2), 16, 16);
		this.drawTexturedModalRect(w + 39, h+18+scroll, iconX()+icon3Lit, iconZ(str3), 16, 16);
		
		this.drawString(fontRendererObj, str1, w+59, h-30+scroll, 0xFFFFFFFF);
		this.drawString(fontRendererObj, str2, w+59, h-4+scroll,  0xFFFFFFFF);
		this.drawString(fontRendererObj, str3, w+59, h+22+scroll, 0xFFFFFFFF);
	}
	
	public void draw4Buttons(String str1, String str2, String str3, String str4) {
		int button6Lit = subButton1.isMouseOver() ? 96 : 72;
		int button7Lit = subButton2.isMouseOver() ? 96 : 72;
		int button8Lit = subButton3.isMouseOver() ? 96 : 72;
		int button9Lit = subButton4.isMouseOver() ? 96 : 72;
		
		int icon1Lit = subButton1.isMouseOver() ? 16 : 0;
		int icon2Lit = subButton2.isMouseOver() ? 16 : 0;
		int icon3Lit = subButton3.isMouseOver() ? 16 : 0;
		int icon4Lit = subButton4.isMouseOver() ? 16 : 0;
		
		subButton1.yPosition = h-51+scroll;
		subButton2.yPosition = h-25+scroll;
		subButton3.yPosition = h+1+scroll;
		subButton4.yPosition = h+27+scroll;
		
		this.drawTexturedModalRect(w + 23, h-51+scroll, 72, button6Lit, 103, 24);
		this.drawTexturedModalRect(w + 23, h-25+scroll, 72, button7Lit, 103, 24);
		this.drawTexturedModalRect(w + 23, h+1+scroll,  72, button8Lit, 103, 24);
		this.drawTexturedModalRect(w + 23, h+27+scroll, 72, button8Lit, 103, 24);
		
		this.drawTexturedModalRect(w + 39, h-47+scroll, iconX()+icon1Lit, iconZ(str1), 16, 16);
		this.drawTexturedModalRect(w + 39, h-21+scroll, iconX()+icon2Lit, iconZ(str2), 16, 16);
		this.drawTexturedModalRect(w + 39, h+5+scroll,  iconX()+icon3Lit, iconZ(str3), 16, 16);
		this.drawTexturedModalRect(w + 39, h+31+scroll, iconX()+icon4Lit, iconZ(str4), 16, 16);
		
		this.drawString(fontRendererObj, str1, w+58, h-43+scroll, 0xFFFFFFFF);
		this.drawString(fontRendererObj, str2, w+58, h-17+scroll, 0xFFFFFFFF);
		this.drawString(fontRendererObj, str3, w+58, h+9+scroll,  0xFFFFFFFF);
		this.drawString(fontRendererObj, str4, w+58, h+35+scroll, 0xFFFFFFFF);
	}

	public int iconX() {
		if (slot1.equals(inv)) { return 144; }
		if (slot1.equals(friends) || slot1.equals(maps)) { return 176; }
		if (slot1.equals(comms) || slot1.equals(settings)) { return 208; }

		return 0;
	}
	
	public int iconZ(String str) {
		if (str.equals("Items")) { return 0; }
		if (str.equals("Skills")) { return 16; }
		if (str.equals("Equipment")) { return 32; }
		
		if (str.equals("Party")) { return 0; }
		if (str.equals("Friends")) { return 16; }
		if (str.equals("Guild")) { return 32; }
		
		if (str.equals("Befriend")) { return 0; }
		if (str.equals("Trade")) { return 16; }
		if (str.equals("Duel")) { return 32; }
		if (str.equals("Marriage")) { return 48; }
		
		if (str.equals("Field Map")) { return 64; }
		if (str.equals("Dungeon Map")) { return 80; }
		if (str.equals("Quest")) { return 96; }
		
		if (str.equals("Options")) { return 64; }
		if (str.equals("Help")) { return 80; }
		if (str.equals("Log Out")) { return 96; }
		return 0;
	}
	
	@Override
	public void initGui() {
		scroll = 0;
		
		w = this.width/2;
		h = this.height/2;
		//this.list = new MenuScrollable(this, this.width, this.height, 256, 256);
		//this.list.registerScrollButtons(this.buttonList, 7, 8);
		
		button1 = new InvisibleButton(1, w-18, h-18, 36, 36, "");
		button2 = new InvisibleButton(2, w-18, h+22, 36, 36, "");
		button3 = new InvisibleButton(3, w-18, h+62, 36, 36, "");
		button4 = new InvisibleButton(4, w-18, h+102, 36, 36, "");
		button5 = new InvisibleButton(5, w-18, h+142, 36, 36, "");
		
		subButton1 = new InvisibleButton(6, w+23, h-38, 103, 24, "");
		subButton2 = new InvisibleButton(7, w+23, h-12, 103, 24, "");
		subButton3 = new InvisibleButton(8, w+23, h+14, 103, 24, "");
		subButton4 = new InvisibleButton(9, w+23, h+40, 103, 24, "");
		
		buttonList.add(button1);
		buttonList.add(button2);
		buttonList.add(button3);
		buttonList.add(button4);
		buttonList.add(button5);
		
		buttonList.add(subButton1);
		buttonList.add(subButton2);
		buttonList.add(subButton3);
		buttonList.add(subButton4);
	}
	
    @Override
    public void actionPerformed(GuiButton button) {
    	String storedSlot;
    	int storedIcon;
    	
    	switch(button.id) {
    	case 1:		
    		slotActive = true;
    		break;
    	case 2:
    		storedSlot = slot1;
    		slot1 = slot2;
    		slot2 = storedSlot;

    		storedIcon = icon1;
    		icon1 = icon2;
    		icon2 = storedIcon;
    		
    		slotActive = true;
    		break;
    	case 3:
    		storedSlot = slot1;
    		slot1 = slot3;
    		slot3 = storedSlot;

    		storedIcon = icon1;
    		icon1 = icon3;
    		icon3 = storedIcon;
    		
    		slotActive = true;
    		break;
    	case 4:
    		storedSlot = slot1;
    		slot1 = slot4;
    		slot4 = storedSlot;

    		storedIcon = icon1;
    		icon1 = icon4;
    		icon4 = storedIcon;
    		
    		slotActive = true;
    		break;
    	case 5:
    		storedSlot = slot1;
    		slot1 = slot5;
    		slot5 = storedSlot;

    		storedIcon = icon1;
    		icon1 = icon5;
    		icon5 = storedIcon;
    		
    		slotActive = true;
    		break;
    		
    	case 6:
    		if (slot1.equals(inv))      { items(); }
    		if (slot1.equals(friends))  { party(); }
    		if (slot1.equals(comms))    { befriend(); }
    		if (slot1.equals(maps))     { fieldMap(); }
    		if (slot1.equals(settings)) { options(); }
    		break;
    	case 7:
    		if (slot1.equals(inv))      { skills(); }
    		if (slot1.equals(friends))  { friend(); }
    		if (slot1.equals(comms))    { trade(); }
    		if (slot1.equals(maps))     { dungeonMap(); }
    		if (slot1.equals(settings)) { help(); }
    		break;
    	case 8:
    		if (slot1.equals(inv))      { equipment(); }
    		if (slot1.equals(friends))  { guild(); }
    		if (slot1.equals(comms))    { duel(); }
    		if (slot1.equals(maps))     { quests(); }
    		if (slot1.equals(settings)) { logout(); }
    		break;
    	case 9:
    		if (slot1.equals(comms)) { marriage(); }
    		break;
    	}
    }
    
    public void equipment() {}
    public void items() {}
    public void skills() {}
    
    public void party() {}
    public void friend() {}
    public void guild() {}
    
    public void befriend() {}
    public void trade() {}
    public void duel() {}
    public void marriage() {}
    
    public void fieldMap() {}
    public void dungeonMap() {}
    public void quests() {}
    
    public void options() { FMLCommonHandler.instance().showGuiScreen(new GuiOptions(this, mc.gameSettings)); }
    public void help() {}
    
    public void logout() {
    	System.out.println("Attempting logout");
    	boolean flag = this.mc.isIntegratedServerRunning();

    	System.out.println("Is it SP? " + flag);
        this.mc.theWorld.sendQuittingDisconnectingPacket();
        this.mc.loadWorld((WorldClient)null);
        
        System.out.println("Packet sent");
        if (flag) {
        	System.out.println("Showing main menu");
            this.mc.displayGuiScreen(new GuiMainMenu());
        } else {
        	System.out.println("Showing mutliplayer menu");
            this.mc.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
        }
        System.out.println("...Done?");
    }
	
	private int selected = -1;
	
	public void selectModIndex(int index) {
        this.selected = index;
    }

    public boolean modIndexSelected(int index) {
        return index == selected;
    }

    @Override
    public boolean doesGuiPauseGame() {
    	return false;
    }
}
