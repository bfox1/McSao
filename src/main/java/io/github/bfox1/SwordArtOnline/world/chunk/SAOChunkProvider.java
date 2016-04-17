package io.github.bfox1.SwordArtOnline.world.chunk;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;

import java.util.List;
import java.util.Random;

//import net.minecraft.world.gen.feature.MapGenScatteredFeature;
//import net.minecraftforge.event.Event.Result;

/**
 * Created by Earbuds on 4/15/2016.
 */
public class SAOChunkProvider implements IChunkProvider {

	private Random rand;
	private NoiseGeneratorOctaves noiseGen1;
	private NoiseGeneratorOctaves noiseGen2;
	private NoiseGeneratorOctaves noiseGen3;
	private NoiseGeneratorPerlin noiseGen4;
	public NoiseGeneratorOctaves noiseGen5;
	public NoiseGeneratorOctaves noiseGen6;
	public NoiseGeneratorOctaves mobSpawnerNoise;
	
	private World worldObj;
	private final boolean mapFeaturesEnabled;
	private WorldType worldType;
	private final double[] field_147434_q;
	private final float[] parabolicField;
	private double[] stoneNoise = new double[256];
	private MapGenBase ravineGenerator = new MapGenRavine();
	private BiomeGenBase[] biomesForGeneration;
	double[] field_147427_d;
	double[] field_147428_e;
	double[] field_147425_f;
	double[] field_147426_g;
	private WorldType field_177475_o;
	int[][] field_73219_j = new int[32][32];

	public SAOChunkProvider(World worldObj, long seed, boolean enableMapFeatures, String settings) {
		this.worldObj = worldObj;
        this.mapFeaturesEnabled = enableMapFeatures;
        this.field_177475_o = worldObj.getWorldInfo().getTerrainType();
        this.rand = new Random(seed);
        this.noiseGen1 = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGen2 = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGen3 = new NoiseGeneratorOctaves(this.rand, 8);
        this.noiseGen4 = new NoiseGeneratorPerlin(this.rand, 4);
        this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
        this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
        this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rand, 8);
        this.field_147434_q = new double[825];
        this.parabolicField = new float[25];

		for(int j = -2; j <= 2; ++j) {
			for(int k = -2; k <= 2; ++k) {
				float f = 10.0F / MathHelper.sqrt_float((float)(j * j + k * k) + 0.2F);
				this.parabolicField[j + 2 + (k + 2) * 5] = f;
			}
		}

		NoiseGenerator[] noiseGens = {noiseGen1, noiseGen2, noiseGen3, noiseGen4, noiseGen5, noiseGen6, mobSpawnerNoise};
        noiseGens = net.minecraftforge.event.terraingen.TerrainGen.getModdedNoiseGenerators(worldObj, this.rand, noiseGens);
        this.noiseGen1 = (NoiseGeneratorOctaves)noiseGens[0];
        this.noiseGen2 = (NoiseGeneratorOctaves)noiseGens[1];
        this.noiseGen3 = (NoiseGeneratorOctaves)noiseGens[2];
        this.noiseGen4 = (NoiseGeneratorPerlin)noiseGens[3];
        this.noiseGen5 = (NoiseGeneratorOctaves)noiseGens[4];
        this.noiseGen6 = (NoiseGeneratorOctaves)noiseGens[5];
        this.mobSpawnerNoise = (NoiseGeneratorOctaves)noiseGens[6];
	}

	@Override
	public boolean chunkExists(int chunkXCoord, int chunkZCoord) {
		return true;
	}

	/**
	 * I Believe this method is used to create the upper layers of terrain generation in the world. For example the top
	 * layer of most biomes is a grass block, followed mostly by dirt but there may be sand and gravel in-between. Then
	 * there is stone, which is generated first I assume, so that this topper and filler layer can be placed above it
	 * using the same random seed.
	 * @param chunkXCoord
	 * @param chunkZCoord
	 * @param chunkOfBlocks
	 */
	public void func_147424_a(int chunkXCoord, int chunkZCoord, Block[] chunkOfBlocks) {
		byte b0 = 63;
		this.biomesForGeneration = this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, chunkXCoord * 4 - 2, chunkZCoord * 4 - 2, 10, 10);
		this.func_147423_a(chunkXCoord * 4, 0, chunkZCoord * 4);

		for(int k = 0; k < 4; ++k) {
			int l = k * 5;
			int i1 = (k + 1) * 5;

			for(int j1 = 0; j1 < 4; ++j1) {
				int k1 = (l + j1) * 33;
				int l1 = (l + j1 + 1) * 33;
				int i2 = (i1 + j1) * 33;
				int j2 = (i1 + j1 + 1) * 33;

				for(int k2 = 0; k2 < 32; ++k2) {
					double d0 = 0.125D;
					double d1 = this.field_147434_q[k1 + k2];
					double d2 = this.field_147434_q[l1 + k2];
					double d3 = this.field_147434_q[i2 + k2];
					double d4 = this.field_147434_q[j2 + k2];
					double d5 = (this.field_147434_q[k1 + k2 + 1] - d1) * d0;
					double d6 = (this.field_147434_q[l1 + k2 + 1] - d2) * d0;
					double d7 = (this.field_147434_q[i2 + k2 + 1] - d3) * d0;
					double d8 = (this.field_147434_q[j2 + k2 + 1] - d4) * d0;

					for(int l2 = 0; l2 < 8; ++l2) {
						double d9 = 0.25D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;

						for(int i3 = 0; i3 < 4; ++i3) {
							int j3 = i3 + k * 4 << 12 | 0 + j1 * 4 << 8 | k2 * 8 + l2;
							short short1 = 256;
							j3 -= short1;
							double d14 = 0.25D;
							double d16 = (d11 - d10) * d14;
							double d15 = d10 - d16;

							for(int k3 = 0; k3 < 4; ++k3) {
								if((d15 += d16) > 0.0D) {
									chunkOfBlocks[j3 += short1] = Blocks.air;
								}
								else if(k2 * 8 + l2 < b0) {
									chunkOfBlocks[j3 += short1] = Blocks.air;
								}else {
									chunkOfBlocks[j3 += short1] = null;
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
	
	public void replaceBlocksForBiome(int chunkZCoord, int chunkXCoord, ChunkPrimer primer, BiomeGenBase[] biomes){
        net.minecraftforge.event.terraingen.ChunkProviderEvent.ReplaceBiomeBlocks event = new net.minecraftforge.event.terraingen.ChunkProviderEvent.ReplaceBiomeBlocks(this, chunkZCoord, chunkXCoord, primer, this.worldObj);
		MinecraftForge.EVENT_BUS.post(event);
        if(event.getResult() == net.minecraftforge.fml.common.eventhandler.Event.Result.DENY) return;

		double d0 = 0.03125D;
        this.stoneNoise = this.noiseGen4.func_151599_a(this.stoneNoise, (double)(chunkZCoord * 16), (double)(chunkXCoord * 16), 16, 16, d0 * 2.0D, d0 * 2.0D, 1.0D);

        for(int i = 0; i < 16; ++i) {
            for(int j = 0; j < 16; ++j) {
                BiomeGenBase biomegenbase = biomes[j + i * 16];
                biomegenbase.genTerrainBlocks(this.worldObj, this.rand, primer, chunkXCoord * 16 + i, chunkZCoord * 16 + j, this.stoneNoise[j + i * 16]);
            }
        }
	}
	
	private int getWorldCoord(int coord, int mod) {
		if(coord >= 0) {
			return coord + mod;
		}else {
			return coord - mod;
		}
	}

	@Override
	public Chunk provideChunk(int x, int z) {
		this.rand.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
        ChunkPrimer chunkprimer = new ChunkPrimer();
        this.setBlocksInChunk(x, z, chunkprimer);
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, x * 16, z * 16, 16, 16);
        this.replaceBlocksForBiome(x, z, chunkprimer, this.biomesForGeneration);
        Chunk chunk = new Chunk(this.worldObj, chunkprimer, x, z);
        byte[] abyte = chunk.getBiomeArray();

        for(int i = 0; i < abyte.length; ++i) {
            abyte[i] = (byte)this.biomesForGeneration[i].biomeID;
        }

        chunk.generateSkylightMap();
        return chunk;
	}

	private void func_147423_a(int p_147423_1_, int p_147423_2_, int p_147423_3_) {
		double d0 = 684.412D;
		double d1 = 684.412D;
		double d2 = 512.0D;
		double d3 = 512.0D;
		this.field_147426_g = this.noiseGen6.generateNoiseOctaves(this.field_147426_g, p_147423_1_, p_147423_3_, 5, 5, 200.0D, 200.0D, 0.5D);
		this.field_147427_d = this.noiseGen3.generateNoiseOctaves(this.field_147427_d, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 8.555150000000001D, 4.277575000000001D, 8.555150000000001D);
		this.field_147428_e = this.noiseGen1.generateNoiseOctaves(this.field_147428_e, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 684.412D, 684.412D, 684.412D);
		this.field_147425_f = this.noiseGen2.generateNoiseOctaves(this.field_147425_f, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 684.412D, 684.412D, 684.412D);
		boolean flag1 = false;
		boolean flag = false;
		int l = 0;
		int i1 = 0;
		double d4 = 8.5D;

		for(int j1 = 0; j1 < 5; ++j1) {
			for(int k1 = 0; k1 < 5; ++k1) {
				float f = 0.0F;
				float f1 = 0.0F;
				float f2 = 0.0F;
				byte b0 = 2;
				BiomeGenBase biomegenbase = this.biomesForGeneration[j1 + 2 + (k1 + 2) * 10];

				for(int l1 = -b0; l1 <= b0; ++l1) {
					for(int i2 = -b0; i2 <= b0; ++i2) {
						BiomeGenBase biomegenbase1 = this.biomesForGeneration[j1 + l1 + 2 + (k1 + i2 + 2) * 10];
						float f3 = biomegenbase1.minHeight;
						float f4 = biomegenbase1.maxHeight;

						if(this.worldType == WorldType.AMPLIFIED && f3 > 0.0F) {
							f3 = 1.0F + f3 * 2.0F;
							f4 = 1.0F + f4 * 4.0F;
						}

						float f5 = this.parabolicField[l1 + 2 + (i2 + 2) * 5] / (f3 + 2.0F);

						if(biomegenbase1.minHeight > biomegenbase.minHeight) {
							f5 /= 2.0F;
						}

						f += f4 * f5;
						f1 += f3 * f5;
						f2 += f5;
					}
				}

				f /= f2;
				f1 /= f2;
				f = f * 0.9F + 0.1F;
				f1 = (f1 * 4.0F - 1.0F) / 8.0F;
				double d12 = this.field_147426_g[i1] / 8000.0D;

				if(d12 < 0.0D) {
					d12 = -d12 * 0.3D;
				}

				d12 = d12 * 3.0D - 2.0D;

				if(d12 < 0.0D) {
					d12 /= 2.0D;

					if(d12 < -1.0D) {
						d12 = -1.0D;
					}

					d12 /= 1.4D;
					d12 /= 2.0D;
				}else {
					if(d12 > 1.0D) {
						d12 = 1.0D;
					}

					d12 /= 8.0D;
				}

				++i1;
				double d13 = (double)f1;
				double d14 = (double)f;
				d13 += d12 * 0.2D;
				d13 = d13 * 8.5D / 8.0D;
				double d5 = 8.5D + d13 * 4.0D;

				for(int j2 = 0; j2 < 33; ++j2) {
					double d6 = ((double)j2 - d5) * 12.0D * 128.0D / 256.0D / d14;

					if(d6 < 0.0D) {
						d6 *= 4.0D;
					}

					double d7 = this.field_147428_e[l] / 512.0D;
					double d8 = this.field_147425_f[l] / 512.0D;
					double d9 = (this.field_147427_d[l] / 10.0D + 1.0D) / 2.0D;
					double d10 = MathHelper.denormalizeClamp(d7, d8, d9) - d6;

					if(j2 > 29) {
						double d11 = (double)((float)(j2 - 29) / 3.0F);
						d10 = d10 * (1.0D - d11) + -10.0D * d11;
					}

					this.field_147434_q[l] = d10;
					++l;
				}
			}
		}
	}
	
	
	

	@Override
	public Chunk provideChunk(BlockPos blockPosIn) {
        return this.provideChunk(blockPosIn.getX() >> 4, blockPosIn.getZ() >> 4);
    }

	@Override
	public void populate(IChunkProvider thisChunkProvider, int chunkX, int chunkZ) {
		BlockFalling.fallInstantly = true;
		int k = chunkX * 16;
		int l = chunkZ * 16;
		BlockPos blockpos = new BlockPos(k, 0, l);
		BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(blockpos.add(16, 0, 16));
		this.rand.setSeed(this.worldObj.getSeed());
		long i1 = this.rand.nextLong() / 2L * 2L + 1L;
		long j1 = this.rand.nextLong() / 2L * 2L + 1L;
		this.rand.setSeed((long)chunkX * i1 + (long)chunkZ * j1 ^ this.worldObj.getSeed());
		boolean flag = false;

		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(thisChunkProvider, worldObj, rand, chunkX, chunkZ, flag));
		
		if(chunkX == 0 && chunkZ == 0) {
			//this.towerGen.generate(this.worldObj, this.rand, chunkX, chunkZ, 40);
		}


		/*int k1;
		int l1;
		int i2;

		if(biomegenbase != BiomeGenBase.desert && biomegenbase != BiomeGenBase.desertHills && !flag && this.rand.nextInt(4) == 0
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
		}*/

		
		BlockFalling.fallInstantly = false;
	}

	@Override
	public boolean saveChunks(boolean bool, IProgressUpdate upd8) {
		return true;
	}

	@Override
	public boolean unloadQueuedChunks() {
		return false;
	}

	@Override
	public boolean canSave() {
		return true;
	}

	@Override
	public String makeString() {
		return "SAOLevelSource";
	}

	@Override
	public int getLoadedChunkCount() {
		return 0;
	}

	@Override
	public void saveExtraData() {}

	@Override
	public boolean func_177460_a(IChunkProvider provider, Chunk chunk, int chunkX, int chunkZ) {
		return false;
	}

	@Override
	public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
		return null;
	}

	@Override
	public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position) {
		return null;
	}

	@Override
	public void recreateStructures(Chunk chunk, int chunkX, int chunkZ) {}

	public void setBlocksInChunk(int p_180518_1_, int p_180518_2_, ChunkPrimer p_180518_3_) {
	/*	this.biomesForGeneration =
	this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, p_180518_1_ * 4 - 2, p_180518_2_ * 4 - 2, 10, 10);
		this.func_147423_a(p_180518_1_ * 4, 0, p_180518_2_ * 4);

		for(int i = 0; i < 4; ++i) {
			int j = i * 5;
			int k = (i + 1) * 5;

			for(int l = 0; l < 4; ++l) {
				int i1 = (j + l) * 33;
				int j1 = (j + l + 1) * 33;
	            int k1 = (k + l) * 33;
	            int l1 = (k + l + 1) * 33;

	            for(int i2 = 0; i2 < 32; ++i2) {
	            	double d0 = 0.125D;
	            	double d1 = this.field_147434_q[i1 + i2];
	            	double d2 = this.field_147434_q[j1 + i2];
	            	double d3 = this.field_147434_q[k1 + i2];
	            	double d4 = this.field_147434_q[l1 + i2];
	            	double d5 = (this.field_147434_q[i1 + i2 + 1] - d1) * d0;
	            	double d6 = (this.field_147434_q[j1 + i2 + 1] - d2) * d0;
                	double d7 = (this.field_147434_q[k1 + i2 + 1] - d3) * d0;
                	double d8 = (this.field_147434_q[l1 + i2 + 1] - d4) * d0;

                	for(int j2 = 0; j2 < 8; ++j2) {
                		double d9 = 0.25D;
                		double d10 = d1;
                		double d11 = d2;
                		double d12 = (d3 - d1) * d9;
                		double d13 = (d4 - d2) * d9;

                		for(int k2 = 0; k2 < 4; ++k2) {
                			double d14 = 0.25D;
                			double d16 = (d11 - d10) * d14;
                			double lvt_45_1_ = d10 - d16;

                			for(int l2 = 0; l2 < 4; ++l2) {
                				if((lvt_45_1_ += d16) > 0.0D) {
                					//p_180518_3_.setBlockState(i * 4 + k2, i2 * 8 + j2, l * 4 + l2, Blocks.stone.getDefaultState());
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
		*/
		byte b0 = 63;
		this.biomesForGeneration =
				this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, p_180518_1_ * 4 - 2, p_180518_2_ * 4 - 2, 10, 10);
		this.func_147423_a(p_180518_1_ * 4, 0, p_180518_2_ * 4);

		for (int k = 0; k < 4; ++k)
		{
			int l = k * 5;
			int i1 = (k + 1) * 5;

			for (int j1 = 0; j1 < 4; ++j1)
			{
				int k1 = (l + j1) * 33;
				int l1 = (l + j1 + 1) * 33;
				int i2 = (i1 + j1) * 33;
				int j2 = (i1 + j1 + 1) * 33;

				for (int k2 = 0; k2 < 32; ++k2)
				{
					double d0 = 0.125D;
					double d1 = this.field_147434_q[k1 + k2];
					double d2 = this.field_147434_q[l1 + k2];
					double d3 = this.field_147434_q[i2 + k2];
					double d4 = this.field_147434_q[j2 + k2];
					double d5 = (this.field_147434_q[k1 + k2 + 1] - d1) * d0;
					double d6 = (this.field_147434_q[l1 + k2 + 1] - d2) * d0;
					double d7 = (this.field_147434_q[i2 + k2 + 1] - d3) * d0;
					double d8 = (this.field_147434_q[j2 + k2 + 1] - d4) * d0;

					for (int l2 = 0; l2 < 8; ++l2)
					{
						double d9 = 0.25D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;

						for (int i3 = 0; i3 < 4; ++i3)
						{
							int j3 = i3 + k * 4 << 12 | 0 + j1 * 4 << 8 | k2 * 8 + l2;
							short short1 = 256;
							j3 -= short1;
							double d14 = 0.25D;
							double d16 = (d11 - d10) * d14;
							double d15 = d10 - d16;

							for (int k3 = 0; k3 < 4; ++k3)
							{
								if ((d15 += d16) > 0.0D)
								{
									//Ian - Attempt to replace all blocks with empty spots here and in the next block.
									//I believe this is the filler block
									//chunkOfBlocks[j3 += short1] = Blocks.air;
									p_180518_3_.setBlockState(j3 += short1, Blocks.air.getDefaultState());
								}
								else if (k2 * 8 + l2 < b0)
								{
									//And I think this is the topper block.
									//chunkOfBlocks[j3 += short1] = Blocks.air;
									p_180518_3_.setBlockState(j3 += short1, Blocks.air.getDefaultState());
								}
								else
								{
									//chunkOfBlocks[j3 += short1] = null;
									p_180518_3_.setBlockState(j3 += short1, null);
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