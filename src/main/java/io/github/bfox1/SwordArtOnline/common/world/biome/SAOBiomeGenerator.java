package io.github.bfox1.SwordArtOnline.common.world.biome;

import java.util.ArrayList;
import java.util.Random;

import io.github.bfox1.SwordArtOnline.common.util.FloorPoint;
import io.github.bfox1.SwordArtOnline.common.util.DistanceHelper;
import io.github.bfox1.SwordArtOnline.init.BlockInit;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public class SAOBiomeGenerator extends Biome
{
	public IBlockState wallBlock;
	private int wallSegmentHeight = 20;
	private int wallThickness = 5;
	private int wallStart = 999;
	private int wallEnd = wallStart+wallThickness;
	private int floorSeparation = ((wallStart * 2) + 512);
	private int floorsEnd = (floorSeparation * 10) + wallEnd;
	
	private ArrayList<FloorPoint> floorCenters = getFloorCenterPoints();
	private FloorPoint currentFloor;
	
	public SAOBiomeGenerator(Biome.BiomeProperties biomeProperties) {
		super(biomeProperties);
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.topBlock = BlockInit.aincradGrassVariation.getDefaultState();
		this.fillerBlock = BlockInit.aincradDirtVariation.getDefaultState();
		this.wallBlock = BlockInit.aincradCobbleVariation.getDefaultState();
	}
	
	@Override
	public float getSpawningChance() {
		return 0.0F;
	}
	
	@Override
	public void genTerrainBlocks(World world, Random rand, ChunkPrimer primer, int x, int z, double noiseGenSeed) {
		this.genVoidTerrain(world, rand, primer, x, z, noiseGenSeed);
	}
	
	private boolean isWithinCone(int distance, int blockHeight) {
		double dist = (double)distance;
		double height = (double)blockHeight;
		double mult = 39.0D / (double)wallStart;
		
		if(height < 40 && height >= ((dist * mult))) {
			return true;
		}
		
		return false;
	}
	
	private ArrayList<FloorPoint> getFloorCenterPoints() {
		ArrayList<FloorPoint> centerPoints = new ArrayList<FloorPoint>();
		
		for(int x = 0; x < 10; x++)
		for(int z = 0; z < 10; z++) {
			FloorPoint point = new FloorPoint((x * 10) + (z + 1), x * floorSeparation, 40, z * floorSeparation, wallStart, wallThickness, 1.4D);
			centerPoints.add(point);
		}
		
		return centerPoints;
	}
	
	private void setFloorPointSize(int floorNumber, int radius, int wallThickness) {
		if(floorNumber <= 0 || floorNumber >= 100) return;
		FloorPoint floor = floorCenters.get(floorNumber-1);
		floor.setFloorRadius(radius);
		floor.setWallThickness(wallThickness);
		calculateFloorSeparation();
	}
	
	private void setFloorPointAmplitude(int floorNumber, int floorAmplitude) {
		if(floorNumber <= 0 || floorNumber >= 100) return;
		FloorPoint floor = floorCenters.get(floorNumber-1);
		floor.setFloorAmplitude(floorAmplitude);
	}
	
	private void calculateFloorSeparation() {
		FloorPoint floor, floorWest, floorEast, floorNorth, floorSouth;
		for(int x = 0; x < 10; x++)
		for(int z = 0; z < 10; z++) {
			floor = floorCenters.get((x*10)+z);
			if(z > 0) {
				floorSouth = floorCenters.get((x*10)+(z-1));
				if(floorSouth.getPosZBoundary() + 512 >= floor.getNegZBoundary()) {
					floor.setZ(floorSouth.getPosZBoundary() + 512 + floor.getFloorRadius());
				}
			}
			if(z < 9) {
				floorNorth = floorCenters.get((x*10)+(z+1));
				if(floorNorth.getNegZBoundary() - 512 <= floor.getPosZBoundary()) {
					floorNorth.setZ(floor.getPosZBoundary() + 512 + floorNorth.getFloorRadius());
				}
			}
			if(x > 0) {
				floorWest = floorCenters.get(((x-1)*10)+z);
				if(floorWest.getPosXBoundary() + 512 >= floor.getNegXBoundary()) {
					floor.setX(floorWest.getPosXBoundary() + 512 + floor.getFloorRadius());
				}
			}
			if(x < 9) {
				floorEast = floorCenters.get(((x+1)*10)+z);
				if(floorEast.getNegXBoundary() - 512 >= floor.getPosXBoundary()) {
					floorEast.setX(floor.getPosXBoundary() + 512 + floorEast.getFloorRadius());
				}
			}
		}
	}
	
	private int getCurrentFloorNumber(int x, int z) {
		for(FloorPoint point : floorCenters) {
			if(x >= point.getNegXBoundary() && x <= point.getPosXBoundary() && z >= point.getNegZBoundary() && z <= point.getPosZBoundary()) {
				return point.getFloorNumber();
			}
		}
		
		return 0;
	}
	
	public void genVoidTerrain(World world, Random random, ChunkPrimer primer, int x, int z, double noiseGenSeed) {
		if(x <= floorsEnd && x >= -wallEnd && z <= floorsEnd && z >= -wallEnd) {
			int floorNumber = getCurrentFloorNumber(x, z);
			if(floorNumber == 0) return;
			currentFloor = floorCenters.get(floorNumber-1);
			int distance = DistanceHelper.distance2D(x, z, currentFloor.getX(), currentFloor.getZ());
			
			int chunkX = x & 15;
	        int chunkZ = z & 15;
	        
	        if(distance <= wallEnd && distance > wallStart) {
				int wallTop = ((wallThickness-(wallEnd - distance)) * wallSegmentHeight)+40;
	        	int wallBottom = wallTop-wallSegmentHeight;
	        	
				for (int blockHeight = 255; blockHeight >= 0; --blockHeight) {
		        	if(blockHeight <= wallTop && blockHeight >= wallBottom) {
		        		primer.setBlockState(chunkX, blockHeight, chunkZ, this.wallBlock);
		        	}
		        }
			}else if(distance <= wallStart) {
		        IBlockState aincradGrass = this.topBlock;
		        IBlockState aincradDirt = this.fillerBlock;
		        		        
		        int blockMaxHeight = 60;
		        
		        for (int blockHeight = 255; blockHeight >= 0; --blockHeight) {
		        	if(blockHeight == blockMaxHeight) {
		        		primer.setBlockState(chunkX, blockHeight, chunkZ, aincradGrass);
		        	}
		        	else if(blockHeight < blockMaxHeight && blockHeight >= 40) {
		        		primer.setBlockState(chunkX, blockHeight, chunkZ, aincradDirt);
		        	}
		        	else if(blockHeight < blockMaxHeight && blockHeight >= 0 && isWithinCone(distance, blockHeight)) {
		        		primer.setBlockState(chunkX, blockHeight, chunkZ, aincradDirt);
		        	}
		        	else {
		        		primer.setBlockState(chunkX, blockHeight, chunkZ, Blocks.AIR.getDefaultState());
		        	}
	            }
	        }
		}
    }
}