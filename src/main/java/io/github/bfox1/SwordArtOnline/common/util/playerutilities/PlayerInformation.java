package io.github.bfox1.SwordArtOnline.common.util.playerutilities;


import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by bfox1 on 4/17/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class PlayerInformation
{
    private int currency;

    public static String QuestKey = "Quest";

    private List<UUID> questUniqueId = new ArrayList<UUID>();

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public void setQuestsList(List<UUID> questsList) {
        this.questUniqueId = questsList;
    }

    public List<UUID> getQuestsList()
    {
        return questUniqueId;
    }

    public void storeQuestID(UUID uuid)
    {
        this.questUniqueId.add(uuid);
    }

    public NBTTagCompound storeQuestAndSave(UUID id)
    {
        this.questUniqueId.add(id);
        return compressData(new NBTTagCompound());
    }

    public void removeQuestID(UUID uuid)
    {
        if(this.questUniqueId.contains(uuid))
        {
            this.questUniqueId.remove(uuid);
        }
    }

    public NBTTagCompound compressData(NBTTagCompound compound)
    {
        final List<UUID> list = questUniqueId;

        int id = 0;

        for(UUID uuid : list)
        {
            compound.setString(QuestKey + ":" + id, uuid.toString());
            id++;
        }

        compound.setInteger("currency",currency);

        return compound;
    }

    public static PlayerInformation uncompressData(NBTTagCompound compound)
    {
        PlayerInformation information = new PlayerInformation();
        information.setCurrency(compound.getInteger("currency"));
        int id = 0;
        List<UUID> tempList = new ArrayList<>();
        while(!compound.hasKey(QuestKey + ":" + id))
        {
            System.out.println(compound.hasKey(QuestKey + ":" + id));
            System.out.println("true");
            tempList.add(id, UUID.fromString(compound.getString(QuestKey + ":" + id)));
            id++;
        }
        information.setQuestsList(tempList);

        return information;
    }


}
