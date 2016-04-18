package io.github.bfox1.SwordArtOnline.common.items.swords;

import io.github.bfox1.SwordArtOnline.common.items.SaoItemAbstract;
import io.github.bfox1.SwordArtOnline.skill.weaponskill.WeaponSkillType;

/**
 * Created by bfox1 on 4/3/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class SaoSwordItem extends SaoItemAbstract
{
    private WeaponSkillType type;

    private float attackDamage;

    public SaoSwordItem(WeaponSkillType type)
    {
        this.type = type;
    }

    public WeaponSkillType getWeaponType()
    {
        return type;
    }

    @Override
    public SaoSwordItem setUnlocalizedName(String string)
    {
        return (SaoSwordItem)super.setUnlocalizedName(string);
    }




}
