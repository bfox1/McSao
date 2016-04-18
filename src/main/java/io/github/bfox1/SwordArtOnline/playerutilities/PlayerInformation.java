package io.github.bfox1.SwordArtOnline.playerutilities;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by bfox1 on 4/17/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class PlayerInformation
{
    private int currency;

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public NBTTagCompound compressData(NBTTagCompound compound)
    {
        compound.setInteger("currency",currency);
        return compound;
    }
}
