package io.github.bfox1.SwordArtOnline.common.items.crystals;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class SaoCrystalAntidote extends SaoCrystalAbstract{
	
	public SaoCrystalAntidote()
	{
		super();
		this.setUnlocalizedName("AntidoteCrystal");
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world,
													EntityPlayer player, EnumHand hand)
	{
		player.removePotionEffect(Potion.getPotionById(17));//hunger
		player.removePotionEffect(Potion.getPotionById(18));//weakness
		player.removePotionEffect(Potion.getPotionById(19));//poison
		player.removePotionEffect(Potion.getPotionById(20));//wither
		return super.onItemRightClick(itemStack, world, player, hand);
	}

}
