package me.hydro.emulator.object.input;

import me.hydro.emulator.collision.Block;
import me.hydro.emulator.util.mcp.AxisAlignedBB;
import me.hydro.emulator.util.mcp.BlockPos;

import java.util.List;

public interface DataSupplier {

    List<AxisAlignedBB> getCollidingBoxes(final AxisAlignedBB bb);

    List<Block> getCollidingBlocks(final AxisAlignedBB bb);

    Block getBlockAt(final BlockPos blockPos);

    default Block getBlockAt(final int x, final int y, final int z) {
        return getBlockAt(new BlockPos(x, y, z));
    }
}
