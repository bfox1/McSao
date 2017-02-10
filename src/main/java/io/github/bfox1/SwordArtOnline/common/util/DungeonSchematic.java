package io.github.bfox1.SwordArtOnline.common.util;

import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

import static net.minecraft.init.Blocks.WOOL;

/**
 * Created by Ian on 2/3/2017.
 */
public class DungeonSchematic
{
    /**
     * /me flips table WHY does this not have a public accessor for the list of blocks and entities?! I am sorely tempted to do away with it, copy most of the code, then
     * implement additional functionality so I never have to use it again. If we ever update, I am going to do that instead of reflection.
     */
    private Template schematic;
    private String fileName;
    private HashMap<Point3D, Connection> connections = new HashMap<Point3D, Connection>();
    private Cuboid boundingBox;
    private ArrayList<Template.BlockInfo> blocks;
    private IBlockState[][][] schema3DList;
    private HashSet<Template.BlockInfo> fullBlockList = new HashSet<Template.BlockInfo>();
    private HashSet<Template.BlockInfo> torchList = new HashSet<Template.BlockInfo>();

    /**
     * Constructor for the dungeon schematic. Will generate the hashmap of connections, and the bounds box.
     *
     * Example connection points: int[][] connectionPoints = {{2,3,4}, {5,6,7}};
     * Example connections: int[][] connections = {{9, 5}, {15, 4}};
     *
     * @param fileName - The name of the NBT file that contains the schematic.
     * @param connectionPoints - Array of arrays of ints, the arrays will be three ints for x, y, z for the relative point at which a piece of the dungeon can connect.
     * @param connections - Array of arrays of ints, the arrays are the "diameter" of the connection point fcllowed by the index of the "direction" with 2-5 being North, South, West, and then East in that order.
     */
    public DungeonSchematic(String fileName, int[][] connectionPoints, int[][] connections)
    {
        this.schematic = readSchema(fileName);

        if(schematic != null)
        {
            try
            {
                Field f = schematic.getClass().getDeclaredField("blocks"); //NoSuchFieldException
                f.setAccessible(true);
                ArrayList<Template.BlockInfo> schema = (ArrayList<Template.BlockInfo>) f.get(schematic);
                this.blocks = schema;
                this.fileName = fileName;
            }
            catch(Exception e)
            {
                System.out.println(e);
                System.out.println("Most likely the error here is from either the field within the Template class changing names, the type of the list" +
                        " we are trying to get changing types, the data storage class within the list changing types or being replaced by a new class, or" +
                        " the template class itself changing names or being replaced, which wouldn't surprise me in the least.");
            }
            boundingBox = new Cuboid(schematic.getSize().getX(), schematic.getSize().getZ(), schematic.getSize().getY());
            schema3DList = get3DSchema(blocks);
            for(Template.BlockInfo block : blocks)
            {
                if(block.blockState.isFullBlock())
                {
                    fullBlockList.add(block);
                }
                else if(block.blockState.getBlock() instanceof BlockTorch)
                {
                    torchList.add(block);
                }
            }
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
        }
        else
        {
            System.out.println("Bad schematic, see error messages above.");
        }
    }

    /**
     * Alternate, but hopefully future main constructor of the dungeon schematic. Only takes a filename. We can use specific "marker blocks" to determine the locations of connections,
     * the direction from a metadata value, and then the size of the opening by scanning the schema at that point. Therefore, no other input is required, but for it to work all the files
     * used with this constructor need to follow a standard marker block convention.
     *
     * Marker blocks will probably be added in the reference class later. For now, they are Red Wool -> North, Blue Wool -> South, Yellow Wool -> East, Orange Wool -> West.
     *
     * @param fileName - The name of the NBT file that contains the schematic.
     */
    public DungeonSchematic(String fileName)
    {
        this.schematic = readSchema(fileName);
        if (schematic != null)
        {
            try
            {
                Field f = schematic.getClass().getDeclaredField("blocks"); //NoSuchFieldException
                f.setAccessible(true);
                ArrayList<Template.BlockInfo> schema = (ArrayList<Template.BlockInfo>) f.get(schematic);
                this.blocks = schema;
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
            boundingBox = new Cuboid(schematic.getSize().getX(), schematic.getSize().getZ(), schematic.getSize().getY());
            schema3DList = get3DSchema(blocks);
            grabConnectionPoints(blocks);
        }
        else
        {
            System.out.println("Bad schematic, see error messages above.");
        }
    }

    public IBlockState[][][] get3DSchema(List<Template.BlockInfo> blocks)
    {
        IBlockState[][][] box = new IBlockState[boundingBox.getWidthX()][boundingBox.getHeightY()][boundingBox.getLengthZ()];
        for(Template.BlockInfo block : blocks)
        {
            box[block.pos.getX()][block.pos.getY()][block.pos.getZ()] = block.blockState;
        }
        return box;
    }

    public IBlockState[][][] get3DSchema()
    {
        return schema3DList.clone();
    }

    public int getSizeFromMarker(BlockPos position)
    {
        int sizeCounter = 1;
        for(int i = position.getY()+1; i < schema3DList[0].length; i++)
        {
            if(schema3DList[position.getX()][i][position.getZ()].getBlock() == Blocks.AIR)
            {
                sizeCounter++;
            }
            else
            {
                break;
            }
        }
        for(int i = position.getY()-1; i > 0; i--)
        {
            if(schema3DList[position.getX()][i][position.getZ()].getBlock() == Blocks.AIR)
            {
                sizeCounter++;
            }
            else
            {
                break;
            }
        }
        return sizeCounter;
    }

    /**
     * Checks the block type to see if it's a wool block, and then the color of the wool block to see if it's one of the colors we look for in a marker block.
     * @param block - An IBlockState pulled from the world or a schema, must be a blockstate so that it can have IProperty values.
     * @returns True if the color is red, blue, yellow, or orange, false if it is none of those colors or not a wool block.
     */
    public static boolean isMarkerBlock(IBlockState block)
    {
        if(block.getBlock().equals(Blocks.WOOL)) {
            EnumDyeColor color = block.getValue(BlockColored.COLOR);
            switch (color) {
                case RED:
                    return true;
                case BLUE:
                    return true;
                case YELLOW:
                    return true;
                case ORANGE:
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }

    /**
     * Scans the schematic for specific blocks and marks their locations, orientations, and sizes for storage in the hashmap.
     * Once it discovers a marker block and verifies the color as one of the usable colors then it attempts to create a new
     * mapping from data within the template and the 3d schema array.
     * @param blocks - The list of BlockInfo objects pulled from the template.
     */
    private void grabConnectionPoints(List<Template.BlockInfo> blocks)
    {
        for(Template.BlockInfo block : blocks)
        {
            if(block.blockState.getBlock().equals(Blocks.WOOL) && isMarkerBlock(block.blockState))
            {
                int width = getSizeFromMarker(block.pos);
                IBlockState woolBlock = block.blockState;
                int blockX = block.pos.getX();
                int blockY = block.pos.getY();
                int blockZ = block.pos.getZ();
                EnumDyeColor color = woolBlock.getValue(BlockColored.COLOR);
                switch(color)
                {
                    case RED:
                        connections.put(new Point3D(blockX, blockY, blockZ), new Connection(width, EnumFacing.NORTH));
                        break;
                    case BLUE:
                        connections.put(new Point3D(blockX, blockY, blockZ), new Connection(width, EnumFacing.SOUTH));
                        break;
                    case YELLOW:
                        connections.put(new Point3D(blockX, blockY, blockZ), new Connection(width, EnumFacing.EAST));
                        break;
                    case ORANGE:
                        connections.put(new Point3D(blockX, blockY, blockZ), new Connection(width, EnumFacing.WEST));
                        break;
                }

            }
        }
    }

    public Template readSchema (String filename)
    {
        String filePath = Reference.SCHEMATICS_DIRECTORY + filename + ".nbt";
        File file = new File(filePath);
        if(file.exists())
        {
            InputStream fileReader = null;
            try
            {
                fileReader = new FileInputStream(file);
                Template template = new Template();
                template.read(CompressedStreamTools.readCompressed(fileReader));
                return template;
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
            finally
            {
                IOUtils.closeQuietly(fileReader);
            }
        }
        else
        {
            System.out.println("File Not Found! Incorrect filename or the default directory is flawed.");
            System.out.println("Default Directory: "+Reference.SCHEMATICS_DIRECTORY);
            System.out.println("Input Filename: "+filename);
            System.out.println("Check to see whether this file is actually in the project or if it's not actually saved as an NBT file.");
            System.out.println("Praise the good and helpful error messages! (For when you know something could go horribly wrong somewhere and why)");
        }

        return null;
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

    public static class Point3D
    {
        int x,y,z;
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
            return new Point3D(x+xOffset, y+yOffset, z+zOffset);
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        @Override
        public String toString()
        {
            return "Point3D-[x="+x+", y="+y+", z="+z+"]";
        }
    }

    public static class Connection
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

        public int getWidth() {
            return width;
        }

        public EnumFacing getDirection() {
            return direction;
        }

        @Override
        public String toString()
        {
            return "Connection-[width:"+width+" , direction:"+direction.toString()+"]";
        }
    }

    public static class DungeonBounds
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
            this.origin = new Point3D(x,y,z);
        }

        public Point3D getMaxBounds()
        {
            return new Point3D(origin.getX() + bounds.getWidthX()-1, origin.getY() + bounds.getHeightY()-1, origin.getZ() + bounds.getLengthZ()-1);
        }

        public Cuboid getBounds() {
            return bounds;
        }

        public Point3D getOrigin() {
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
    }

    public static class Cuboid
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
            return new Point3D(origin.x+width, origin.y+height, origin.z+length);
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

        /**
         * Determines whether or not this cuboid is within the other cuboid, by checking whether the cuboid's min and max bounds fall within the other cuboid's.
         * @param originPoint - This cuboid's origin point, cannot be reasonably stored within, so it must be supplied.
         * @param otherCuboid - The cuboid you're comparing this one to.
         * @param otherOriginPoint - The other cuboid's origin point, cannot be reasonably stored within, so it must be supplied.
         * @returns true if this cuboid's bounds are completely within the other cuboid's, inclusive.
         */
        public boolean isWithin(Point3D originPoint, Cuboid otherCuboid, Point3D otherOriginPoint)
        {
            return originPoint.isEqualOrLargerThan(otherOriginPoint) && this.maxBoundsPoint(originPoint).isLessThan(otherCuboid.maxBoundsPoint(otherOriginPoint));
        }
    }

    public Template getSchematic() {
        return schematic;
    }

    public void setSchematic(Template schematic) {
        this.schematic = schematic;
    }

    public HashMap<Point3D, Connection> getConnections() {
        return connections;
    }

    public void setConnections(HashMap<Point3D, Connection> connections) {
        this.connections = connections;
    }

    public Cuboid getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(Cuboid boundingBox) {
        this.boundingBox = boundingBox;
    }

    public ArrayList<Template.BlockInfo> getBlocks() {
        return blocks;
    }

    public void setBlocks(ArrayList<Template.BlockInfo> blocks) {
        this.blocks = blocks;
    }

    public IBlockState[][][] getSchema3DList() {
        return schema3DList;
    }

    public void setSchema3DList(IBlockState[][][] schema3DList) {
        this.schema3DList = schema3DList;
    }

    public String getFileName() {
        return fileName;
    }

    /**
     * Attempt to uniquely copy the data within the list of BlockInfo objects, uses copy methods/constructors from BlockPos and NBTTagCompound,
     * but it doesn't need it for IBlockState because it can't be copied, each block state is unique to a position in the world and can't be accessed
     * without the world anyway.
     * @returns a non-reference copy list of the data within the block info list.
     */
    public ArrayList<Template.BlockInfo> copyBlockList()
    {
        ArrayList<Template.BlockInfo> copyList = new ArrayList<Template.BlockInfo>();
        for(Template.BlockInfo block : this.blocks)
        {
            BlockPos copyPos = new BlockPos(block.pos);
            NBTTagCompound copyNBTData = block.tileentityData.copy();
            Template.BlockInfo copyBlock = new Template.BlockInfo(copyPos, block.blockState, copyNBTData);
            copyList.add(copyBlock);
        }
        return copyList;
    }

}