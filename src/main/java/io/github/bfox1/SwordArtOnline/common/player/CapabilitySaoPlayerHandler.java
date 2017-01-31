package io.github.bfox1.SwordArtOnline.common.player;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import java.util.concurrent.Callable;

/**
 * Created by bfox1 on 1/27/2017.
 */
public class CapabilitySaoPlayerHandler
{
    @CapabilityInject(PlayerPropertyHandler.class)
    public static Capability<PlayerPropertyHandler> PLAYER_HANDLER_PROPERTIES = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(PlayerPropertyHandler.class, new Capability.IStorage<PlayerPropertyHandler>()
        {
            @Override
            public NBTBase writeNBT(Capability<PlayerPropertyHandler> capability, PlayerPropertyHandler instance, EnumFacing side)
            {

                NBTTagCompound compound = new NBTTagCompound();

                compound.setInteger("Col", instance.getCurrency());

                compound.setByteArray("Quest", instance.serializeQuest());
                return compound;
            }

            @Override
            public void readNBT(Capability<PlayerPropertyHandler> capability, PlayerPropertyHandler instance, EnumFacing side, NBTBase base)
            {
                if(base instanceof NBTTagCompound)
                {
                    NBTTagCompound compound = (NBTTagCompound)base;

                    instance.setCurrency(compound.getInteger("Col"));
                    instance.deserializeObject(compound.getByteArray("Quest"));
                }
            }
        }, new Callable<PlayerPropertyHandler>()
        {
            @Override
            public PlayerPropertyHandler call() throws Exception
            {
                return new PlayerPropertyHandler();
            }
        });

    }
}
