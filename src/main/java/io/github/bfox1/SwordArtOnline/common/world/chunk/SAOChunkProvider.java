package io.github.bfox1.SwordArtOnline.common.world.chunk;

import io.github.bfox1.SwordArtOnline.common.util.Cuboid;
import io.github.bfox1.SwordArtOnline.common.util.DistanceHelper;
import io.github.bfox1.SwordArtOnline.common.util.DungeonSchematic;
import io.github.bfox1.SwordArtOnline.common.util.Point3D;
import io.github.bfox1.SwordArtOnline.common.world.SAODungeonBuilder;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.structure.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created and fixed by Dradgit again on 2/3/2017.
 */
public class SAOChunkProvider implements IChunkGenerator
{
	protected static final IBlockState STONE = Blocks.STONE.getDefaultState();
	private final Random rand;
	private NoiseGeneratorOctaves minLimitPerlinNoise;
	private NoiseGeneratorOctaves maxLimitPerlinNoise;
	private NoiseGeneratorOctaves mainPerlinNoise;
	private NoiseGeneratorPerlin surfaceNoise;
	public NoiseGeneratorOctaves scaleNoise;
	public NoiseGeneratorOctaves depthNoise;
	public NoiseGeneratorOctaves forestNoise;
	private final World worldObj;
	private final boolean mapFeaturesEnabled;
	private final WorldType terrainType;
	private final double[] heightMap;
	private final float[] biomeWeights;
	private ChunkProviderSettings settings;
	private IBlockState oceanBlock = Blocks.AIR.getDefaultState();
	private IBlockState topBlock = Blocks.AIR.getDefaultState();
    private IBlockState dirtBlock = Blocks.AIR.getDefaultState();
    private IBlockState stoneBlock = Blocks.AIR.getDefaultState();
    private IBlockState cobbleBlock = Blocks.AIR.getDefaultState();
	private double[] depthBuffer = new double[256];
	private MapGenBase caveGenerator = new MapGenCaves();
	private MapGenStronghold strongholdGenerator = new MapGenStronghold();
	private MapGenVillage villageGenerator = new MapGenVillage();
	private MapGenMineshaft mineshaftGenerator = new MapGenMineshaft();
	private MapGenScatteredFeature scatteredFeatureGenerator = new MapGenScatteredFeature();
	private MapGenBase ravineGenerator = new MapGenRavine();
	private StructureOceanMonument oceanMonumentGenerator = new StructureOceanMonument();
	private Biome[] biomesForGeneration;
	double[] mainNoiseRegion;
	double[] minLimitRegion;
	double[] maxLimitRegion;
	double[] depthRegion;

	private int[][] dungeonOriginChunks = new int[3][2];

	public SAOChunkProvider(World worldIn, long seed, boolean mapFeaturesEnabledIn, String settings)
    {
		{
			//caveGenerator = net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(caveGenerator, net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.CAVE);
			//strongholdGenerator = (MapGenStronghold)net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(strongholdGenerator, net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.STRONGHOLD);
			//villageGenerator = (MapGenVillage)net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(villageGenerator, net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.VILLAGE);
			//mineshaftGenerator = (MapGenMineshaft)net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(mineshaftGenerator, net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.MINESHAFT);
			//scatteredFeatureGenerator = (MapGenScatteredFeature)net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(scatteredFeatureGenerator, net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.SCATTERED_FEATURE);
			//ravineGenerator = net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(ravineGenerator, net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.RAVINE);
			//oceanMonumentGenerator = (StructureOceanMonument)net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(oceanMonumentGenerator, net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.OCEAN_MONUMENT);
		}
		this.worldObj = worldIn;
		this.mapFeaturesEnabled = mapFeaturesEnabledIn;
		this.terrainType = worldIn.getWorldInfo().getTerrainType();
		this.rand = new Random(seed);
		this.minLimitPerlinNoise = new NoiseGeneratorOctaves(this.rand, 16);
		this.maxLimitPerlinNoise = new NoiseGeneratorOctaves(this.rand, 16);
		this.mainPerlinNoise = new NoiseGeneratorOctaves(this.rand, 8);
		this.surfaceNoise = new NoiseGeneratorPerlin(this.rand, 4);
		this.scaleNoise = new NoiseGeneratorOctaves(this.rand, 10);
		this.depthNoise = new NoiseGeneratorOctaves(this.rand, 16);
		this.forestNoise = new NoiseGeneratorOctaves(this.rand, 8);
		this.heightMap = new double[825];
		this.biomeWeights = new float[25];

		for (int i = -2; i <= 2; ++i)
		{
			for (int j = -2; j <= 2; ++j)
			{
				float f = 10.0F / MathHelper.sqrt_float((float)(i * i + j * j) + 0.2F);
				this.biomeWeights[i + 2 + (j + 2) * 5] = f;
			}
		}

		if (settings != null)
		{
			this.settings = ChunkProviderSettings.Factory.jsonToFactory(settings).build();
			//this.oceanBlock = this.settings.useLavaOceans ? Blocks.LAVA.getDefaultState() : Blocks.WATER.getDefaultState();
			worldIn.setSeaLevel(this.settings.seaLevel);
		}

		net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextOverworld ctx =
				new net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextOverworld(minLimitPerlinNoise, maxLimitPerlinNoise, mainPerlinNoise, surfaceNoise, scaleNoise, depthNoise, forestNoise);
		ctx = net.minecraftforge.event.terraingen.TerrainGen.getModdedNoiseGenerators(worldIn, this.rand, ctx);
		this.minLimitPerlinNoise = ctx.getLPerlin1();
		this.maxLimitPerlinNoise = ctx.getLPerlin2();
		this.mainPerlinNoise = ctx.getPerlin();
		this.surfaceNoise = ctx.getHeight();
		this.scaleNoise = ctx.getScale();
		this.depthNoise = ctx.getDepth();
		this.forestNoise = ctx.getForest();

		int counter = 0;
		while(counter < 3)
        {
            int chunkX = rand.nextInt(126)-63;
            int chunkZ = rand.nextInt(126)-63;
            Point3D chunkCornerNW = new Point3D(chunkX*16, 70, chunkZ*16);
            Point3D chunkCornerSE = new Point3D(chunkCornerNW.getX()+15, 70, chunkCornerNW.getZ()+15);
            Point3D chunkCornerSW = new Point3D(chunkCornerNW.getX(), 70, chunkCornerNW.getZ()+15);
            Point3D chunkCornerNE = new Point3D(chunkCornerNW.getX()+15, 70, chunkCornerNW.getZ());
            if(DistanceHelper.distance2D(chunkCornerNW.getX(), chunkCornerNW.getZ()) <= 983
                    && DistanceHelper.distance2D(chunkCornerSE.getX(), chunkCornerSE.getZ()) <= 983
                    && DistanceHelper.distance2D(chunkCornerSW.getX(), chunkCornerSW.getZ()) <= 983
                    && DistanceHelper.distance2D(chunkCornerNE.getX(), chunkCornerNE.getZ()) <= 983)
            {
                dungeonOriginChunks[counter][0] = chunkX;
                dungeonOriginChunks[counter][1] = chunkZ;
                counter++;
            }
        }
        System.out.println("Origin Chunks: "+"[[ 0 - "+Arrays.toString(dungeonOriginChunks[0])+"], 1 - "+Arrays.toString(dungeonOriginChunks[1])+"], 2 - "+Arrays.toString(dungeonOriginChunks[2])+"]]");
	}

	public void replaceBiomeBlocks(int x, int z, ChunkPrimer primer, Biome[] biomesIn)
	{
		if (!net.minecraftforge.event.ForgeEventFactory.onReplaceBiomeBlocks(this, x, z, primer, this.worldObj)) return;
		double d0 = 0.03125D;
		this.depthBuffer = this.surfaceNoise.getRegion(this.depthBuffer, (double)(x * 16), (double)(z * 16), 16, 16, 0.0625D, 0.0625D, 1.0D);

		for (int xIncrement = 0; xIncrement < 16; ++xIncrement)
		{
			for (int zIncrement = 0; zIncrement < 16; ++zIncrement)
			{
				Biome biome = biomesIn[zIncrement + xIncrement * 16];
				biome.genTerrainBlocks(this.worldObj, this.rand, primer, x * 16 + xIncrement, z * 16 + zIncrement, this.depthBuffer[xIncrement + zIncrement * 16]);
			}
		}
	}

	@Override
	public Chunk provideChunk(int x, int z)
    {
		this.rand.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
        ChunkPrimer chunkprimer = new ChunkPrimer();
        this.setBlocksInChunk(x, z, chunkprimer);
		// OLD this.biomesForGeneration = this.worldObj.getBiomeProvider().loadBlockGeneratorData(this.biomesForGeneration, x * 16, z * 16, 16, 16);
		this.biomesForGeneration = this.worldObj.getBiomeProvider().getBiomesForGeneration(this.biomesForGeneration, x * 16, z * 16, 16, 16);
        this.replaceBiomeBlocks(x, z, chunkprimer, this.biomesForGeneration);
        Chunk chunk = new Chunk(this.worldObj, chunkprimer, x, z);
        byte[] abyte = chunk.getBiomeArray();

        for(int i = 0; i < abyte.length; ++i) {
			abyte[i] = (byte)Biome.getIdForBiome(this.biomesForGeneration[i]);
        }

        for(int i = 0; i < dungeonOriginChunks.length; i++)
        {
            int chunkX = dungeonOriginChunks[i][0];
            int chunkZ = dungeonOriginChunks[i][1];
            if(x == chunkX && z == chunkZ)
            {
                ArrayList<DungeonSchematic> pieceList = new ArrayList<>();
                pieceList.add(new DungeonSchematic("BasicCrossroads"));
                SAODungeonBuilder dungeonBuilder = new SAODungeonBuilder(pieceList, 10, new Cuboid(100, 100, 20 ),new Point3D(x*16, 70, z*16 ));
                dungeonBuilder.buildDungeon(worldObj);
            }
        }

        chunk.generateSkylightMap();
        chunk.resetRelightChecks();
        return chunk;
	}

	@Override
	public boolean generateStructures(Chunk chunkIn, int x, int z)
	{
		return false;
	}

	@Override
	public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
    {
		return null;
	}

	private void generateHeightmap(int xOffset, int yOffset, int zOffset)
	{
		this.depthRegion = this.depthNoise.generateNoiseOctaves(this.depthRegion, xOffset, zOffset, 5, 5, (double)this.settings.depthNoiseScaleX, (double)this.settings.depthNoiseScaleZ, (double)this.settings.depthNoiseScaleExponent);
		float f = this.settings.coordinateScale;
		float f1 = this.settings.heightScale;
		this.mainNoiseRegion = this.mainPerlinNoise.generateNoiseOctaves(this.mainNoiseRegion, xOffset, yOffset, zOffset, 5, 33, 5, (double)(f / this.settings.mainNoiseScaleX), (double)(f1 / this.settings.mainNoiseScaleY), (double)(f / this.settings.mainNoiseScaleZ));
		this.minLimitRegion = this.minLimitPerlinNoise.generateNoiseOctaves(this.minLimitRegion, xOffset, yOffset, zOffset, 5, 33, 5, (double)f, (double)f1, (double)f);
		this.maxLimitRegion = this.maxLimitPerlinNoise.generateNoiseOctaves(this.maxLimitRegion, xOffset, yOffset, zOffset, 5, 33, 5, (double)f, (double)f1, (double)f);
		int i = 0;
		int j = 0;

		for (int k = 0; k < 5; ++k)
		{
			for (int l = 0; l < 5; ++l)
			{
				float f2 = 0.0F;
				float f3 = 0.0F;
				float f4 = 0.0F;
				int i1 = 2;
				Biome biome = this.biomesForGeneration[k + 2 + (l + 2) * 10];

				for (int j1 = -2; j1 <= 2; ++j1)
				{
					for (int k1 = -2; k1 <= 2; ++k1)
					{
						Biome biome1 = this.biomesForGeneration[k + j1 + 2 + (l + k1 + 2) * 10];
						float f5 = this.settings.biomeDepthOffSet + biome1.getBaseHeight() * this.settings.biomeDepthWeight;
						float f6 = this.settings.biomeScaleOffset + biome1.getHeightVariation() * this.settings.biomeScaleWeight;

						if (this.terrainType == WorldType.AMPLIFIED && f5 > 0.0F)
						{
							f5 = 1.0F + f5 * 2.0F;
							f6 = 1.0F + f6 * 4.0F;
						}

						float f7 = this.biomeWeights[j1 + 2 + (k1 + 2) * 5] / (f5 + 2.0F);

						if (biome1.getBaseHeight() > biome.getBaseHeight())
						{
							f7 /= 2.0F;
						}

						f2 += f6 * f7;
						f3 += f5 * f7;
						f4 += f7;
					}
				}

				f2 = f2 / f4;
				f3 = f3 / f4;
				f2 = f2 * 0.9F + 0.1F;
				f3 = (f3 * 4.0F - 1.0F) / 8.0F;
				double d7 = this.depthRegion[j] / 8000.0D;

				if (d7 < 0.0D)
				{
					d7 = -d7 * 0.3D;
				}

				d7 = d7 * 3.0D - 2.0D;

				if (d7 < 0.0D)
				{
					d7 = d7 / 2.0D;

					if (d7 < -1.0D)
					{
						d7 = -1.0D;
					}

					d7 = d7 / 1.4D;
					d7 = d7 / 2.0D;
				}
				else
				{
					if (d7 > 1.0D)
					{
						d7 = 1.0D;
					}

					d7 = d7 / 8.0D;
				}

				++j;
				double d8 = (double)f3;
				double d9 = (double)f2;
				d8 = d8 + d7 * 0.2D;
				d8 = d8 * (double)this.settings.baseSize / 8.0D;
				double d0 = (double)this.settings.baseSize + d8 * 4.0D;

				for (int l1 = 0; l1 < 33; ++l1)
				{
					double d1 = ((double)l1 - d0) * (double)this.settings.stretchY * 128.0D / 256.0D / d9;

					if (d1 < 0.0D)
					{
						d1 *= 4.0D;
					}

					double d2 = this.minLimitRegion[i] / (double)this.settings.lowerLimitScale;
					double d3 = this.maxLimitRegion[i] / (double)this.settings.upperLimitScale;
					double d4 = (this.mainNoiseRegion[i] / 10.0D + 1.0D) / 2.0D;
					double d5 = MathHelper.denormalizeClamp(d2, d3, d4) - d1;
					if (l1 > 29)
					{
						double d6 = (double)((float)(l1 - 29) / 3.0F);
						d5 = d5 * (1.0D - d6) + -10.0D * d6;
					}
					this.heightMap[i] = d5;
					++i;
				}
			}
		}
	}

	@Override
	public void populate(int chunkX, int chunkZ) {
		BlockFalling.fallInstantly = true;
		int k = chunkX * 16;
		int l = chunkZ * 16;
		BlockPos blockpos = new BlockPos(k, 0, l);
		//Biome biomegenbase = this.worldObj.getBiomeGenForCoords(blockpos.add(16, 0, 16));
		this.rand.setSeed(this.worldObj.getSeed());
		long i1 = this.rand.nextLong() / 2L * 2L + 1L;
		long j1 = this.rand.nextLong() / 2L * 2L + 1L;
		this.rand.setSeed((long) chunkX * i1 + (long) chunkZ * j1 ^ this.worldObj.getSeed());
		boolean flag = false;

		net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(true, this, this.worldObj, this.rand, chunkX, chunkZ, flag);
		//MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(thisChunkProvider, worldObj, rand, chunkX, chunkZ, flag));

		if (k == 0 && l == 0) {
            /*DungeonSchematic test1 = new DungeonSchematic("BlankShape-1", new int[][] {{2,3,4}, {5,6,7}}, new int[][] {{9, 5}, {15, 4}});
            test1.placeSchematic(worldObj, 0, 90, 0); //TODO: These here caused errors.
            DungeonSchematic test2 = new DungeonSchematic("BasicCrossroads");
            test2.placeSchematic(worldObj, 0, 67, 0); //TODO: Same here
            System.out.println(test2.getConnections());*/
		}

		/*
		int k1;
		int l1;
		int i2;

		if(biomegenbase != Biome.desert && biomegenbase != Biome.desertHills && !flag && this.rand.nextInt(4) == 0
				&& TerrainGen.populate(thisChunkProvider, worldObj, rand, chunkX, chunkZ, flag, LAKE))
		{
			k1 = k + this.rand.nextInt(16) + 8;
			l1 = this.rand.nextInt(256);
			i2 = l + this.rand.nextInt(16) + 8;
			// (new WorldGenLakes(Blocks.water)).generate(this.worldObj, this.rand, k1, l1, i2);
			// Ian - Attempt to disable water. This works!
		}

		if(TerrainGen.populate(thisChunkProvider, worldObj, rand, chunkX, chunkZ, flag, LAVA) && !flag && this.rand.nextInt(8) == 0)
		{
			k1 = k + this.rand.nextInt(16) + 8;
			l1 = this.rand.nextInt(this.rand.nextInt(248) + 8);
			i2 = l + this.rand.nextInt(16) + 8;

			if(l1 < 63 || this.rand.nextInt(10) == 0)
			{
				//(new WorldGenLakes(Blocks.lava)).generate(this.worldObj, this.rand, k1, l1, i2);
				// Ian - Attempt to disable lava. This didn't work on its own for whatever reason. 
				// I blame the dungeon spawn code below.
			}
		}
		*/

		BlockFalling.fallInstantly = false;
	}

	@Override
	public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position) {
		return null;
	}

	@Override
	public void recreateStructures(Chunk chunk, int chunkX, int chunkZ)
	{

	}

	public void setBlocksInChunk(int x, int z, ChunkPrimer primer)
	{
		this.biomesForGeneration = this.worldObj.getBiomeProvider().getBiomesForGeneration(this.biomesForGeneration, x * 4 - 2, z * 4 - 2, 10, 10);
		this.generateHeightmap(x * 4, 0, z * 4);

		for (int i = 0; i < 4; ++i)
		{
			int j = i * 5;
			int k = (i + 1) * 5;

			for (int l = 0; l < 4; ++l)
			{
				int i1 = (j + l) * 33;
				int j1 = (j + l + 1) * 33;
				int k1 = (k + l) * 33;
				int l1 = (k + l + 1) * 33;

				for (int i2 = 0; i2 < 32; ++i2)
				{
					double d0 = 0.125D;
					double d1 = this.heightMap[i1 + i2];
					double d2 = this.heightMap[j1 + i2];
					double d3 = this.heightMap[k1 + i2];
					double d4 = this.heightMap[l1 + i2];
					double d5 = (this.heightMap[i1 + i2 + 1] - d1) * 0.125D;
					double d6 = (this.heightMap[j1 + i2 + 1] - d2) * 0.125D;
					double d7 = (this.heightMap[k1 + i2 + 1] - d3) * 0.125D;
					double d8 = (this.heightMap[l1 + i2 + 1] - d4) * 0.125D;

					for (int j2 = 0; j2 < 8; ++j2)
					{
						double d9 = 0.25D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * 0.25D;
						double d13 = (d4 - d2) * 0.25D;

						for (int k2 = 0; k2 < 4; ++k2)
						{
							double d14 = 0.25D;
							double d16 = (d11 - d10) * 0.25D;
							double lvt_45_1_ = d10 - d16;

							for (int l2 = 0; l2 < 4; ++l2)
							{
								if ((lvt_45_1_ += d16) > 0.0D)
								{
									primer.setBlockState(i * 4 + k2, i2 * 8 + j2, l * 4 + l2, Blocks.AIR.getDefaultState());
								}
								else if (i2 * 8 + j2 < this.settings.seaLevel)
								{
									primer.setBlockState(i * 4 + k2, i2 * 8 + j2, l * 4 + l2, Blocks.AIR.getDefaultState());
								}
								else
                                {
                                    primer.setBlockState(i * 4 + k2, i2 * 8 + j2, l * 4 + l2, Blocks.AIR.getDefaultState());
                                }
							}

							d10 += d12;
							d11 += d13;
						}

						d1 += d5;
						d2 += d6;
						d3 += d7;
						d4 += d8;
					}
				}
			}
		}
	}
}