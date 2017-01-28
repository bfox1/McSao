package io.github.bfox1.SwordArtOnline.common.world;

import io.github.bfox1.SwordArtOnline.common.util.Reference;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Earbuds on 4/12/2016.
 */
public class SAOWorldProvider extends WorldProvider {
		

		
		@Override
		public boolean canRespawnHere() {
			return true;
		}
		

		public String getDimensionName() {
			return "Aincrad";
		}


		public String getInternalNameSuffix() {
			return "_sao";
		}
		
		@SideOnly(Side.CLIENT)
	    public float getCloudHeight() {
			return 256.0F;
	    }

	@Override
	public DimensionType getDimensionType() {
		return DimensionType.register(getDimensionName(), getInternalNameSuffix(), Reference.saoDimensionId, SAOWorldProvider.class, true);
	}
}