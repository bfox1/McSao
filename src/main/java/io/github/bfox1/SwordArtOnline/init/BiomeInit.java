package io.github.bfox1.SwordArtOnline.init;

import io.github.bfox1.SwordArtOnline.common.SwordArtOnline;
import io.github.bfox1.SwordArtOnline.common.util.Reference;
import io.github.bfox1.SwordArtOnline.common.world.biome.SAOBiomeGenerator;
import net.minecraft.world.biome.Biome;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.registry.GameRegistry;


/**
 * Created by Ian on 1/31/2017.
 */
public class BiomeInit
{
    public static final SAOBiomeGenerator SAO_BIOME;

    static
    {
        SAO_BIOME = registerBiome(new SAOBiomeGenerator(new Biome.BiomeProperties("Aincrad")
                .setBaseHeight(-0.5F)
                .setHeightVariation(0F)
                .setTemperature(0.3F)
                .setRainfall(0.3F)
        ), new ResourceLocation(Reference.MODID, "sao_biome"), BiomeManager.BiomeType.WARM, Integer.MAX_VALUE, BiomeDictionary.Type.SPARSE, BiomeDictionary.Type.PLAINS);
    }

    public static void regsiterBiomes()
    {

    }

    private static <T extends Biome> T registerBiome(T biome, ResourceLocation biomeName, BiomeManager.BiomeType biomeType, int weight, BiomeDictionary.Type ... types)
    {
        GameRegistry.register(biome.setRegistryName(biomeName));
        BiomeDictionary.registerBiomeType(biome, types);
        BiomeManager.addBiome(biomeType, new BiomeManager.BiomeEntry(biome, weight));

        return biome;
    }
}
