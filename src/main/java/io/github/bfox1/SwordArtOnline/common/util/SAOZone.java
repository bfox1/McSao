package io.github.bfox1.SwordArtOnline.common.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;

public class SAOZone
{
	private CuboidArea zoneArea;

	private int xStart, yStart, zStart;
	private int width, height, length;

	private int mobSpawnAmountMin, mobSpawnAmountMax;
	private int mobSpawnGroupMin, mobSpawnGroupMax;
	private int mobLevelMin, mobLevelMax;

	private int currentMobCount = 0;

	private ArrayList<Enum> permissions = new ArrayList<>();
	private ArrayList<MobSpawnLocation> spawnLocations = new ArrayList<>();

	//List of mob types goes here, use it for determining which mobs can spawn. Empty list should mean it can spawn anything.

	/**
	 * No mobs being zero mobs being added by our mob spawning mechanic at all. Inherited meaning, there is probably an overlapping zone that we can grab
	 * data from, which will allow us to create amorphously shaped zones. Level limited meaning we only control what levels of mobs spawn in, and all
	 * other values are default. Location limited means we only control where within the zone mobs can spawn, other values are default or not limited
	 * at all. Full control means we want to customize every aspect of the mob spawning within the zone.
	 */
	private enum MobControl
	{
		NO_MOBS, INHERITED, FREE_LEVELS, NO_GROUPS, LOCATION_LIMITED, FULL_CONTROL
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
		ITEMS_RESTRICTED, NO_COMBAT, DUEL_ZONE, FFA_ZONE, PLAYER_COUNT_LIMITED
	}

	private enum ZoneTraversal
	{
		NO_PLAYER_EXIT, NO_PLAYER_ENTRY, NO_MOB_ENTRY, NO_MOB_EXIT
	}

	/**
	 * Groups of mobs that denote any mob that falls under a category can spawn. Placeholder, needs more info to be finished.
	 */
	private enum MobTypes
	{
		WOLVES, GOBLINS, LIZARDMEN, GIANTS
	}

	/**
	 * Basic zone that only specifies the initial location. Default size of 15^3. Mob spawn amount range is 0-10. Mob groups disabled. Levels 1-3.
	 *
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
		mobLevelMax = 99;

		zoneArea = new CuboidArea(xStart, yStart, zStart, width, height, length);
		permissions.add(MobControl.NO_GROUPS);
		permissions.add(MobControl.FREE_LEVELS);
	}

	/**
	 * Make a Zone that specifies the starting location, size, and mob amount limitations. Default to no groups and levels 1-3.
	 *
	 * @param xStart
	 * @param yStart
	 * @param zStart
	 * @param width
	 * @param height
	 * @param length
	 * @param mobSpawnAmountMin
	 * @param mobSpawnAmountMax
	 */
	public SAOZone(int xStart, int yStart, int zStart, int width, int height, int length,
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
		mobLevelMax = 99;

		zoneArea = new CuboidArea(xStart, yStart, zStart, width, height, length);
		permissions.add(MobControl.NO_GROUPS);
		permissions.add(MobControl.FREE_LEVELS);
	}

	/**
	 * Make a zone that specifies the starting location, size, mob spawn limitations, and whether they can spawn in groups. Level defaults to 1-3.
	 *
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
	public SAOZone(int xStart, int yStart, int zStart, int width, int height, int length,
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
		mobLevelMax = 99;

		zoneArea = new CuboidArea(xStart, yStart, zStart, width, height, length);
		permissions.add(MobControl.FREE_LEVELS);
	}

	/**
	 * Customize the location, size, and mob spawn limitations as well as whether mobs can spawn in groups and their level restrictions.
	 *
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
	public SAOZone(int xStart, int yStart, int zStart, int width, int height, int length, int mobSpawnAmountMin,
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
		permissions.add(MobControl.FULL_CONTROL);
	}

	/**
	 * Checks to see whether a living entity, be it a player or a mob, has its current (rounded) location inside the zone's area.
	 *
	 * @returns true if the entity's location is within the zone, false if not.
	 */
	public boolean isEntityWithinZone(Entity entity)
	{
		Point3D entityLocation = new Point3D((int) entity.posX, (int) entity.posY, (int) entity.posZ);
		if (zoneArea.isPointWithin(entityLocation))
		{
			return true;
		}
		return false;
	}

	/**
	 * After making a list of permissions (enums) set this zone's permissions to that enum list.
	 * @param permissions
	 */
	public void setZonePermissions(ArrayList<Enum> permissions)
	{
		this.permissions = permissions;
	}

	/**
	 * Somewhat complicated method for determining if an entity can cross the border of the zone,
	 * based on permissions. In order to exit, the entity has to be inside, has to be moving in a direction
	 * that will put them outside, and the permissions list has to not contain "No Player Exit" for a player
	 * and not contain "No Mob Exit" for a mob.
	 *
	 * Likewise for entering the zone, they have to be outside already, have to be moving in a direction that
	 * will put them inside, and the permissions list needs to not contain "No Player Entry" for a player, and
	 * not contain "No Mob Entry" for a mob.
	 *
	 * This method should be used sparingly because it is calculation intensive. Try to see if there is a way to
	 * make zone barriers out of invisible blocks that can change solidity per entity based on permissions "on the fly"
	 *
	 * In case this is needed later: EntityLivingBase -> EntityLiving -> EntityPlayer
	 *                                                                -> EntityCreature -> EntityMob -> All The Mobs
	 * @param entity - The entity being checked.
	 * @returns True if the entity, going the direction it is, can cross the border of the zone.
	 */
	public boolean canEntityCross(EntityLivingBase entity)
	{
		boolean isPlayer = entity instanceof EntityPlayer;
		boolean isInside = isEntityWithinZone(entity);
		double motionZ = entity.motionZ;
		double motionY = entity.motionY;
		double motionX = entity.motionX;
		//add motion to coordinates to see if entity will be moving outside
		int movedX = (int)(entity.posX+motionX);
		int movedY = (int)(entity.posY+motionY);
		int movedZ = (int)(entity.posZ+motionZ);
		//New coords should be what the player "will be" doing.
		Point3D movedCoords = new Point3D(movedX, movedY, movedZ);
		boolean willExit = !zoneArea.isPointWithin(movedCoords);
		boolean playerExit = true;
		boolean playerEnter = true;
		boolean mobExit = true;
		boolean mobEnter = true;
		for(Enum e : permissions)
		{
			if(isPlayer)
			{
				if(e == ZoneTraversal.NO_PLAYER_EXIT)
				{
					playerExit = false;
				}
				else if(e == ZoneTraversal.NO_PLAYER_ENTRY)
				{
					playerEnter = false;
				}
			}
			else
			{
				if(e == ZoneTraversal.NO_MOB_EXIT)
				{
					mobExit = false;
				}
				else if(e == ZoneTraversal.NO_MOB_ENTRY)
				{
					mobEnter = false;
				}
			}
		}
		if(isPlayer)
		{
			if(isInside && willExit && playerExit)
			{
				return true;
			}
			else if(!isInside && !willExit && playerEnter)
			{
				return true;
			}
		}
		else
		{
			if(isInside && willExit && mobExit)
			{
				return true;
			}
			else if(!isInside && !willExit && mobEnter)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Check to see whether the current number of mobs will exceed spawn restrictions for this zone.
	 * @param spawnGroup
	 * @returns True if the permissions and restrictions allow more mobs to spawn, false if not.
	 */
	public boolean canMobsSpawn(boolean spawnGroup)
	{
		if(permissions.contains(MobControl.NO_MOBS))
		{
			return false;
		}
		if(spawnGroup && currentMobCount+mobSpawnGroupMax <= mobSpawnAmountMax)
		{
			return true;
		}
		if(!spawnGroup && currentMobCount+1 <= mobSpawnAmountMax)
		{
			return true;
		}
		return false;
	}

	/**
	 * Check to see whether the point is valid for this zone, and if it is, add it to the list of specific spawn
	 * locations. Also add the "LOCATION_LIMITED" enum to the permissions list. When mobs spawn, it will use the spawn
	 * locations list instead of random locations inside.
	 * @param point
	 */
	public void addMobSpawnLocation(Point3D point)
	{
		permissions.add(MobControl.LOCATION_LIMITED);
		if(zoneArea.isPointWithin(point))
		{
			spawnLocations.add(new MobSpawnLocation(point, mobLevelMin, mobLevelMax, new ArrayList<Enum>()));
		}
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
