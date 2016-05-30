package io.github.bfox1.SwordArtOnline.common.event.questevents;

import io.github.bfox1.SwordArtOnline.common.items.SaoItemAbstract;
import io.github.bfox1.SwordArtOnline.common.items.swords.SaoSwordItem;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;
import org.omg.CORBA.Object;

/**
 * Created by rober on 5/21/2016.
 */
public class QuestItemPickupEvent extends PlayerEvent.ItemPickupEvent
{
    private final EntityItem pickedUpItem;
    private final EntityPlayer player;
    public QuestItemPickupEvent(EntityPlayer player, EntityItem pickedUp)
    {
        super(player, pickedUp);
        this.pickedUpItem = pickedUp;
        this.player = player;
    }

    public EntityItem getPickedUpItem() {
        return pickedUpItem;
    }

    public ItemStack getItemStack()
    {
        return pickedUpItem.getEntityItem();
    }

    public Item getItem()
    {
        return getPickedUpItem().getEntityItem().getItem();
    }

    public int getItemID()
    {
        return Item.getIdFromItem(getItem());
    }

    public EntityPlayer getPlayer()
    {
        return player;
    }


}
