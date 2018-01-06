package io.github.bfox1.SwordArtOnline.server.quest;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by bfox1 on 4/17/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class Quests
{
    private final HashMap<UUID, QuestGlobals> activeQuests;
    private final String questName;
    private int objectiveNumber;
    private String questPath;


    /**
     * Basic Quest constructor for generating quests directory and sub quests paths.
     * @param questName
     * @param questPath
     */
    public Quests(String questName, String questPath)
    {

        this.activeQuests = new HashMap<>();
        this.questName = questName;
        this.questPath = questPath;
        if(validateQuestPath(questPath))
        {
           // try {
            //    throw new Exception(questPath + " is invalid! Please check your path again.");
           // } catch (Exception e) {
          //      e.printStackTrace();
          //  }
        }

    }

    /**
     * To store the quest data to activeQuest list. These are all quests that are currently active and attached to players
     * @param id
     * @param globals
     */
    public void storeQuestData(UUID id, QuestGlobals globals)
    {
        this.activeQuests.put(id, globals);
    }

    /**
     * As a Quest Template, only template Classes should EVER call this method.
     * This uses the templates of other Quests to search and load new quests of the same category.
     * @return
     */
    public List<QuestGlobals> readScriptQuests()
    {
        final File[] file = new File(questPath).listFiles();
        List<QuestGlobals> questGlobalses = new ArrayList<>();
        if(file != null)
        for(File nFile : file)
        {
            if(nFile.getName().contains(".lua"))
            {
                // Getting Global data for Quest.//

                Globals value = validateLuaValue(nFile.getName());
                if(value != null)
                {
                    questGlobalses.add(new QuestGlobals(value, nFile.getName()));
                    for(Map.Entry key : activeQuests.entrySet())
                    {
                        // Checking if Quests are active already, if so, it will be replaced and updated //
                        QuestGlobals globals = (QuestGlobals) key.getValue();
                        if(globals.getFileName().equalsIgnoreCase(nFile.getName()))
                        {
                            activeQuests.replace((UUID)key.getKey(), globals, QuestGlobals.updateQuestGlobals(globals, value));
                            break;
                        }
                    }
                }
                else
                {
                    //LogHelper.log(Level.ERROR, nFile.getName() + "is not valid and cannot be loaded!");
                }
            }
        }
        System.out.println(questGlobalses.size());
        return questGlobalses;
    }

    /**
     * Validating file as Lua. This checks ALL information and lines of code within thte Lua file
     * If errors arise, it will throw an runtime exception and move on its merry way. Returning information such as the
     * quest name and the line in which threw the exception.
     * @param questName
     * @return
     */
    private Globals validateLuaValue(String questName)
    {
        Globals globals = JsePlatform.standardGlobals();
        System.out.println(questPath + "/" + questName);
        LuaValue chunk = globals.loadfile(questPath + "/" + questName);
        chunk.call();

        return globals;
    }

    /**
     * To get the Raw Globals data from the Quest.lua
     * @param name
     * @return
     */
    public Globals getGlobalsByName(String name)
    {
        return validateLuaValue(name);
    }
    private boolean validateQuestPath(String pathName)
    {
        return new File(pathName).isDirectory();
    }


    public void testLua() throws ScriptException {

        File file = new File("SaoQuest/testQuest/");
        file.mkdirs();
        File js = null;
        try {
           js = new File("SaoQuest/testQuest", "testObjective.lua");
            js.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Globals global = JsePlatform.standardGlobals();
        Globals globalX = JsePlatform.standardGlobals();

       // global.get("testFunction");
        System.out.println();
        LuaValue val = global.loadfile("SaoQuest/testQuest/testObjective.lua");
        LuaValue second = globalX.loadfile("SaoQuest/testQuest/testObjective.lua");

        System.out.println(val);
        val.call();
        second.call();

       // LuaValue luaObj = CoerceJavaToLua.coerce(this);
        LuaValue func = global.get("setTestString");
        LuaValue sec = globalX.get("setTestString");
        sec.call("SECOND WINS");
        func.call("Testing");
        System.out.println(global.get("getTestString").call());
        System.out.println(globalX.get("getTestString").call());

    }

    public String getQuestName() {
        return questName;
    }

    public int getObjectiveNumber() {
        return objectiveNumber;
    }

    public void setObjectiveNumber(int objectiveNumber) {
        this.objectiveNumber = objectiveNumber;
    }

    public String getQuestPath() {
        return questPath;
    }

    public HashMap<UUID, QuestGlobals> getQuestGlobals()
    {
        return activeQuests;
    }
}
