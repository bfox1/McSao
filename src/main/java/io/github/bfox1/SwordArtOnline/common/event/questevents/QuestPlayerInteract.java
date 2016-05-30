package io.github.bfox1.SwordArtOnline.common.event.questevents;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

/**
 * Created by rober on 5/25/2016.
 */
public class QuestPlayerInteract extends PlayerInteractEvent
{
    private final Entity entity;
    private final EntityPlayer player;
    private final PlayerInteractEvent.Action action;
    private final World world;
    private final BlockPos pos;
    private final EnumFacing face;


    public QuestPlayerInteract(PlayerInteractEvent event)
    {
        super(event.entityPlayer, event.action, event.pos, event.face, event.world);
        entity = event.entity;
        player = event.entityPlayer;
        action = event.action;
        face = event.face;

        pos = event.pos;
        world = event.world;


    }

    public Entity getEntity() {
        return entity;
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public InventoryPlayer getPlayerInventory()
    {

        return getPlayer().inventory;
    }

    public PlayerInteractEvent.Action getAction() {
        return action;
    }

    public World getWorld() {
        return world;
    }

    public BlockPos getPos() {
        return pos;
    }

    public EnumFacing getFace() {
        return face;
    }


}
