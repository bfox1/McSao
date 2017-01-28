package io.github.bfox1.SwordArtOnline.common.world;

import com.google.common.collect.Lists;
import io.github.bfox1.SwordArtOnline.common.util.Reference;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;

import net.minecraft.entity.Entity;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by Earbuds on 4/13/2016.
 */


public class SAOTeleporter extends Teleporter {
    private final WorldServer worldServerInstance;
    private final Random random;
    private final Long2ObjectOpenHashMap<PortalPosition> destinationCoordinateCache = new Long2ObjectOpenHashMap<>();
    private final List<Long> destinationCoordinateKeys = Lists.<Long>newArrayList();

    public SAOTeleporter(WorldServer world) {
    	super(world);
        this.worldServerInstance = world;
        world.getWorldInfo().setSpawnX(0);
        world.getWorldInfo().setSpawnZ(0);
        world.getWorldInfo().setSpawnY(world.getChunkFromChunkCoords(0,0).getHeightValue(0,0));
        this.random = new Random(world.getSeed());
    }

    public void placeInPortal(Entity entity, float rotationYaw) {
        if(this.worldServerInstance.provider.getDimension() != Reference.saoDimensionId) {
            if(!this.placeInExistingPortal(entity, rotationYaw)) {
                this.makePortal(entity);
                this.placeInExistingPortal(entity, rotationYaw);
            }
        }else {
            int i = MathHelper.floor_double(entity.posX);
            int j = MathHelper.floor_double(entity.posY) - 1;
            int k = MathHelper.floor_double(entity.posZ);

            entity.setLocationAndAngles((double)i, (double)j, (double)k, entity.rotationYaw, 0.0F);
            entity.motionX = entity.motionY = entity.motionZ = 0.0D;
        }
    }
    
    public void removeStalePortalLocations(long worldTime) {
        if(worldTime % 100L == 0L) {
            Iterator<Long> iterator = this.destinationCoordinateKeys.iterator();
            long i = worldTime - 300L;

            while(iterator.hasNext()) {
                Long olong = (Long)iterator.next();
                PortalPosition teleporter$portalposition = (PortalPosition)this.destinationCoordinateCache.get(olong.longValue());

                if(teleporter$portalposition == null || teleporter$portalposition.lastUpdateTime < i) {
                    iterator.remove();
                    this.destinationCoordinateCache.remove(olong.longValue());
                }
            }
        }
    }

    public class PortalPosition extends BlockPos {
        public long lastUpdateTime;

        public PortalPosition(BlockPos pos, long lastUpdate) {
            super(pos.getX(), pos.getY(), pos.getZ());
            this.lastUpdateTime = lastUpdate;
        }
    }
}
