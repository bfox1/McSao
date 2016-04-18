package io.github.bfox1.SwordArtOnline.skill;

/**
 * Created by bfox1 on 4/13/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public abstract class Skill
{

    private final ISubSkillTypeIdentifier skillTypeIdentifier;


    public Skill(ISubSkillTypeIdentifier skillTypeIdentifier)
    {
        this.skillTypeIdentifier = skillTypeIdentifier;
        if(skillTypeIdentifier.getClass().getSimpleName().equalsIgnoreCase(PrimarySkillType.SUPPORT.getClass().getSimpleName()))
        {
            try {
                throw new Exception("Simple Error has occurred! The Constructor ISubSkillTypeIdentifier cannot be of a Primary Skill Type.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
