package io.github.bfox1.SwordArtOnline.common.util;

/**
 * Created by Ian on 2/8/2017.
 */
public class DungeonBounds
{
    private Cuboid bounds;
    private Point3D origin;

    public DungeonBounds(Cuboid bounds, Point3D origin)
    {
        this.bounds = bounds;
        this.origin = origin;
    }

    public DungeonBounds(Cuboid bounds, int x, int y, int z)
    {
        this.bounds = bounds;
        this.origin = new Point3D(x, y, z);
    }

    public Point3D getMaxBounds()
    {
        return new Point3D(origin.getX() + bounds.getWidthX() - 1, origin.getY() + bounds.getHeightY() - 1, origin.getZ() + bounds.getLengthZ() - 1);
    }

    public Cuboid getBounds()
    {
        return bounds;
    }

    public Point3D getOrigin()
    {
        return origin;
    }

    public boolean intersectsWith(DungeonBounds otherBounds)
    {
        Point3D otherBoundsMax = otherBounds.getMaxBounds();
        Point3D boundsMax = getMaxBounds();
        return (origin.getX() <= otherBoundsMax.getX() && boundsMax.getX() >= otherBounds.getOrigin().getX()) &&
                (origin.getZ() <= otherBoundsMax.getZ() && boundsMax.getZ() >= otherBounds.getOrigin().getZ()) &&
                (origin.getY() <= otherBoundsMax.getY() && boundsMax.getY() >= otherBounds.getOrigin().getY());
    }

    @Override
    public String toString()
    {
        return ""+origin+" - "+bounds;
    }
}
