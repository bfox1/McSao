package io.github.bfox1.SwordArtOnline.quest;

import com.google.gson.JsonObject;
import io.github.bfox1.SwordArtOnline.common.entity.SaoExtendedProperty;
import io.github.bfox1.SwordArtOnline.common.util.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by bfox1 on 4/17/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class QuestManager
{
    private final HashMap<String, Quests> questTemplate;
    private final List<QuestGlobals> tempGlobals;
    public QuestManager()
    {
        this.questTemplate = new HashMap<>();
        this.tempGlobals = new ArrayList<>(1000);
        this.questTemplate.put(BossQuest.templateName, new BossQuest());
        this.questTemplate.put(MiscQuest.templateName, new MiscQuest());
        scanQuests();
    }

    public boolean scanQuests()
    {
        try {
            for (Quests quest : questTemplate.values()) {
                for (QuestGlobals tempGlobal : quest.readScriptQuests()) {
                    if (tempGlobal != null)
                        if (tempGlobals.contains(tempGlobal)) {
                            LogHelper.error(tempGlobal.getQuestName() + " With the id " + tempGlobal.getQuestID() + " has already been registered!" +
                                    " Please check QuestsList for openSkillID");
                        } else {
                            System.out.println("loaded quest " + tempGlobal.getQuestName());
                            tempGlobals.add(tempGlobal.getQuestID(), tempGlobal);
                        }
                }
            }
            System.out.println(tempGlobals.size() + " Were registered Quests!");
            tempGlobals.clear();
            return true;
        }
        catch(Exception e)
        {

            e.printStackTrace();
        }
        return false;
    }

    public final void saveQuestData(EntityPlayer player)
    {
        SaoExtendedProperty saoExtendedProperty = (SaoExtendedProperty) player.getExtendedProperties(SaoExtendedProperty.IEEP_ID);
        JsonObject obj = new JsonObject();
       /* List<QuestGlobals> globalses = saoExtendedProperty.getInformation().getQuestsList();

        for(QuestGlobals g : globalses)
        {
            if(g != null)
            {
                obj.addProperty(g.getQuestName(), g.getQuestID());
            }
        }
        try {
            FileWriter writer = new FileWriter("SaoData/" + player.getUniqueID() + ".json");
            writer.write(obj.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    public void setPlayerQuest(SaoExtendedProperty property, String questTemplate, String questName)
    {
        Quests quest = this.questTemplate.get(questTemplate);
        if(quest != null)
        {
            QuestGlobals globals = new QuestGlobals(quest.getGlobalsByName(questName), questName);
            UUID uuid = UUID.randomUUID();
            quest.storeQuestData(uuid, globals);
            property.getInformation().storeQuestID(uuid);
        }
    }

    public void callActives(Event event, SaoExtendedProperty property)
    {
        for(Quests quest : questTemplate.values())
        {
            property.getInformation().getQuestsList().stream().filter(id -> quest.getQuestGlobals().containsKey(id)).forEach(id ->
            {
                QuestGlobals globals = quest.getQuestGlobals().get(id);

                globals.fireStepEvent(event);
            });
        }
    }




}
