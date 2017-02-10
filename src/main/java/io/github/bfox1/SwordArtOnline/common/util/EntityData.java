package io.github.bfox1.SwordArtOnline.common.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/**
 * Created by Ian on 2/8/2017.
 */
public class EntityData
{
    private Vec3d direction;
    private BlockPos position;
    private NBTTagCompound data;

    public EntityData(Vec3d direction, BlockPos position, NBTTagCompound data)
    {
        this.direction = direction;
        this.position = position;
        this.data = data;
    }

    public Vec3d getDirection()
    {
        return direction;
    }

    public BlockPos getPosition()
    {
        return position;
    }

    public NBTTagCompound getData()
    {
        return data;
    }
}
