package io.github.bfox1.SwordArtOnline.common.world;

import io.github.bfox1.SwordArtOnline.common.util.Reference;
import io.github.bfox1.SwordArtOnline.common.world.biome.SAOBiomeGenerator;
import io.github.bfox1.SwordArtOnline.common.world.chunk.SAOChunkProvider;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;

/**
 * Created by Earbuds on 4/12/2016.
 */
public class SAOWorldProvider extends WorldProvider {
		
	public BiomeGenBase biomeSao = new SAOBiomeGenerator(44, true);
		
		public void registerWorldChunkManager()
		{
			this.worldChunkMgr = new WorldChunkManagerHell(biomeSao, 0.1F);
			this.dimensionId = Reference.saoDimensionId;
		}
		
		public IChunkProvider createChunkGenerator() {
			return new SAOChunkProvider(worldObj, worldObj.getSeed(), worldObj.getWorldInfo().isMapFeaturesEnabled(), null);
		}
		
		@Override
		public boolean canRespawnHere() {
			return true;
		}
		
		@Override
		public String getDimensionName() {
			return "Aincrad";
		}

		@Override
		public String getInternalNameSuffix() {
			return "_sao";
		}
}