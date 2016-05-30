package io.github.bfox1.SwordArtOnline.common.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by rober on 5/24/2016.
 */
public class ClassReference
{

    //public  Minecraft getMinecraft()
   // {
      //  return Minecraft.getMinecraft();
   // }

    public  Item getItem(String modID, String itemName)
    {
        return GameRegistry.findItem(modID, itemName);
    }

    public  Item getItemByName(String itemName)
    {
        return GameRegistry.findItem(Reference.MODID, itemName);
    }

    public void sendPlayerMessage(String message, EntityPlayer player)
    {
        player.addChatMessage(new ChatComponentText(message));
    }
}
