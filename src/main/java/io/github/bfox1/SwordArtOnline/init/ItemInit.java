package io.github.bfox1.SwordArtOnline.init;

import io.github.bfox1.SwordArtOnline.common.items.crystals.SaoCrystalAbstract;
import io.github.bfox1.SwordArtOnline.common.items.crystals.SaoCrystalAntidote;
import io.github.bfox1.SwordArtOnline.common.items.crystals.SaoCrystalHealing;
import io.github.bfox1.SwordArtOnline.common.items.crystals.SaoCrystalTeleport;

public class ItemInit {
	public static final SaoCrystalAbstract healingCrystal;
	public static final SaoCrystalAbstract antidoteCrystal;
	public static final SaoCrystalAbstract teleportCrystal;
	static
	{
		healingCrystal = new SaoCrystalHealing();
		antidoteCrystal = new SaoCrystalAntidote();
		teleportCrystal = new SaoCrystalTeleport();
	}
}
