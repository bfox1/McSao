package io.github.bfox1.SwordArtOnline.common.items.crystals;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class SaoCrystalTeleport extends SaoCrystalAbstract{
	
	public SaoCrystalTeleport()
	{
		super();
		this.setUnlocalizedName("TeleportCrystal");
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world,
													EntityPlayer player, EnumHand hand) {
		//ToDo: add selection of teleport location + teleporting functionality
		return super.onItemRightClick(itemStack, world, player, hand);
	}
	
}
