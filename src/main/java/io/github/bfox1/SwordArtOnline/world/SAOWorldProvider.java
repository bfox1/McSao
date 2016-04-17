package io.github.bfox1.SwordArtOnline.world;

import io.github.bfox1.SwordArtOnline.common.util.Reference;
import io.github.bfox1.SwordArtOnline.init.BlockInit;
import io.github.bfox1.SwordArtOnline.world.biome.SAOBiomeGenerator;
import io.github.bfox1.SwordArtOnline.world.chunk.SAOChunkProvider;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.Height;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderEnd;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Earbuds on 4/12/2016.
 */
public class SAOWorldProvider extends WorldProvider {
		
		public void registerWorldChunkManager() {
			this.worldChunkMgr = new WorldChunkManagerHell(Reference.saoBiome, 0.1F);
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
		
		@SideOnly(Side.CLIENT)
	    public float getCloudHeight() {
			return 256.0F;
	    }
}