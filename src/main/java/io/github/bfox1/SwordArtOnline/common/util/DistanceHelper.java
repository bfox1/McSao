package io.github.bfox1.SwordArtOnline.common.util;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class DistanceHelper {
	
	public static Integer distance2D(double xD, double xC, double Dx, double Cx) {
    	double xDelta = Math.abs(Dx - xD);
    	double zDelta = Math.abs(Cx - xC);
    	double distance = Math.sqrt(xDelta*xDelta + zDelta*zDelta);
    	return (int) distance;
    }
	
	public static Integer distance2D(double xD, double Cx) {
		double xDelta = Math.abs(xD);
		double zDelta = Math.abs(Cx);
		double distance = Math.sqrt(xDelta*xDelta + zDelta*zDelta);
    	return (int) distance;
	}
}