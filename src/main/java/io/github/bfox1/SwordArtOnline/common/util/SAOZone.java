package io.github.bfox1.SwordArtOnline.common.util;

import net.minecraft.entity.EntityLiving;

public class SAOZone
{
	private CuboidArea zoneArea;

	private int xStart, yStart, zStart;
	private int width, height, length;

	private int mobSpawnAmountMin, mobSpawnAmountMax;
	private int mobSpawnGroupMin, mobSpawnGroupMax;
	private int mobLevelMin, mobLevelMax;
	//List of mob types goes here, use it for determining which mobs can spawn. Empty list should mean it can spawn anything.

	/**
	 * No mobs being zero mobs being added by our mob spawning mechanic at all. Inherited meaning, there is probably an overlapping zone that we can grab
	 * data from, which will allow us to create amorphously shaped zones. Level limited meaning we only control what levels of mobs spawn in, and all
	 * other values are default. Location limited means we only control where within the zone mobs can spawn, other values are default or not limited
	 * at all. Full control means we want to customize every aspect of the mob spawning within the zone.
	 */
	private enum MobControl
	{
		NO_MOBS, INHERITED, LEVEL_LIMITED, LOCATION_LIMITED, FULL_CONTROL
	}

	/**
	 * Used to specify which buffs are added to a zone. Can be used with additional controls to specify whether mobs or players get each buff added.
	 */
	private enum ZoneEffect
	{
		BLINDNESS, ON_FIRE, STRENGTH, POISONED, SPEED, HEALING, WEAKNESS
	}

	/**
	 * Specifications for controlling actions which are allowed or disallowed within a zone. This can control things such as whether mobs can be attacked,
	 * player vs player is allowed or disabled, using items or abilities is allowed, or whether anyone or anything can even enter or exit the zone.
	 */
	private enum ZonePrivileges
	{
		PVP_ENABLED, PVP_DISABLED, PVE_ENABLED, PVE_DISABLED, ABILITIES_ENABLED, ABILITIES_DISABLED, ABILITIES_RESTRICTED, ITEMS_ENABLED, ITEMS_DISABLED,
		ITEMS_RESTRICTED, NO_COMBAT, DUEL_ZONE, FFA_ZONE, NO_PLAYER_EXIT, NO_PLAYER_ENTRY, PLAYER_COUNT_LIMITED, NO_MOB_ENTRY, NO_MOB_EXIT
	}

	/**
	 * Groups of mobs that denote any mob that falls under a category can spawn. Placeholder, needs more info to be finished.
	 */
	private enum MobGroup
	{
		WOLVES, GOBLINS, LIZARDMEN, GIANTS
	}

	/**
	 * Basic zone that only specifies the initial location. Default size of 15^3. Mob spawn amount range is 0-10. Mob groups disabled. Levels 1-3.
	 * @param xStart
	 * @param yStart
	 * @param zStart
	 */
	public SAOZone(int xStart, int yStart, int zStart)
	{
		this.xStart = xStart;
		this.yStart = yStart;
		this.zStart = zStart;

		width = 15;
		height = 15;
		length = 15;

		mobSpawnAmountMin = 0;
		mobSpawnAmountMax = 10;

		mobSpawnGroupMax = 0;
		mobSpawnGroupMin = 0;

		mobLevelMin = 1;
		mobLevelMax = 3;

		zoneArea = new CuboidArea(xStart, yStart, zStart, width, height, length);
	}

	/**
	 * Make a Zone that specifies the starting location, size, and mob amount limitations. Default to no groups and levels 1-3.
	 * @param xStart
	 * @param yStart
	 * @param zStart
	 * @param width
	 * @param height
	 * @param length
	 * @param mobSpawnAmountMin
	 * @param mobSpawnAmountMax
	 */
	public SAOZone (int xStart, int yStart, int zStart, int width, int height, int length,
	                int mobSpawnAmountMin, int mobSpawnAmountMax)
	{
		this.xStart = xStart;
		this.yStart = yStart;
		this.zStart = zStart;

		this.width = width;
		this.height = height;
		this.length = length;

		this.mobSpawnAmountMax = mobSpawnAmountMax;
		this.mobSpawnAmountMin = mobSpawnAmountMin;

		mobSpawnGroupMax = 0;
		mobSpawnGroupMin = 0;

		mobLevelMin = 1;
		mobLevelMax = 3;

		zoneArea = new CuboidArea(xStart, yStart, zStart, width, height, length);
	}

	/**
	 * Make a zone that specifies the starting location, size, mob spawn limitations, and whether they can spawn in groups. Level defaults to 1-3.
	 * @param xStart
	 * @param yStart
	 * @param zStart
	 * @param width
	 * @param height
	 * @param length
	 * @param mobSpawnAmountMin
	 * @param mobSpawnAmountMax
	 * @param mobSpawnGroupMin
	 * @param mobSpawnGroupMax
	 */
	public SAOZone (int xStart, int yStart, int zStart, int width, int height, int length,
	                int mobSpawnAmountMin, int mobSpawnAmountMax, int mobSpawnGroupMin, int mobSpawnGroupMax)
	{
		this.xStart = xStart;
		this.yStart = yStart;
		this.zStart = zStart;

		this.width = width;
		this.height = height;
		this.length = length;

		this.mobSpawnAmountMax = mobSpawnAmountMax;
		this.mobSpawnAmountMin = mobSpawnAmountMin;

		this.mobSpawnGroupMin = mobSpawnGroupMin;
		this.mobSpawnGroupMax = mobSpawnGroupMax;

		mobLevelMin = 1;
		mobLevelMax = 3;

		zoneArea = new CuboidArea(xStart, yStart, zStart, width, height, length);
	}

	/**
	 * Customize the location, size, and mob spawn limitations as well as whether mobs can spawn in groups and their level restrictions.
	 * @param xStart
	 * @param yStart
	 * @param zStart
	 * @param width
	 * @param height
	 * @param length
	 * @param mobSpawnAmountMin
	 * @param mobSpawnAmountMax
	 * @param mobSpawnGroupMin
	 * @param mobSpawnGroupMax
	 * @param mobLevelMin
	 * @param mobLevelMax
	 */
	public SAOZone (int xStart, int yStart, int zStart, int width, int height, int length, int mobSpawnAmountMin,
	                int mobSpawnAmountMax, int mobSpawnGroupMin, int mobSpawnGroupMax, int mobLevelMin, int mobLevelMax)
	{
		this.xStart = xStart;
		this.yStart = yStart;
		this.zStart = zStart;

		this.width = width;
		this.height = height;
		this.length = length;

		this.mobSpawnAmountMax = mobSpawnAmountMax;
		this.mobSpawnAmountMin = mobSpawnAmountMin;

		this.mobSpawnGroupMin = mobSpawnGroupMin;
		this.mobSpawnGroupMax = mobSpawnGroupMax;

		this.mobLevelMax = mobLevelMax;
		this.mobLevelMin = mobLevelMin;

		zoneArea = new CuboidArea(xStart, yStart, zStart, width, height, length);
	}

	/**
	 * Checks to see whether a living entity, be it a player or a mob, has its current (rounded) location inside the zone's area.
	 * @returns true if the entity's location is within the zone, false if not.
	 */
	public boolean isWithinZone(EntityLiving entity)
	{
		Point3D entityLocation = new Point3D((int)entity.posX,(int)entity.posY,(int)entity.posZ);
		if(zoneArea.isPointWithin(entityLocation))
		{
			return true;
		}
		return false;
	}

	public int getXStart()
	{
		return xStart;
	}

	public int getYStart()
	{
		return yStart;
	}

	public int getZStart()
	{
		return zStart;
	}

	public int getXEnd()
	{
		return xStart+width;
	}

	public int getYEnd()
	{
		return yStart+height;
	}

	public int getZEnd()
	{
		return zStart+length;
	}

	public int getMobSpawnAmountMin()
	{
		return mobSpawnAmountMin;
	}

	public int getMobSpawnAmountMax()
	{
		return mobSpawnAmountMax;
	}

	public int getMobSpawnGroupMin()
	{
		return mobSpawnGroupMin;
	}

	public int getMobSpawnGroupMax()
	{
		return mobSpawnGroupMax;
	}

	public int getMobLevelMin()
	{
		return mobLevelMin;
	}

	public int getMobLevelMax()
	{
		return mobLevelMax;
	}
}
