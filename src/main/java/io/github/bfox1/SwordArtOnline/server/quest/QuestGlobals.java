package io.github.bfox1.SwordArtOnline.server.quest;

import io.github.bfox1.SwordArtOnline.api.quest.EventType;
import io.github.bfox1.SwordArtOnline.common.util.ClassReference;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

/**
 * Created by bfox1 on 4/24/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public final class QuestGlobals
{
    private final Globals value;
    private final int questID;
    private final String questName;
    private int stepLocation = 0;
    private EventType eventType;
    private final String fileName;

    public QuestGlobals(Globals value, String fileName)
    {
        this.value = value;
        this.fileName = fileName;
        questID = value.get("getQuestID").call().toint();
        questName = value.get("getQuestName").call().toString();
        this.stepLocation = value.get("getStepLocation").call().toint();
        this.eventType = EventType.getType(value.get("getEventType").call().toString());

    }

    private QuestGlobals(QuestGlobals globals, Globals global)
    {
        this.value = global;
        this.fileName = globals.getFileName();
        this.questID = globals.getQuestID();
        this.questName = globals.getQuestName();
        this.stepLocation = globals.getStepLocation();
        this.eventType = globals.getEventType();
    }

    static QuestGlobals updateQuestGlobals(QuestGlobals global, Globals value)
    {
        return new QuestGlobals(global, value);
    }
    public Globals getValue() {
        return value;
    }

    int getQuestID() {
        return questID;
    }

    String getQuestName() {
        return questName;
    }

    private int getStepLocation() {
        return stepLocation;
    }

    private void setStepLocation(int stepLocation) {
        this.stepLocation = stepLocation;
    }

    private EventType getEventType()
    {
        return eventType;
    }

    /**
     * After passing through QuestEventHandler, any active quests will then get fired and will come to this method.
     * It will check the EventType of the current quest and call the function within the Quest and Fire the event.
     * @param event
     */
    void fireStepEvent(Event event)
    {

        switch (eventType)
        {
            case ITEMPICKUP :
                if(event instanceof PlayerEvent.ItemPickupEvent)
                {
                    value.get("onItemPickupEvent").call(CoerceJavaToLua.coerce(event), CoerceJavaToLua.coerce(new ClassReference()));
                    this.setStepLocation(value.get("getStepLocation").call().toint());
                    this.eventType = EventType.getType(value.get("getEventType").call().toString());
                }
                break;
            case PLAYERINTERACT:
                if(event instanceof PlayerInteractEvent)
                {
                    value.get("onPlayerInteractEvent").call(CoerceJavaToLua.coerce(event), CoerceJavaToLua.coerce(new ClassReference()));
                    this.setStepLocation(value.get("getStepLocation").call().toint());
                    this.eventType = EventType.getType(value.get("getEventType").call().toString());
                    break;
                }
            case ENTITYMOUNT:
            {
                value.get("onEntityMountEvent").call(CoerceJavaToLua.coerce(event), CoerceJavaToLua.coerce(new ClassReference()));
                this.setStepLocation(value.get("getStepLocation").call().toint());
                this.eventType = EventType.getType(value.get("getEventType").call().toString());
            }
            case PLAYERDROPS:
            {
                value.get("onPlayerDropItemEvent").call(CoerceJavaToLua.coerce(event), CoerceJavaToLua.coerce(new ClassReference()));
                this.setStepLocation(value.get("getStepLocation").call().toint());
                this.eventType = EventType.getType(value.get("getEventType").call().toString());
            }
            case PLAYEROPENCONTAINER:
            {
                doEvents("playerOpensContainerEvent", event);
            }
            case PLAYERSLEEPINBED:
            {
                doEvents("playerSleepInBedEvent", event);
            }
            case PLAYERUSEITEM:
            {
                doEvents("playerUseItemEvent", event);
            }
            default:
        }
    }


    /**
     * Simple method to fire said events.
     * @param eventName The event name found inside of Quest(Lua file
     * @param event The minecraft event being passed into the Lua event.
     */
    private void doEvents(String eventName, Event event)
    {
        value.get(eventName).call(CoerceJavaToLua.coerce(event), CoerceJavaToLua.coerce(new ClassReference()));
        this.setStepLocation(value.get("getStepLocation").call().toint());
        this.eventType = EventType.getType(value.get("getEventType").call().toString());
    }

    String getFileName() {
        return fileName;
    }
}
