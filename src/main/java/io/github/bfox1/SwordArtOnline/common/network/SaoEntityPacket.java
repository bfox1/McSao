package io.github.bfox1.SwordArtOnline.common.network;


import io.github.bfox1.SwordArtOnline.common.player.PlayerPropertyHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.IThreadListener;
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
    private PlayerPropertyHandler handler;

    public SaoEntityPacket(PlayerPropertyHandler handler)
    {
        this.handler = handler;
        //this.information = property.getInformation();
       // this.function = property.getFunction();
    }
    @Override
    public void fromBytes(ByteBuf buf)
    {

       // this.function = readWorldFunction(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        parsePlayerInformation(buf);
        //parseWorldFunction(buf);
        while(buf.isReadable())
        {

            System.out.println(buf.readBoolean());
        }

    }

    public PlayerPropertyHandler readProperty(ByteBuf buf)
    {
        PlayerPropertyHandler handler = new PlayerPropertyHandler();
        handler.deserializeObject(buf.readBytes(0).array());
        handler.setCurrency(buf.getInt(1));
        return handler;
    }

    public void parsePlayerInformation(ByteBuf buf)
    {
       // buf.writeByte(information.getCurrency());
        buf.writeBytes(this.handler.serializeQuest());
        buf.writeInt(handler.getCurrency());
    }

    public void parseWorldFunction(ByteBuf buf)
    {
       // ByteBufUtils.writeUTF8String(buf, String.valueOf(function.getFunctionType("admin")));
        //ByteBufUtils.writeUTF8String(buf, String.valueOf(function.getFunctionType("build")));
        //ByteBufUtils.writeUTF8String(buf, String.valueOf(function.getFunctionType("plotbuild")));
       // ByteBufUtils.writeUTF8String(buf, String.valueOf(function.getFunctionType("mod")));
    }
           // function.setFunctionType("admin",Boolean.getBoolean(ByteBufUtils.readUTF8String(buf)));
           // function.setFunctionType("build",Boolean.getBoolean(ByteBufUtils.readUTF8String(buf)));
           // function.setFunctionType("plotbuild",Boolean.getBoolean(ByteBufUtils.readUTF8String(buf)));
           // function.setFunctionType("mod",Boolean.getBoolean(ByteBufUtils.readUTF8String(buf)));


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

                      //  SaoExtendedProperty property = SaoExtendedProperty.getData(playerSP);
                      //  property.getFunction().setToOldFunction(message.function);
                    }
                });
            }
            return null;
        }
    }
}
