package io.github.bfox1.SwordArtOnline.common.items.crystals;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import io.github.bfox1.SwordArtOnline.common.items.SaoItemAbstract;

public class SaoCrystalAbstract extends SaoItemAbstract{

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {
		itemStack.stackSize--;
		return super.onItemRightClick(itemStack, world, player, hand);
	}
	
}
