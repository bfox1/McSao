package io.github.bfox1.SwordArtOnline.init;

import io.github.bfox1.SwordArtOnline.common.util.Reference;
import io.github.bfox1.SwordArtOnline.common.world.SAOWorldProvider;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

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
        DimensionManager.registerDimension(Reference.SAO_DIMENSION_ID, AINCRAD_DIMENSION);
    }

}
