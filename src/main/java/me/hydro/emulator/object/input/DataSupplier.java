package me.hydro.emulator.object.input;

import me.hydro.emulator.util.mcp.AxisAlignedBB;

import java.util.List;

public interface DataSupplier {

    List<AxisAlignedBB> getCollidingBoxes(final AxisAlignedBB bb);
}
