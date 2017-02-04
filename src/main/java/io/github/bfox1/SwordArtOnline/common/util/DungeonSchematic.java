package io.github.bfox1.SwordArtOnline.common.util;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;

import java.util.HashMap;

/**
 * Created by Ian on 2/3/2017.
 */
public class DungeonSchematic
{
    private Template schematic;
    private HashMap<Point3D, Connection> connections = new HashMap<Point3D, Connection>();
    private Cuboid boundingBox;
    /**
     * Constructor for the dungeon schematic. Will generate the hashmap of connections, and the bounds box.
     *
     * Example connection points: int[][] connectionPoints = {{2,3,4}, {5,6,7}};
     * Example connections: int[][] connections = {{9, 5}, {15, 4}};
     *
     * @param schematic - The Template that contains the list of blocks itself, will be used to generate the bounds box of the dungeon piece.
     * @param connectionPoints - Array of arrays of ints, the arrays will be three ints for x, y, z for the relative point at which a piece of the dungeon can connect.
     * @param connections - Array of arrays of ints, the arrays are the "diameter" of the connection point fcllowed by the index of the "direction" with 2-5 being North, South, West, and then East in that order.
     */
    public DungeonSchematic(Template schematic, int[][] connectionPoints, int[][] connections)
    {
        this.schematic = schematic;
        if(connectionPoints == null || connectionPoints.length < 1 || connections == null || connections.length < 1 || connectionPoints.length != connections.length)
        {
            return;
        }
        for(int i = 0; i < connectionPoints.length; i++)
        {
            int[] coords = connectionPoints[i];
            int[] connection = connections[i];
            this.connections.put(new Point3D(coords[0], coords[1], coords[2]), new Connection(connection[0], EnumFacing.VALUES[connection[1]]));
        }
        boundingBox = new Cuboid(schematic.getSize().getX(), schematic.getSize().getZ(), schematic.getSize().getY());
    }

    public void placeSchematic(World worldin, int x, int y, int z)
    {
        schematic.addBlocksToWorld(worldin, new BlockPos((double)x, (double)y, (double)z), new PlacementSettings());
    }

    /**
     * Thank you handy game dev techniques. This method uses cuboids and origin points to determine if two sets of 3d bounding boxes intersect.
     * @param otherBox
     * @param otherBoxOrigin
     * @returns true if the two cuboids intersect, false if not. Intersections have to mean that they intersect on all three axes.
     */
    public boolean boundingBoxCollision(Cuboid otherBox, Point3D otherBoxOrigin)
    {
        int box1MaxX = boundingBox.getWidthX();
        int box1MaxY = boundingBox.getHeightY();
        int box1MaxZ = boundingBox.getLengthZ();
        int box2MinX = otherBoxOrigin.x;
        int box2MinY = otherBoxOrigin.y;
        int box2MinZ = otherBoxOrigin.z;
        int box2MaxX = otherBox.getWidthX()+box2MinX;
        int box2MaxY = otherBox.getHeightY()+box2MinY;
        int box2MaxZ = otherBox.getLengthZ()+box2MinZ;
        return (0 <= box2MaxX && box1MaxX >= box2MinX) &&
                (0 <= box2MaxY && box1MaxY >= box2MinY) &&
                (0 <= box2MaxZ && box1MaxZ >= box2MinZ);
    }

    public class Point3D
    {
        int x,y,z;
        public Point3D(int x, int y, int z)
        {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

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
    }

    public class Cuboid
    {
        int width, length, height;

        public Cuboid(int width, int length, int height)
        {
            this.width = width;
            this.length = length;
            this.height = height;
        }

        public int getMaxX(int startX)
        {
            return width+startX;
        }

        public int getMaxY(int startY)
        {
            return height+startY;
        }

        public int getMaxZ(int startZ)
        {
            return length + startZ;
        }

        public int getWidthX() {
            return width;
        }

        public int getLengthZ() {
            return length;
        }

        public int getHeightY() {
            return height;
        }
    }

}