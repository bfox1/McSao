package io.github.bfox1.SwordArtOnline.skill.requirements;

/**
 * Created by bfox1 on 4/12/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public abstract class TaskCompleted implements ITaskComplete
{
    private final int completedID;

    private final String completedName;

    public TaskCompleted(int id, String name)
    {
        this.completedID = id;
        this.completedName = name;
    }

    @Override
    public final int getCompletedID()
    {
        return this.completedID;
    }

    @Override
    public final String getCompletedName()
    {
        return this.completedName;
    }
}
