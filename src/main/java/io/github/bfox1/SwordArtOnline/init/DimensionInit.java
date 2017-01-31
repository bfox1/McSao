package io.github.bfox1.SwordArtOnline.init;

import io.github.bfox1.SwordArtOnline.common.SwordArtOnline;
import io.github.bfox1.SwordArtOnline.common.util.Reference;
import io.github.bfox1.SwordArtOnline.common.world.SAOWorldProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Ian on 1/31/2017.
 */
public class DimensionInit
{
    public static final DimensionType AINCRAD_DIMENSION = DimensionType.register("Aincrad", "_sao", 4, SAOWorldProvider.class, false);

    public static void init()
    {
        registerDimensions();
    }

    private static void registerDimensions() {
        DimensionManager.registerDimension(Reference.saoDimensionId, AINCRAD_DIMENSION);
    }

}
