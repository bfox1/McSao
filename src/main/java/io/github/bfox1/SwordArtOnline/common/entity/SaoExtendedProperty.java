package io.github.bfox1.SwordArtOnline.common.entity;

import io.github.bfox1.SwordArtOnline.common.SwordArtOnline;
import io.github.bfox1.SwordArtOnline.common.network.SaoEntityPacket;
import io.github.bfox1.SwordArtOnline.playerutilities.PlayerInformation;
import io.github.bfox1.SwordArtOnline.playerutilities.WorldFunction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.util.Constants;

/**
 * Created by bfox1 on 4/17/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 *
 * This Class will contain Vital information about our Player.
 * Such information will be Quests currently active, Money, Home locations and anything of the such.
 *
 */
public class SaoExtendedProperty implements IExtendedEntityProperties
{
    public static final String IEEP_ID = "SAOIEEP";

    private final WorldFunction function;

    private final PlayerInformation information;
    public boolean containsQuestData;


    public SaoExtendedProperty(WorldFunction function, PlayerInformation information)
    {
        this.function = function;
        this.information = information;

    }

    @Override
    public void saveNBTData(NBTTagCompound compound)
    {
        NBTTagCompound propertyTag = new NBTTagCompound();

        propertyTag = function.compressData(propertyTag);
        propertyTag = information.compressData(propertyTag);


        compound.setTag(IEEP_ID, propertyTag);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound)
    {
        if(compound.hasKey(IEEP_ID, Constants.NBT.TAG_COMPOUND))
        {
            NBTTagCompound propertyTag = compound.getCompoundTag(IEEP_ID);
            WorldFunction.uncompressData(propertyTag, this);
            PlayerInformation.uncompressData(propertyTag, this);
        }

    }

    @Override
    public void init(Entity entity, World world)
    {

    }

    public static SaoExtendedProperty getData(Entity player)
    {
        return (SaoExtendedProperty)player.getExtendedProperties(IEEP_ID);
    }

    public String getIEEP_ID() {
        return IEEP_ID;
    }

    public WorldFunction getFunction() {
        return function;
    }

    public PlayerInformation getInformation() {
        return information;
    }

    private void dataChanged(World world, Entity entity)
    {

        if(!world.isRemote)
        {
            EntityTracker tracker = ((WorldServer)world).getEntityTracker();
            SaoEntityPacket packet = new SaoEntityPacket(this);

            for(EntityPlayer player : tracker.getTrackingPlayers(entity))
            {
                SwordArtOnline.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) player);
            }
        }
    }

    public void entitySpawned(World world, Entity entity)
    {
        dataChanged(world, entity);
    }
    public void playerStartedTracking(EntityPlayer player)
    {
        SwordArtOnline.NETWORK_WRAPPER.sendTo(new SaoEntityPacket(this), (EntityPlayerMP) player);
    }


}
