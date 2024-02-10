package dev.quarris.simplepings.ping;

import cpw.mods.fml.common.network.ByteBufUtils;
import dev.quarris.simplepings.util.BlockData;
import dev.quarris.simplepings.util.BlockPos;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.UUID;

public abstract class PingInfo {

    private UUID playerId;
    private String playerName;
    private int dimension;
    private Vec3 position;

    public PingInfo(UUID playerId, String playerName, int dimension, Vec3 position) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.dimension = dimension;
        this.position = position;
    }

    public final void writeTo(ByteBuf buf) {
        buf.writeInt(this.getPingType());
        ByteBufUtils.writeUTF8String(buf, this.playerId.toString());
        ByteBufUtils.writeUTF8String(buf, this.playerName);
        buf.writeInt(this.dimension);
        buf.writeDouble(this.position.xCoord);
        buf.writeDouble(this.position.yCoord);
        buf.writeDouble(this.position.zCoord);
        this.writeExtra(buf);
    }

    public static PingInfo fromBuf(ByteBuf buf) {
        int pingType = buf.readInt();
        UUID playerId = UUID.fromString(ByteBufUtils.readUTF8String(buf));
        String playerName = ByteBufUtils.readUTF8String(buf);
        int dimension = buf.readInt();
        Vec3 position = Vec3.createVectorHelper(buf.readDouble(), buf.readDouble(), buf.readDouble());

        PingInfo pingInfo = null;
        if (pingType == 0) {
            pingInfo = new BlockPingInfo(playerId, playerName, dimension, position);
        }

        if (pingInfo == null) {
            return null;
        }

        pingInfo.readExtra(buf);
        return pingInfo;
    }

    public abstract void writeExtra(ByteBuf buf);

    public abstract void readExtra(ByteBuf buf);

    public abstract int getPingType();

    public UUID getPlayerId() {
        return this.playerId;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public int getDimension() {
        return this.dimension;
    }

    public Vec3 getPosition() {
        return this.position;
    }

    public double getX() {
        return this.position.xCoord;
    }

    public double getY() {
        return this.position.yCoord;
    }

    public double getZ() {
        return this.position.zCoord;
    }

    public static PingInfo fromRay(EntityPlayer player, MovingObjectPosition rayHit) {
        World world = player.worldObj;
        if (rayHit.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            int dimension = world.provider.dimensionId;
            BlockPos pos = new BlockPos(rayHit.blockX, rayHit.blockY, rayHit.blockZ);
            Block block = world.getBlock(pos.getX(), pos.getY(), pos.getZ());
            int metadata = world.getBlockMetadata(pos.getX(), pos.getY(), pos.getZ());
            return new BlockPingInfo(player.getUniqueID(), player.getDisplayName(), dimension, rayHit.hitVec, new BlockData(pos, block, metadata));
        }

        return null;
    }
}
