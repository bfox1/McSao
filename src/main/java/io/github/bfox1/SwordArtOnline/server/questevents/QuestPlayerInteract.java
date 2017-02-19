package io.github.bfox1.SwordArtOnline.server.questevents;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nullable;

/**
 * Created by bfox1 on 1/30/2017.
 */
public class QuestPlayerInteract extends Event
{
    private final EnumHand hand;
    private final ItemStack stack;
    private final BlockPos pos;
    private final EnumFacing face;
    private final Entity entity;
    public QuestPlayerInteract(PlayerInteractEvent event)
    {
        this.hand = event.getHand();
        this.stack = event.getItemStack();
        this.pos = event.getPos();
        this.face = event.getFace();
        this.entity = event.getEntity();
    }

    public EnumHand getHand()
    {
        return hand;
    }

    public ItemStack getStack()
    {
        return stack;
    }


    public BlockPos getPos()
    {
        return pos;
    }

    @Nullable
    public EnumFacing getFace()
    {
        return face;
    }

    public Entity getEntity()
    {
        return entity;
    }
}
