package io.github.bfox1.SwordArtOnline.server.quest;

/**
 * Created by bfox1 on 4/29/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public enum EventType
{
    ITEMPICKUP("itemPickup"),
    PLAYERINTERACT("playerInteract"),
    ENTITYMOUNT("entityMount"),
    PLAYERDROPS("playerDrops"),
    PLAYEROPENCONTAINER("playerOpenContainer"),
    PLAYERSLEEPINBED("playerSleepInBed"),
    PLAYERUSEITEM("playerWakeUp"),
    ;


    private String name;
    EventType(String name)
    {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static EventType getType(String name)
    {
        for(EventType type : EventType.values())
        {
            if(type.getName().equalsIgnoreCase(name))
            {
                return type;
            }
        }
        return null;
    }
}
