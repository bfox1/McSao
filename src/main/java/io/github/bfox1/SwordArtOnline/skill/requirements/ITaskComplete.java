package io.github.bfox1.SwordArtOnline.skill.requirements;

/**
 * Created by bfox1 on 4/12/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 *
 * This interface is intended to declare that the Class implemented with this can Fire a SkillComplete to any Player who
 * was apart of this TaskCompleted Task.
 */
public interface ITaskComplete
{
    /**
     * The Completed Tasks Name. Simple enough :)
     * @return The Task Name.
     */
    String getCompletedName();

    /**
     * All SkillTasks WILL require a Completed ID number. There can never be 2 of the same.
     * @return The Task ID.
     */
    int getCompletedID();

    /**
     * Sends the Player(s) a Completed Task to Unknown.class to evaluate the Completed task and
     * determine which skills will be unlocked or close. if anything will just save as a Text to Player indicating
     * The player has accomplished this task.
     */
    void sendCompletion();

}
