package co.uk.silvania.rpgcore;

import co.uk.silvania.rpgcore.client.menugui.MenuGui;
import co.uk.silvania.rpgcore.client.skillgui.SkillListGui;
import co.uk.silvania.rpgcore.client.skillgui.SkillSelectGui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	public GuiHandler() {}
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		switch (id) {
			case 0: {
				return new SkillsContainer(player);
			}
		}
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		System.out.println("Open gui: " + id);
		switch (id) {
			case 0: {
				return new SkillSelectGui(new SkillsContainer(player));
			}
			case 1: {
				return null; //skill config gui in RPGCore, but that's disabled here. Leaving to avoid ID confusion between versions.
			}
			case 2: {
				return new SkillListGui();
			}
			case 3: {
				//return new PlayerConfig();
			}
			case 4: {
				return null; //GlobalLevelConfig in RPGCore. Again, disbaled.
			}
			case 5: {
				return new MenuGui();
			}
		}
		return null;
	}
}
