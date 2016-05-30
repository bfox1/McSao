package io.github.bfox1.SwordArtOnline.common;

import io.github.bfox1.SwordArtOnline.common.proxy.ServerProxy;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

/**
 * Created by bfox1 on 5/9/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class CommandSao extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "sao";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return ">;>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        if(args[0].equalsIgnoreCase("questReload"))
        {
            ServerProxy.manager.scanQuests();
        }
    }
}
