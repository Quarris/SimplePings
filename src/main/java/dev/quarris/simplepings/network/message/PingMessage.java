package dev.quarris.simplepings.network.message;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import dev.quarris.simplepings.ModConfig;
import dev.quarris.simplepings.network.PacketHandler;
import dev.quarris.simplepings.ping.PingInfo;
import dev.quarris.simplepings.ping.PingManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

public class PingMessage implements IMessage {

    private String player;
    private Vec3 position;

    public PingMessage() {
    }

    public PingMessage(Vec3 position) {
        this.position = position;
    }

    public PingMessage(String player, Vec3 position) {
        this.player = player;
        this.position = position;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(this.position.xCoord);
        buf.writeDouble(this.position.yCoord);
        buf.writeDouble(this.position.zCoord);
        buf.writeBoolean(!StringUtils.isNullOrEmpty(this.player));
        if (!StringUtils.isNullOrEmpty(this.player)) {
            ByteBufUtils.writeUTF8String(buf, this.player);
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.position = Vec3.createVectorHelper(buf.readDouble(), buf.readDouble(), buf.readDouble());
        if (buf.readBoolean()) {
            this.player = ByteBufUtils.readUTF8String(buf);
        }
    }

    public Vec3 getPingPosition() {
        return this.position;
    }

    public static class PingMessageHandler implements IMessageHandler<PingMessage, IMessage> {
        @Override
        public IMessage onMessage(PingMessage message, MessageContext ctx) {
            if (ctx.side == Side.SERVER) {
                // Broadcast the ping message to nearby players
                Vec3 position = message.getPingPosition();
                World world = ctx.getServerHandler().playerEntity.worldObj;
                double range = ModConfig.pingRange;
                List<EntityPlayer> nearbyPlayers = world.getEntitiesWithinAABB(EntityPlayer.class,
                    AxisAlignedBB.getBoundingBox(
                        position.xCoord - range, position.yCoord - range, position.zCoord - range,
                        position.xCoord + range, position.yCoord + range, position.zCoord + range));

                for (EntityPlayer player : nearbyPlayers) {
                    PacketHandler.INSTANCE.sendTo(new PingMessage(ctx.getServerHandler().playerEntity.getDisplayName(), position), (EntityPlayerMP) player);
                }
            }

            if (ctx.side == Side.CLIENT) {
                PingManager.addPing(new PingInfo(message.player, message.position, 100));
            }

            return null;
        }
    }
}
