package io.github.bfox1.SwordArtOnline.init;

import io.github.bfox1.SwordArtOnline.common.items.crystals.SaoCrystalAbstract;
import io.github.bfox1.SwordArtOnline.common.items.crystals.SaoCrystalAntidote;
import io.github.bfox1.SwordArtOnline.common.items.crystals.SaoCrystalHealing;
import io.github.bfox1.SwordArtOnline.common.items.crystals.SaoCrystalTeleport;
import io.github.bfox1.SwordArtOnline.common.items.swords.SaoSwordItem;
import io.github.bfox1.SwordArtOnline.common.util.RegisterUtility;
import io.github.bfox1.SwordArtOnline.skill.weaponskill.WeaponSkillType;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class ItemInit
{
	public static final SaoCrystalAbstract healingCrystal;
	public static final SaoCrystalAbstract antidoteCrystal;
	public static final SaoCrystalAbstract teleportCrystal;


	public static final SaoSwordItem[] saoSwordItem =
			{
					new SaoSwordItem(WeaponSkillType.ONE_HANDED_SWORD).setUnlocalizedName("AnnealBlade"),
					new SaoSwordItem(WeaponSkillType.ONE_HANDED_SWORD).setUnlocalizedName("DarkRepulser"),
					new SaoSwordItem(WeaponSkillType.ONE_HANDED_SWORD).setUnlocalizedName("Elucidator"),
					new SaoSwordItem(WeaponSkillType.ONE_HANDED_SWORD).setUnlocalizedName("GuiltyThorn"),
					new SaoSwordItem(WeaponSkillType.ONE_HANDED_SWORD).setUnlocalizedName("KagemitsuG45"),
					new SaoSwordItem(WeaponSkillType.ONE_HANDED_SWORD).setUnlocalizedName("Karakurenai"),
					new SaoSwordItem(WeaponSkillType.ONE_HANDED_SWORD).setUnlocalizedName("LambentLight"),
					new SaoSwordItem(WeaponSkillType.ONE_HANDED_SWORD).setUnlocalizedName("LiberatorSword"),
					new SaoSwordItem(WeaponSkillType.ONE_HANDED_SWORD).setUnlocalizedName("ObjectEraser"),

					new SaoSwordItem(WeaponSkillType.ONE_HANDED_AXE).setUnlocalizedName("MateChopper"),

					new SaoSwordItem(WeaponSkillType.ONE_HANDED_DAGGER).setUnlocalizedName("EbonDagger"),
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

	public SaoSwordItem getSwordIndex(int location)
	{
		if(saoSwordItem[location] != null)
		{
			return saoSwordItem[location];
		}
		return null;
	}

	public static SaoSwordItem getSwordByIndex(int location)
	{
		if(saoSwordItem[location] != null)
		{
			return saoSwordItem[location];
		}
		return null;
	}

	public static String getUnlocalizedItemName(int id)
	{
		Item item = Item.getItemById(id);
		return item.getUnlocalizedName();
	}

	public static LuaValue getLuaSword(int location)
	{
		if(saoSwordItem[location] != null)
		{
			return CoerceJavaToLua.coerce(saoSwordItem[location]);
		}
		return null;
	}

}
