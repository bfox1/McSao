package io.github.bfox1.SwordArtOnline.common.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

/**
 * Created by Ian on 2/8/2017.
 */
public class BlockData
{
    private BlockPos position;
    private Block block;
    private int metadata;
    private NBTTagCompound nbtData;

    public BlockData(BlockPos position, Block block, int metadata, NBTTagCompound nbtData)
    {
        this.position = position;
        this.block = block;
        this.metadata = metadata;
        this.nbtData = nbtData;
    }

    public Block getBlock()
    {
        return block;
    }

    public int getMetadata()
    {
        return metadata;
    }

    public BlockPos getPosition()
    {
        return position;
    }

    public NBTTagCompound getNbtData()
    {
        return nbtData;
    }
}
