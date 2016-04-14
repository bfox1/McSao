package io.github.bfox1.SwordArtOnline.skill.weaponskill;

import io.github.bfox1.SwordArtOnline.skill.ISubSkillTypeIdentifier;
import io.github.bfox1.SwordArtOnline.skill.PrimarySkillType;

/**
 * Created by bfox1 on 4/6/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public enum WeaponSkillType implements ISubSkillTypeIdentifier
{

    ONE_HANDED_SWORD(0, "One-Handed Sword"),
    ONE_HANDED_CURVE_BLADE(1, "One-Handed Curved Blade"),
    ONE_HANDED_DAGGER(2, "One-Handed Dagger"),
    ONE_HANDED_RAPIER(3, "One-Handed Rapier"),
    ONE_HANDED_AXE(4, "One-Handed Axe"),
    ONE_HANDED_WAR_HAMMER(5, "One-Handed War Hammer");


    private int id;
    private String name;
    private final PrimarySkillType type;

     WeaponSkillType(int id, String name)
    {
        this.id = id;
        this.name = name;
        this.type = PrimarySkillType.WEAPON;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PrimarySkillType getPrimarySkillType() {return type;}
}
