package io.github.bfox1.SwordArtOnline.common.world;

import io.github.bfox1.SwordArtOnline.common.util.DungeonSchematic;
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
    private DungeonSchematic.Cuboid dungeonBounds;
    private HashSet<DungeonSchematic.DungeonBounds> placedBounds = new HashSet<>();
    private HashMap<DungeonSchematic.Point3D, DungeonSchematic.Connection> connections = new HashMap<DungeonSchematic.Point3D, DungeonSchematic.Connection>();
    private HashMap<DungeonSchematic.Point3D, DungeonSchematic> placedPieces = new HashMap<>();
    private DungeonSchematic.Point3D dungeonOrigin = new DungeonSchematic.Point3D(0, 70, 0);

    public SAODungeonBuilder(ArrayList<DungeonSchematic> pieces, int maxDungeonPieces, DungeonSchematic.Cuboid bounds)
    {
        this.potentialPieces = pieces;
        this.maxDungeonPieces = maxDungeonPieces;
        this.dungeonBounds = bounds;
    }

    private void placePiece(DungeonSchematic schema, DungeonSchematic.Point3D pieceOrigin)
    {
        DungeonSchematic.DungeonBounds bounds = new DungeonSchematic.DungeonBounds(schema.getBoundingBox(), pieceOrigin);
        if(pieceOrigin.getX() < 0 || pieceOrigin.getY() < 0 || pieceOrigin.getZ() < 0 || !pieceFits(bounds))
        {
            System.out.println("Bad bounds arguments, somehow the schema is larger than the dungeon bounds.");
            return;
        }
        placedBounds.add(bounds);
        for(DungeonSchematic.Point3D point : schema.getConnections().keySet())
        {
            DungeonSchematic.Point3D offsetPoint = new DungeonSchematic.Point3D(
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
    public boolean pieceFits(DungeonSchematic.DungeonBounds piece)
    {
        if(!(piece.getBounds().isWithin(piece.getOrigin(), dungeonBounds, dungeonOrigin)))
        {
            return false;
        }
        for(DungeonSchematic.DungeonBounds bounds : placedBounds)
        {
            if(bounds.intersectsWith(piece))
            {
                return false;
            }
        }
        return true;
    }

    public HashMap<DungeonSchematic, DungeonSchematic.DungeonBounds> getFittingPieces()
    {
        HashMap<DungeonSchematic, DungeonSchematic.DungeonBounds> boundsList = new HashMap<>();
        for(DungeonSchematic.Point3D connectionPoint : connections.keySet())
        {
            DungeonSchematic.Connection connection = connections.get(connectionPoint);
            for(DungeonSchematic schema : potentialPieces)
            {
                for(DungeonSchematic.Point3D connectionPoint2 : schema.getConnections().keySet())
                {
                    if(connection.canConnect(schema.getConnections().get(connectionPoint2)))
                    {
                        Vec3i offset = connection.getDirection().getDirectionVec();
                        int offsetX = connectionPoint.getX()+offset.getX()-connectionPoint2.getX();
                        int offsetY = connectionPoint.getY()+offset.getY()-connectionPoint2.getY();
                        int offsetZ = connectionPoint.getZ()+offset.getZ()-connectionPoint2.getZ();
                        DungeonSchematic.Point3D offsetPoint = new DungeonSchematic.Point3D(offsetX, offsetY, offsetZ);
                        DungeonSchematic.DungeonBounds bounds = new DungeonSchematic.DungeonBounds(schema.getBoundingBox(), offsetPoint);
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

    public static Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
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

        //Place initial piece. Random type for now, may classify entrances later.
        DungeonSchematic initial = potentialPieces.get(rand.nextInt(potentialPieces.size()));
        int minX = dungeonOrigin.getX();
        int maxX = dungeonBounds.maxBoundsPoint(dungeonOrigin).getX()-initial.getBoundingBox().getWidthX();
        int minY = dungeonOrigin.getY();
        int maxY = dungeonBounds.maxBoundsPoint(dungeonOrigin).getY()-initial.getBoundingBox().getHeightY();
        int minZ = dungeonOrigin.getZ();
        int maxZ = dungeonBounds.maxBoundsPoint(dungeonOrigin).getZ()-initial.getBoundingBox().getLengthZ();
        int randomX = rand.nextInt((maxX-minX)+1)+minX;
        int randomY = rand.nextInt((maxY-minY)+1)+minY;
        int randomZ = rand.nextInt((maxZ-minZ)+1)+minZ;
        DungeonSchematic.Point3D initialOrigin = new DungeonSchematic.Point3D(randomX, randomY, randomZ);

        placePiece(initial, initialOrigin);

        for(int i = 1; i < maxDungeonPieces; i++)
        {
            //get list of pieces that can fit, and what orientations etc
            HashMap<DungeonSchematic, DungeonSchematic.DungeonBounds> fittingPieces = getFittingPieces();
            if(fittingPieces.isEmpty())
            {
                break;
            }
            ArrayList<DungeonSchematic.DungeonBounds> pieces = new ArrayList<DungeonSchematic.DungeonBounds>(fittingPieces.values());
            DungeonSchematic.DungeonBounds randomPiece = pieces.get(rand.nextInt(pieces.size()));
            DungeonSchematic schema = (DungeonSchematic)getKeyFromValue(fittingPieces, randomPiece);
            placePiece(schema, randomPiece.getOrigin());
        }
        for(DungeonSchematic.Point3D point : placedPieces.keySet())
        {
            placedPieces.get(point).placeSchematic(world, point.getX(), point.getY(), point.getZ());
        }
    }

}
