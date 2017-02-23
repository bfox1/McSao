package io.github.bfox1.SwordArtOnline.common.util;

/**
 * Created by Ian on 2/8/2017.
 */
public class Cuboid
{
    int width, length, height;

    public Cuboid(int width, int length, int height)
    {
        this.width = width;
        this.length = length;
        this.height = height;
    }

    public Point3D maxBoundsPoint(Point3D origin)
    {
        return new Point3D(origin.x + width, origin.y + height, origin.z + length);
    }

    public int getWidthX()
    {
        return width;
    }

    public int getLengthZ()
    {
        return length;
    }

    public int getHeightY()
    {
        return height;
    }

    /**
     * Determines whether or not this cuboid is within the other cuboid, by checking whether the cuboid's min and max bounds fall within the other cuboid's.
     *
     * @param originPoint      - This cuboid's origin point, cannot be reasonably stored within, so it must be supplied.
     * @param otherCuboid      - The cuboid you're comparing this one to.
     * @param otherOriginPoint - The other cuboid's origin point, cannot be reasonably stored within, so it must be supplied.
     * @returns true if this cuboid's bounds are completely within the other cuboid's, inclusive.
     */
    public boolean isWithin(Point3D originPoint, Cuboid otherCuboid, Point3D otherOriginPoint)
    {
        return originPoint.isEqualOrLargerThan(otherOriginPoint) && this.maxBoundsPoint(originPoint).isLessThan(otherCuboid.maxBoundsPoint(otherOriginPoint));
    }

    @Override
    public String toString()
    {
        return "Cuboid{" +
                "width=" + width +
                ", length=" + length +
                ", height=" + height +
                '}';
    }
}
