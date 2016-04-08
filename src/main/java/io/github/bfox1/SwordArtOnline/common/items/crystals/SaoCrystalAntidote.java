package io.github.bfox1.SwordArtOnline.common.items.crystals;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SaoCrystalAntidote extends SaoCrystalAbstract{
	
	public SaoCrystalAntidote()
	{
		super();
		this.setUnlocalizedName("AntidoteCrystal");
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world,
			EntityPlayer player) {
		player.removePotionEffect(17);//hunger
		player.removePotionEffect(18);//weakness
		player.removePotionEffect(19);//poison
		player.removePotionEffect(20);//wither
		return super.onItemRightClick(itemStack, world, player);
	}

}
