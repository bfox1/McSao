package io.github.bfox1.SwordArtOnline.api.attribute;

import java.util.UUID;

/**
 * Created by bfox1 on 2/9/2017.
 */
public interface IAttribute {

    void executeAction();

    int getAttributeID();

    String getAttributeName();

    UUID getUniqueAttributeID();

    void setAction();

    boolean attributeCondition();
}
