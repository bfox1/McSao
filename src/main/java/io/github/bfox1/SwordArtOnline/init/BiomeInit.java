package io.github.bfox1.SwordArtOnline.init;

import io.github.bfox1.SwordArtOnline.common.SwordArtOnline;
import io.github.bfox1.SwordArtOnline.common.util.Reference;
import io.github.bfox1.SwordArtOnline.common.world.biome.SAOBiomeGenerator;
import net.minecraft.world.biome.Biome;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistry;


/**
 * Created by Ian on 1/31/2017.
 */
@GameRegistry.ObjectHolder(Reference.MODID)
public class BiomeInit
{
    public static final SAOBiomeGenerator SAO_BIOME = new SAOBiomeGenerator(new Biome.BiomeProperties("Aincrad")
            .setBaseHeight(-0.5F)
            .setHeightVariation(0F)
            .setTemperature(0.3F)
            .setRainfall(0.3F)
    );

    @Mod.EventBusSubscriber
    public static class RegistrationHandler
    {
        @SubscribeEvent
        public static void registerBiomes(RegistryEvent.Register<Biome> event)
        {
            final IForgeRegistry<Biome> registry = event.getRegistry();
            
            registerBiome(registry, SAO_BIOME, "Aincrad", BiomeManager.BiomeType.WARM, Integer.MAX_VALUE, BiomeDictionary.Type.SPARSE, BiomeDictionary.Type.PLAINS);
        }

        private static <T extends Biome> T registerBiome(IForgeRegistry<Biome> registry, T biome, String biomeName, BiomeManager.BiomeType biomeType, int weight, BiomeDictionary.Type... types)
        {
            GameRegistry.register(biome.setRegistryName(biomeName));
            BiomeDictionary.registerBiomeType(biome, types);
            BiomeManager.addBiome(biomeType, new BiomeManager.BiomeEntry(biome, weight));

            return biome;
        }
    }


}
