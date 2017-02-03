package io.github.bfox1.SwordArtOnline.common.world;

import io.github.bfox1.SwordArtOnline.common.proxy.CommonProxy;
import io.github.bfox1.SwordArtOnline.common.world.biome.SAOBiomeProvider;
import io.github.bfox1.SwordArtOnline.init.BiomeInit;
import io.github.bfox1.SwordArtOnline.init.DimensionInit;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Eardbuds on 4/12/2016.
 * Modified by Ian on 2/3/2017.
 */
public class SAOWorldProvider extends WorldProvider {


    @Override
	public BiomeProvider getBiomeProvider()
	{
        return new SAOBiomeProvider(BiomeInit.SAO_BIOME);
	}

	@Override
    public IChunkGenerator createChunkGenerator()
    {
        return CommonProxy.SAO_WORLD_TYPE.getChunkGenerator(worldObj, "");
    }
		
    @Override
    public boolean canRespawnHere()
    {
        return true;
    }


    public String getDimensionName()
    {
        return "Aincrad";
    }

    @Override
    public DimensionType getDimensionType()
    {
        return DimensionInit.AINCRAD_DIMENSION;
    }

    public String getInternalNameSuffix()
    {
        return "_sao";
    }

    @SideOnly(Side.CLIENT)
    public float getCloudHeight()
    {
        return 256.0F;
    }


}