package io.github.bfox1.SwordArtOnline.common.network;

import io.github.bfox1.SwordArtOnline.common.entity.SaoExtendedProperty;
import io.github.bfox1.SwordArtOnline.playerutilities.PlayerInformation;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by bfox1 on 4/17/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class SaoEntityPacket implements IMessage
{
    private PlayerInformation information;
    private WorldFunction function;

    public SaoEntityPacket(SaoExtendedProperty property)
    {
        this.information = property.getInformation();
        this.function = property.getFunction();
    }
    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.function = readWorldFunction(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        //parsePlayerInformation(buf);
        parseWorldFunction(buf);
        while(buf.isReadable())
        {

            System.out.println(buf.readBoolean());
        }

    }

    public void parsePlayerInformation(ByteBuf buf)
    {
        buf.writeByte(information.getCurrency());
    }

    public void parseWorldFunction(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, String.valueOf(function.getFunctionType("admin")));
        ByteBufUtils.writeUTF8String(buf, String.valueOf(function.getFunctionType("build")));
        ByteBufUtils.writeUTF8String(buf, String.valueOf(function.getFunctionType("plotbuild")));
        ByteBufUtils.writeUTF8String(buf, String.valueOf(function.getFunctionType("mod")));
    }

    public WorldFunction readWorldFunction(ByteBuf buf)
    {
        WorldFunction function = new WorldFunction();

            function.setFunctionType("admin",Boolean.getBoolean(ByteBufUtils.readUTF8String(buf)));
            function.setFunctionType("build",Boolean.getBoolean(ByteBufUtils.readUTF8String(buf)));
            function.setFunctionType("plotbuild",Boolean.getBoolean(ByteBufUtils.readUTF8String(buf)));
            function.setFunctionType("mod",Boolean.getBoolean(ByteBufUtils.readUTF8String(buf)));


        return function;
    }

    public static class SaoEntityPacketHandler implements IMessageHandler<SaoEntityPacket, IMessage>
    {

        @Override
        public IMessage onMessage(final SaoEntityPacket message, MessageContext ctx)
        {
            if(ctx.side.isClient())
            {
                final Minecraft mc = Minecraft.getMinecraft();
                IThreadListener mainThread = mc;

                mainThread.addScheduledTask(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        EntityPlayerSP playerSP = mc.thePlayer;

                        SaoExtendedProperty property = SaoExtendedProperty.getData(playerSP);
                        property.getFunction().setToOldFunction(message.function);
                    }
                });
            }
            return null;
        }
    }
}
