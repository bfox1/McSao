package io.github.bfox1.SwordArtOnline.common.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

/**
 * Created by Ian on 2/8/2017.
 */
public class Schematic
{
    private int width, length, height;

    private ArrayList<BlockData> blocks = new ArrayList<>();
    private ArrayList<EntityData> entities = new ArrayList<>();

    public Schematic(int width, int length, int height, ArrayList<BlockData> blocks, ArrayList<EntityData> entities)
    {
        this.width = width;
        this.length = length;
        this.height = height;
        this.blocks = blocks;
        this.entities = entities;
    }

    public int getWidth()
    {
        return width;
    }

    public int getLength()
    {
        return length;
    }

    public int getHeight()
    {
        return height;
    }

    public ArrayList<BlockData> getBlocks()
    {
        return blocks;
    }

    public ArrayList<EntityData> getEntities()
    {
        return entities;
    }

    public ArrayList<IBlockState> getBlockStates (World world)
    {
        ArrayList<IBlockState> blockStates = new ArrayList<>();
        for(BlockData blockData : blocks)
        {
            Block block = blockData.getBlock();
            BlockPos position = blockData.getPosition();
            IBlockState blockState = block.getDefaultState();
        }
        return blockStates;
    }

}
