package dev.quarris.simplepings.util;

import net.minecraft.block.Block;

public class BlockData {

    private final BlockPos pos;
    private final Block block;
    private final int metadata;

    public BlockData(BlockPos pos, Block block, int metadata) {
        this.pos = pos;
        this.block = block;
        this.metadata = metadata;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public Block getBlock() {
        return this.block;
    }

    public int getMetadata() {
        return this.metadata;
    }
}
