package io.github.bfox1.SwordArtOnline.skill;

import io.github.bfox1.SwordArtOnline.exceptions.SkillTypeException;

import java.util.Enumeration;
import java.util.List;

/**
 * Created by bfox1 on 4/6/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class SkillObtainer
{

    private final int skillLevelUnlockedAt;
    private List<ISubSkillTypeIdentifier> requiredSkills;

    public SkillObtainer(int setUnlockedLevel)
    {
        this.skillLevelUnlockedAt = setUnlockedLevel;
    }

    public void addRequiredSkills(ISubSkillTypeIdentifier... identifier)
    {
        for(ISubSkillTypeIdentifier id: identifier)
        {
            if(!(id instanceof Enumeration))
            {
                try {
                    throw new SkillTypeException(id.getClass().getName() + " is not a valid SkillEnumType. ISubSkillIdentifier should only be used for Skill Types.");
                } catch (SkillTypeException e) {
                    e.printStackTrace();
                }
            }

            requiredSkills.add(id);
        }
    }

    public void addRequiedSkill(ISubSkillTypeIdentifier identifier)
    {
        if(!(identifier instanceof Enumeration))
        {
            try
            {
                throw new SkillTypeException(identifier.getClass().getName() + " is not a valid SkillEnumTYpe. ISubSkillIdentifier should only be used for Skill Types.");
            } catch (SkillTypeException e)
            {
                e.printStackTrace();
            }
        }

        requiredSkills.add(identifier);
    }

    public int getSkillLevelUnlockedAt() {
        return skillLevelUnlockedAt;
    }

    public List<ISubSkillTypeIdentifier> getRequiredSkills()
    {
        return this.requiredSkills;
    }
}
