package io.github.bfox1.SwordArtOnline.common.util;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;

/**
 * Created by bfox1 on 5/4/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public enum ForgeActionWrapper
{
    LEFT_CLICK_BLOCK(PlayerInteractEvent.Action.LEFT_CLICK_BLOCK),
    LEFT_CLICK_AIR(PlayerInteractEvent.Action.RIGHT_CLICK_AIR),
    RIGHT_CLICK_BLOCK(PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK),
    RIGHT_CLICK_AIR(PlayerInteractEvent.Action.RIGHT_CLICK_AIR);
    private PlayerInteractEvent.Action action;
    ForgeActionWrapper(PlayerInteractEvent.Action action)
    {
        this.action = action;
    }

    public PlayerInteractEvent.Action getAction()
    {
        return action;
    }

    public boolean compareTo(PlayerInteractEvent.Action action)
    {
        return action == this.action;
    }
}
