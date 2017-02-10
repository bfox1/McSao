package io.github.bfox1.SwordArtOnline.common.util;

import net.minecraft.util.EnumFacing;

/**
 * Created by Ian on 2/8/2017.
 */
public class Connection
{
    /**
     * Three sizes, 5x5, 9x9, 15x15, for simplicity for now.
     */
    int width;
    /**
     * Just the cardinal directions, not up or down. That's what we have stairs for.
     */
    EnumFacing direction;

    public Connection(int width, EnumFacing direction)
    {
        this.width = width;
        this.direction = direction;
    }

    public boolean canConnect(Connection otherConnection)
    {
        return otherConnection.direction == this.direction.getOpposite() && this.width == otherConnection.width;
    }

    public int getWidth()
    {
        return width;
    }

    public EnumFacing getDirection()
    {
        return direction;
    }

    @Override
    public String toString()
    {
        return "Connection-[width:" + width + " , direction:" + direction.toString() + "]";
    }
}
