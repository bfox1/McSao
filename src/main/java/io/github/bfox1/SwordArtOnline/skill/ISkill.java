package io.github.bfox1.SwordArtOnline.skill;

/**
 * Created by bfox1 on 4/5/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public interface ISkill
{
    PrimarySkillType getSkillType();

    int getSkillID();

    SkillObtainer getSkillObtainer();
}
