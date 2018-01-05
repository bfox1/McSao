package io.github.bfox1.SwordArtOnline.common.util;

public class CuboidArea
{
	private int width, height, length;
	private int xStart, yStart, zStart;

	private Cuboid area;
	private Point3D originPoint;

	/**
	 * Creates a new cuboid using a set of coordinates to denote the origin point and the width, height, and length to
	 * set the bounds of the cuboid's area. It keeps the information put in, so that it can be retrieved separately from
	 * the cuboid and the origin point.
	 * @param xStart
	 * @param yStart
	 * @param zStart
	 * @param width
	 * @param height
	 * @param length
	 */
	public CuboidArea(int xStart, int yStart, int zStart, int width, int height, int length)
	{
		this.width = width;
		this.height = height;
		this.length = length;

		this.xStart = xStart;
		this.yStart = yStart;
		this.zStart = zStart;

		area = new Cuboid(width, height, length);
		originPoint = new Point3D(xStart, yStart, zStart);
	}

	public Point3D maxBoundsPoint()
	{
		return new Point3D(xStart+width, yStart+height, zStart+length);
	}

	/**
	 * Check to see whether the other cuboid area is within this one, by checking to see whether the origin point of this cuboid is greater than the other
	 * cuboid's origin point, as well as the max bounds point being less than the other cuboid max bounds point. Those two things together are required to
	 * see whether this cuboid is totally within the other cuboid.
	 * @param otherCuboid
	 * @returns true if this cuboid is totally within the other cuboid. Returns false if they are merely touching or not touching at all.
	 */
	public boolean isCuboidWithin(CuboidArea otherCuboid)
	{
		if(this.originPoint.isEqualOrLargerThan(otherCuboid.originPoint) && maxBoundsPoint().isLessThan(otherCuboid.maxBoundsPoint()))
		{
			return true;
		}
		return false;
	}

	/**
	 * Determines whether the point specified is within this cuboid. It does this by comparing the cuboid origin point to see if its numbers are less than
	 * the other point's numbers, and it also checks to see whether this cuboid's max bounds point is larger than the other point. If both of those things
	 * are true then the point should be within the cuboid.
	 * @param otherPoint
	 * @returns true if the point is inside this cuboid, false if not.
	 */
	public boolean isPointWithin(Point3D otherPoint)
	{
		if(this.originPoint.isLessThan(otherPoint) && maxBoundsPoint().isEqualOrLargerThan(otherPoint))
		{
			return true;
		}
		return false;
	}

	@Override
	public String toString()
	{
		return "CuboidArea{" +
				"xStart=" + xStart +
				", yStart=" + yStart +
				", zStart=" + zStart +
				", area=" + area +
				'}';
	}
}
