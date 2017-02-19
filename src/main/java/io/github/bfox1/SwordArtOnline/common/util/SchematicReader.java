package io.github.bfox1.SwordArtOnline.common.util;

import net.minecraft.block.Block;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Ian on 2/6/2017.
 */
public class SchematicReader
{

    public Schematic readSchema(String fileName)
    {
        Schematic schema = null;
        try
        {
            InputStream fis = getClass().getResourceAsStream(fileName+".schematic");
            NBTTagCompound nbtdata = CompressedStreamTools.readCompressed(fis);;

            short width = nbtdata.getShort("Width");
            short height = nbtdata.getShort("Height");
            short length = nbtdata.getShort("Length");

            byte[] blocks = nbtdata.getByteArray("Blocks");
            byte[] data = nbtdata.getByteArray("Data");

            //Not sure how to use these two just yet. The format doesn't really say how to locate the index of a tile entity, and I don't think entities are going to be part of schema
            NBTTagList entities = nbtdata.getTagList("Entities", 10);
            NBTTagList tileEntities = nbtdata.getTagList("TileEntities", 10);

            ArrayList<BlockData> blockData = new ArrayList<>();
            for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++)
            for(int z = 0; z < length; z++)
            {
                int index = (y*length + z)*width + x;
                Block block = Block.getBlockById(blocks[index]);
                int metadata = (int)data[index];
                BlockPos position = new BlockPos(x, y, z);
                BlockData blockDatum = new BlockData(position, block, metadata, null);
                blockData.add(blockDatum);
            }
            ArrayList<EntityData> entityData = new ArrayList<>();

            schema = new Schematic(width, length, height, blockData, entityData);

            fis.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Most likely the error here has come from a schematic being called in code and not being within the project, or not being found" +
                    " by our utilities in the future, check to see if that is the case.");
        }
        return schema;
    }
}