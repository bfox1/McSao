package io.github.bfox1.SwordArtOnline.common.world;

import io.github.bfox1.SwordArtOnline.common.util.*;
import io.github.bfox1.SwordArtOnline.init.BlockInit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.*;

/**
 * Dungeon building class that will take a few inputs, such as the schema it's possible to use to build the dungeon, the max bounds of the dungeon, and
 * the total number of pieces it can add to the dungeon. Potentially, after building pieces it could build other environmental details to add flair.
 *
 * Created by Ian on 2/6/2017.
 */
public class SAODungeonBuilder
{
    private ArrayList<DungeonSchematic> potentialPieces;
    private int maxDungeonPieces;
    private Cuboid dungeonBounds;
    private HashSet<DungeonBounds> placedBounds = new HashSet<>();
    private HashMap<Point3D, Connection> connections = new HashMap<Point3D, Connection>();
    private HashMap<Point3D, DungeonSchematic> placedPieces = new HashMap<>();
    private Point3D dungeonOrigin;

    public SAODungeonBuilder(ArrayList<DungeonSchematic> pieces, int maxDungeonPieces, Cuboid bounds)
    {
        this.potentialPieces = pieces;
        this.maxDungeonPieces = maxDungeonPieces;
        this.dungeonBounds = bounds;
        dungeonOrigin = new Point3D(0, 70, 0);
    }

    public SAODungeonBuilder(ArrayList<DungeonSchematic> pieces, int maxDungeonPieces, Cuboid bounds, Point3D point)
    {
        this.potentialPieces = pieces;
        this.maxDungeonPieces = maxDungeonPieces;
        this.dungeonBounds = bounds;
        dungeonOrigin = point;
    }


    private void placePiece(DungeonSchematic schema, Point3D pieceOrigin)
    {
        DungeonBounds bounds = new DungeonBounds(schema.getBoundingBox(), pieceOrigin);
        if(pieceOrigin.getX() < dungeonOrigin.getX() || pieceOrigin.getY() < dungeonOrigin.getY() || pieceOrigin.getZ() < dungeonOrigin.getZ() || !pieceFits(bounds))
        {
            System.out.println("Bad bounds arguments, somehow the schema is larger than the dungeon bounds.");
            return;
        }
        placedBounds.add(bounds);
        for(Point3D point : schema.getConnections().keySet())
        {
            Point3D offsetPoint = new Point3D(
                    point.getX() + pieceOrigin.getX(),
                    point.getY() + pieceOrigin.getY(),
                    point.getZ() + pieceOrigin.getZ());
            if(offsetPoint.getX() < dungeonBounds.getWidthX()-1 && offsetPoint.getZ() < dungeonBounds.getLengthZ()-1
                    && offsetPoint.getX() > 0 && offsetPoint.getZ() > 0)
            {
                connections.put(offsetPoint, schema.getConnections().get(point));
            }
        }
        placedPieces.put(pieceOrigin, schema);
    }

    /**
     * Checks to see whether the list of placed pieces interferes with the placement of the piece at the location that was given during the
     * creation of the piece. Additionally checks to see whether any of the connections available can fit the piece.
     * @param piece
     * @returns true if the piece fits within the dungeon and among all other placed pieces, false if not.
     */
    public boolean pieceFits(DungeonBounds piece)
    {
        if(!(piece.getBounds().isWithin(piece.getOrigin(), dungeonBounds, dungeonOrigin)))
        {
            System.out.println("");
            return false;
        }
        for(DungeonBounds bounds : placedBounds)
        {
            if(bounds.intersectsWith(piece))
            {
                return false;
            }
        }
        return true;
    }

    public HashMap<DungeonSchematic, DungeonBounds> getFittingPieces()
    {
        HashMap<DungeonSchematic, DungeonBounds> boundsList = new HashMap<>();
        for(Point3D connectionPoint : connections.keySet())
        {
            Connection connection = connections.get(connectionPoint);
            for(DungeonSchematic schema : potentialPieces)
            {
                for(Point3D connectionPoint2 : schema.getConnections().keySet())
                {
                    if(connection.canConnect(schema.getConnections().get(connectionPoint2)))
                    {
                        Vec3i offset = connection.getDirection().getDirectionVec();
                        int offsetX = connectionPoint.getX()+offset.getX()-connectionPoint2.getX();
                        int offsetY = connectionPoint.getY()+offset.getY()-connectionPoint2.getY();
                        int offsetZ = connectionPoint.getZ()+offset.getZ()-connectionPoint2.getZ();
                        Point3D offsetPoint = new Point3D(offsetX, offsetY, offsetZ);
                        DungeonBounds bounds = new DungeonBounds(schema.getBoundingBox(), offsetPoint);
                        if(pieceFits(bounds))
                        {
                            boundsList.put(schema, bounds);
                        }
                    }
                }
            }
        }
        return boundsList;
    }

    public static Object getKeyFromValue(Map hm, Object value)
    {
        for (Object o : hm.keySet())
        {
            if (hm.get(o).equals(value))
            {
                return o;
            }
        }
        return null;
    }


    /**
     * Based on all of the input parameters and some bounding box math to determine which connections are compatible and subsequently figure out whether any
     * available pieces fit on those connections while still fitting in the dungeon and not colliding with any existing pieces. May lag a bit, I might have
     * to optimize it later to reduce the amount of things calculating while making a dungeon.
     */
    public void buildDungeon(World world)
    {
        Random rand = new Random();

        //Find the floor, based on the overall size of the dungeon, place it on the floor or in it

        int maxHeight = 0;
        int originX = dungeonOrigin.getX();
        int originZ = dungeonOrigin.getZ();

        for(int x = 0; x < dungeonBounds.getWidthX()-1; x++)
        for(int z = 0; z < dungeonBounds.getLengthZ()-1; z++)
        {
            BlockPos ceiling = world.getHeight(new BlockPos(originX+x, 0, originZ+z));
            if(ceiling.getY() > maxHeight)
            {
                maxHeight = ceiling.getY();
            }
        }

        dungeonOrigin = new Point3D(originX, maxHeight, originZ);

        /*for(int x = 0; x < dungeonBounds.getWidthX()-1; x++)
        for(int z = 0; z < dungeonBounds.getLengthZ()-1; z++)
        {
            //Here we put dirt below the dungeon to fill in holes.
            //This may disappear into a method based on flags for an above-ground dungeon or below-ground dungeon
            BlockPos ceiling = world.getHeight(new BlockPos(originX+x, 0, originZ+z));
            for(int y = maxHeight; y > ceiling.getY(); y--)
            {
                world.setBlockState(new BlockPos(dungeonOrigin.getX()+x, dungeonOrigin.getY()+y, dungeonOrigin.getZ()+z), BlockInit.getSaoBlocks("aincrad_dirt_t1").getDefaultState());
            }
        }*/

        System.out.println(Reference.RESOURCE_DIRECTORY);

        //Place initial piece. Random type for now, may classify entrances later.
        DungeonSchematic initial = potentialPieces.get(rand.nextInt(potentialPieces.size()));
        int minX = dungeonOrigin.getX();
        int maxX = dungeonBounds.maxBoundsPoint(dungeonOrigin).getX()-initial.getBoundingBox().getWidthX();
        int minY = dungeonOrigin.getY();
        int maxY = dungeonBounds.maxBoundsPoint(dungeonOrigin).getY()-initial.getBoundingBox().getHeightY();
        int minZ = dungeonOrigin.getZ();
        int maxZ = dungeonBounds.maxBoundsPoint(dungeonOrigin).getZ()-initial.getBoundingBox().getLengthZ();
        int randomX = rand.nextInt((maxX-minX)+1)+minX;
        int randomZ = rand.nextInt((maxZ-minZ)+1)+minZ;
        Point3D initialOrigin = new Point3D(randomX, 70, randomZ);

        System.out.println("Placed dungeon at: "+initialOrigin);

        placePiece(initial, initialOrigin);

        for(int i = 1; i < maxDungeonPieces; i++)
        {
            //get list of pieces that can fit, and what orientations etc
            HashMap<DungeonSchematic, DungeonBounds> fittingPieces = getFittingPieces();
            if(fittingPieces.isEmpty())
            {
                System.out.println("No more spots to fit, we have placed "+i+" piece(s).");
                break;
            }
            ArrayList<DungeonBounds> pieces = new ArrayList<DungeonBounds>(fittingPieces.values());
            DungeonBounds randomPiece = pieces.get(rand.nextInt(pieces.size()));
            DungeonSchematic schema = (DungeonSchematic)getKeyFromValue(fittingPieces, randomPiece);
            placePiece(schema, randomPiece.getOrigin());
        }
        for(Point3D point : placedPieces.keySet())
        {
            placedPieces.get(point).placeSchematic(world, point.getX(), point.getY(), point.getZ());
        }
    }

}
