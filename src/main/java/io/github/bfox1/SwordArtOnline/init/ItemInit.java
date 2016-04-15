package io.github.bfox1.SwordArtOnline.init;

import io.github.bfox1.SwordArtOnline.common.items.crystals.SaoCrystalAbstract;
import io.github.bfox1.SwordArtOnline.common.items.crystals.SaoCrystalAntidote;
import io.github.bfox1.SwordArtOnline.common.items.crystals.SaoCrystalHealing;
import io.github.bfox1.SwordArtOnline.common.items.crystals.SaoCrystalTeleport;
import io.github.bfox1.SwordArtOnline.common.items.swords.SaoSwordItem;
import io.github.bfox1.SwordArtOnline.common.util.RegisterUtility;
import io.github.bfox1.SwordArtOnline.skill.weaponskill.WeaponSkillType;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemInit
{
	public static final SaoCrystalAbstract healingCrystal;
	public static final SaoCrystalAbstract antidoteCrystal;
	public static final SaoCrystalAbstract teleportCrystal;


	public static final SaoSwordItem[] saoSwordItem =
			{
					new SaoSwordItem(WeaponSkillType.ONE_HANDED_SWORD).setUnlocalizedName("Anneal Blade"),
					new SaoSwordItem(WeaponSkillType.ONE_HANDED_SWORD).setUnlocalizedName("Dark Repulser"),
					new SaoSwordItem(WeaponSkillType.ONE_HANDED_SWORD).setUnlocalizedName("Elucidator"),
					new SaoSwordItem(WeaponSkillType.ONE_HANDED_SWORD).setUnlocalizedName("Guilty Thorn"),
					new SaoSwordItem(WeaponSkillType.ONE_HANDED_SWORD).setUnlocalizedName("KagemitsuG45"),
					new SaoSwordItem(WeaponSkillType.ONE_HANDED_SWORD).setUnlocalizedName("Karakurenai"),
					new SaoSwordItem(WeaponSkillType.ONE_HANDED_SWORD).setUnlocalizedName("Lambent Light"),
					new SaoSwordItem(WeaponSkillType.ONE_HANDED_SWORD).setUnlocalizedName("Liberator Sword"),
					new SaoSwordItem(WeaponSkillType.ONE_HANDED_SWORD).setUnlocalizedName("Object Eraser"),

					new SaoSwordItem(WeaponSkillType.ONE_HANDED_AXE).setUnlocalizedName("Mate Chopper"),

					new SaoSwordItem(WeaponSkillType.ONE_HANDED_DAGGER).setUnlocalizedName("Ebon Dagger"),
			};
	static
	{
		healingCrystal = new SaoCrystalHealing();
		antidoteCrystal = new SaoCrystalAntidote();
		teleportCrystal = new SaoCrystalTeleport();

	}


	public static void init()
	{
		for(SaoSwordItem item : saoSwordItem)
		{
			GameRegistry.registerItem(item, item.getUnlocalizedName().replaceAll("item.", ""));
		}
	}

	public static void register()
	{
		for(SaoSwordItem item : saoSwordItem)
		{
			if(item != null)
			RegisterUtility.registerItem(item, item.getUnlocalizedName().replaceAll("item.", ""));
		}
	}
}
