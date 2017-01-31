package io.github.bfox1.SwordArtOnline.common.items.crystals;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class SaoCrystalHealing extends SaoCrystalAbstract{
	
	public SaoCrystalHealing()
	{
		super();

	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world,
													EntityPlayer player, EnumHand hand) {
		player.heal(4f);
		return super.onItemRightClick(itemStack, world, player, hand);
	}
}
