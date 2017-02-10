package io.github.bfox1.SwordArtOnline.server.skill.attributes;

import io.github.bfox1.SwordArtOnline.api.attribute.IAttribute;

import java.util.UUID;

/**
 * Created by bfox1 on 2/9/2017.
 */
public class Attribute_Example implements IAttribute
{

    @Override
    public void executeAction()
    {
        /**
         * This is where the execution of this Skill attribute fires.
         * This is only a WIP skill system. more changes will be made when idea is finalized.
         */

    }

    @Override
    public int getAttributeID() {
        return 0;
    }

    @Override
    public String getAttributeName() {
        return null;
    }

    @Override
    public UUID getUniqueAttributeID() {
        return null;
    }

    @Override
    public void setAction() {

    }

    @Override
    public boolean attributeCondition() {
        return false;
    }
}
