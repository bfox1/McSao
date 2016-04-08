package io.github.bfox1.SwordArtOnline.common.items.crystals;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import io.github.bfox1.SwordArtOnline.common.items.SaoItemAbstract;

public class SaoCrystalAbstract extends SaoItemAbstract{

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world,	EntityPlayer player) {
		itemStack.stackSize--;
		return super.onItemRightClick(itemStack, world, player);
	}
	
}
