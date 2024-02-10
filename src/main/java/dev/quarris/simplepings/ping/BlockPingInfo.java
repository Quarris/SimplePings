package dev.quarris.simplepings.ping;

import cpw.mods.fml.common.network.ByteBufUtils;
import dev.quarris.simplepings.util.BlockData;
import dev.quarris.simplepings.util.BlockPos;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.util.Vec3;

import java.util.UUID;

public class BlockPingInfo extends PingInfo {

    private BlockData blockData;
    public BlockPingInfo(UUID playerId, String playerName, int dimension, Vec3 position, BlockData target) {
        super(playerId, playerName, dimension, position);
        this.blockData = target;
    }

    BlockPingInfo(UUID playerId, String playerName, int dimension, Vec3 position) {
        super(playerId, playerName, dimension, position);
    }

    @Override
    public void writeExtra(ByteBuf buf) {
        buf.writeInt(this.blockData.getPos().getX());
        buf.writeInt(this.blockData.getPos().getY());
        buf.writeInt(this.blockData.getPos().getZ());
        ByteBufUtils.writeUTF8String(buf, Block.blockRegistry.getNameForObject(this.blockData.getBlock()));
        buf.writeInt(this.blockData.getMetadata());
    }

    @Override
    public void readExtra(ByteBuf buf) {
        BlockPos pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        Block block = Block.getBlockFromName(ByteBufUtils.readUTF8String(buf));
        int metadata = buf.readInt();
        this.blockData = new BlockData(pos, block, metadata);
    }

    @Override
    public int getPingType() {
        return 0;
    }
}
