package io.github.bfox1.SwordArtOnline.common.player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by bfox1 on 1/27/2017.
 */
public class PlayerPropertyHandler
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


    public byte[] serializeQuest()
    {
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();

        try
        {
            ObjectOutputStream sream = new ObjectOutputStream(byteOutput);

            sream.writeObject(questUniqueId);

            return byteOutput.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deserializeObject(byte[] array)
    {
        ByteArrayInputStream b = new ByteArrayInputStream(array);

        try
        {
            ObjectInputStream o = new ObjectInputStream(b);

            Object list = o.readObject();

            if(list instanceof ArrayList)
            {
                this.questUniqueId = (List<UUID>) list;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
