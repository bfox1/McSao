package io.github.bfox1.SwordArtOnline.common.util;

/**
 * Created by Ian on 2/8/2017.
 */
public class Point3D
{
    int x, y, z;

    public Point3D(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public boolean isEqualOrLargerThan(Point3D otherPoint)
    {
        return x >= otherPoint.x && y >= otherPoint.y && z >= otherPoint.z;
    }

    public boolean isLessThan(Point3D otherPoint)
    {
        return x < otherPoint.x && y < otherPoint.y && z < otherPoint.z;
    }

    public Point3D getNewPoint(int xOffset, int yOffset, int zOffset)
    {
        return new Point3D(x + xOffset, y + yOffset, z + zOffset);
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getZ()
    {
        return z;
    }

    @Override
    public String toString()
    {
        return "Point3D-[x=" + x + ", y=" + y + ", z=" + z + "]";
    }
}
