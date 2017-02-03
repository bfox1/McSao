package io.github.bfox1.SwordArtOnline.common.world;

import io.github.bfox1.SwordArtOnline.common.world.chunk.SAOChunkProvider;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;

public class SAOWorldType extends WorldType {

	public SAOWorldType(String name)
    {
		super(name);
	}

	@Override
    public net.minecraft.world.chunk.IChunkGenerator getChunkGenerator(World world, String generatorOptions)
    {
        return new SAOChunkProvider(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled(), generatorOptions);
    }
}
