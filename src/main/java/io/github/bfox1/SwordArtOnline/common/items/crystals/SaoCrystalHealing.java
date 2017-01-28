package io.github.bfox1.SwordArtOnline.common.items.crystals;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SaoCrystalHealing extends SaoCrystalAbstract{
	
	public SaoCrystalHealing()
	{
		super();
		this.setUnlocalizedName("HealingCrystal");
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world,
			EntityPlayer player) {
		player.heal(4f);
		return super.onItemRightClick(itemStack, world, player);
	}
}
