package io.github.bfox1.SwordArtOnline.common.util;

import java.util.ArrayList;

/**
 * Can be used to store both a location and a list of mobs to spawn. These objects will be referenced by the mob
 * spawning mechanics to determine whether or not a mob can spawn.
 */
public class MobSpawnLocation
{
	private Point3D location;
	private int minLevel, maxLevel;
	/**
	 * If this is empty don't restrict mob types from spawning here.
	 */
	private ArrayList<Enum> mobTypeList;

	public MobSpawnLocation(Point3D location, int minLevel, int maxLevel, ArrayList<Enum> mobTypeList)
	{
		this.location = location;
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
		this.mobTypeList = mobTypeList;
	}

	public void setLocation(Point3D location)
	{
		this.location = location;
	}

	public void setMinLevel(int minLevel)
	{
		this.minLevel = minLevel;
	}

	public void setMaxLevel(int maxLevel)
	{
		this.maxLevel = maxLevel;
	}

	public Point3D getLocation()
	{
		return location;
	}

	public int getMinLevel()
	{
		return minLevel;
	}

	public int getMaxLevel()
	{
		return maxLevel;
	}

	public ArrayList<Enum> getMobTypeList()
	{
		return mobTypeList;
	}
}
