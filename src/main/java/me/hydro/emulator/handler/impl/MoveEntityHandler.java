package me.hydro.emulator.handler.impl;

import me.hydro.emulator.Emulator;
import me.hydro.emulator.collision.Block;
import me.hydro.emulator.collision.CollisionBlockState;
import me.hydro.emulator.collision.CollisionLandable;
import me.hydro.emulator.collision.VerticalCollisionBlock;
import me.hydro.emulator.handler.MovementHandler;
import me.hydro.emulator.object.iteration.IterationHolder;
import me.hydro.emulator.util.Vector;
import me.hydro.emulator.util.mcp.AxisAlignedBB;
import me.hydro.emulator.util.mcp.BlockPos;
import me.hydro.emulator.util.mcp.MathHelper;

import java.util.ArrayList;
import java.util.List;

public class MoveEntityHandler implements MovementHandler {

    /**
     * The dogshit code inside here can be traced back to the best
     * game development studio in the world, Mojang.
     *
     * Entity#moveEntity
     */

    @Override
    public IterationHolder handle(IterationHolder iteration) {
        double x = iteration.getMotion().getMotionX();
        double y = iteration.getMotion().getMotionY();
        double z = iteration.getMotion().getMotionZ();

        double d3 = x;
        double d4 = y;
        double d5 = z;

        final AxisAlignedBB lastReportedBoundingBox = iteration.getInput().getLastReportedBoundingBox();
        final boolean edges = iteration.getInput().isSneaking() && iteration.getInput().isGround();

        if (edges) {
            iteration.getTags().add("edges");

            double magicSteppingValue = 0.05D;

            for (; x != 0.0D && iteration.getDataSupplier().getCollidingBoxes(lastReportedBoundingBox
                    .offset(x, -1.0D, 0.0D)).isEmpty(); d3 = x) {
                if (x < magicSteppingValue && x >= -magicSteppingValue) {
                    x = 0.0D;
                } else if (x > 0.0D) {
                    x -= magicSteppingValue;
                } else x += magicSteppingValue;

                d3 = x;
            }

            for (; z != 0.0D && iteration.getDataSupplier().getCollidingBoxes(lastReportedBoundingBox
                    .offset(0.0D, -1.0D, z)).isEmpty(); d5 = z) {
                if (z < magicSteppingValue && z >= -magicSteppingValue) {
                    z = 0.0D;
                } else if (z > 0.0D) {
                    z -= magicSteppingValue;
                } else z += magicSteppingValue;

                d5 = z;
            }

            for (; x != 0.0D && z != 0.0D && iteration.getDataSupplier().getCollidingBoxes(lastReportedBoundingBox
                    .offset(x, -1.0D, z)).isEmpty(); d5 = z) {
                if (x < magicSteppingValue && x >= -magicSteppingValue) {
                    x = 0.0D;
                } else if (x > 0.0D) {
                    x -= magicSteppingValue;
                } else x += magicSteppingValue;

                d3 = x;

                if (z < magicSteppingValue && z >= -magicSteppingValue) {
                    z = 0.0D;
                } else if (z > 0.0D) {
                    z -= magicSteppingValue;
                } else z += magicSteppingValue;

                d5 = z;
            }
        }

        final AxisAlignedBB bb = lastReportedBoundingBox.clone();
        final List<AxisAlignedBB> collidingBoxes = new ArrayList<>(iteration.getDataSupplier().getCollidingBoxes(bb.addCoord(x, y, z)));

        AxisAlignedBB entityBB = lastReportedBoundingBox;

        for (AxisAlignedBB axisalignedbb1 : collidingBoxes) {
            y = axisalignedbb1.calculateYOffset(entityBB, y);
        }

        entityBB = entityBB.offset(0.0D, y, 0.0D);

        for (AxisAlignedBB axisalignedbb2 : collidingBoxes) {
            x = axisalignedbb2.calculateXOffset(entityBB, x);
        }

        entityBB = entityBB.offset(x, 0.0D, 0.0D);

        for (AxisAlignedBB axisalignedbb13 : collidingBoxes) {
            z = axisalignedbb13.calculateZOffset(entityBB, z);
        }

        entityBB = entityBB.offset(0.0D, 0.0D, z);

        // Step handling
        final float stepHeight = 0.6F;
        final boolean flag1 = iteration.getInput().isGround() || d4 != y && d4 < 0.0D;

        // I am not bothering to clean up this absolute abomination of an if block
        // This is Mojang's dogshit, not mine
        if (flag1 && (d3 != x || d5 != z)) {
            iteration.getTags().add("step");

            double d11 = x;
            double d7 = y;
            double d8 = z;

            AxisAlignedBB axisalignedbb3 = entityBB;
            entityBB = bb.clone();

            y = stepHeight;

            List<AxisAlignedBB> list = new ArrayList<>(iteration.getDataSupplier().getCollidingBoxes(bb.addCoord(d3, y, d5)));

            AxisAlignedBB axisalignedbb4 = entityBB;
            AxisAlignedBB axisalignedbb5 = axisalignedbb4.addCoord(d3, 0.0D, d5);

            double d9 = y;

            for (AxisAlignedBB axisalignedbb6 : list) {
                d9 = axisalignedbb6.calculateYOffset(axisalignedbb5, d9);
            }

            axisalignedbb4 = axisalignedbb4.offset(0.0D, d9, 0.0D);
            double d15 = d3;

            for (AxisAlignedBB axisalignedbb7 : list) {
                d15 = axisalignedbb7.calculateXOffset(axisalignedbb4, d15);
            }

            axisalignedbb4 = axisalignedbb4.offset(d15, 0.0D, 0.0D);
            double d16 = d5;

            for (AxisAlignedBB axisalignedbb8 : list) {
                d16 = axisalignedbb8.calculateZOffset(axisalignedbb4, d16);
            }

            axisalignedbb4 = axisalignedbb4.offset(0.0D, 0.0D, d16);

            AxisAlignedBB axisalignedbb14 = entityBB;
            double d17 = y;

            for (AxisAlignedBB axisalignedbb9 : list) {
                d17 = axisalignedbb9.calculateYOffset(axisalignedbb14, d17);
            }

            axisalignedbb14 = axisalignedbb14.offset(0.0D, d17, 0.0D);
            double d18 = d3;

            for (AxisAlignedBB axisalignedbb10 : list) {
                d18 = axisalignedbb10.calculateXOffset(axisalignedbb14, d18);
            }

            axisalignedbb14 = axisalignedbb14.offset(d18, 0.0D, 0.0D);
            double d19 = d5;

            for (AxisAlignedBB axisalignedbb11 : list) {
                d19 = axisalignedbb11.calculateZOffset(axisalignedbb14, d19);
            }

            axisalignedbb14 = axisalignedbb14.offset(0.0D, 0.0D, d19);
            double d20 = d15 * d15 + d16 * d16;
            double d10 = d18 * d18 + d19 * d19;

            if (d20 > d10) {
                x = d15;
                z = d16;
                y = -d9;
                entityBB = axisalignedbb4;
            } else {
                x = d18;
                z = d19;
                y = -d17;
                entityBB = axisalignedbb14;
            }

            for (AxisAlignedBB axisalignedbb12 : list) {
                y = axisalignedbb12.calculateYOffset(entityBB, y);
            }

            entityBB = entityBB.offset(0.0D, y, 0.0D);

            if (d11 * d11 + d8 * d8 >= x * x + z * z) {
                x = d11;
                y = d7;
                z = d8;
                entityBB = axisalignedbb3;
            }
        }

        // We're not handling special case collisions (such as soul sand, slime, etc.). Good luck have fun :)

        final Vector predicted = resetPositionToBB(entityBB);
        final double offset = iteration.getInput().getTo().distance(predicted);

        iteration.setPredicted(predicted);
        iteration.setOffset(offset);

        final double x1 = d3;
        final double x2 = x;

        final double y1 = d4;
        final double y2 = y;

        final double z1 = d5;
        final double z2 = z;

        final AxisAlignedBB finalBounding = entityBB;

        iteration.addPostAction(emulator -> {
            final boolean collidedVertically = y1 != y2;
            final boolean collidedGround = collidedVertically && y1 < 0.0D;

            final int collisionX = MathHelper.floor_double(iteration.getPredicted().getX());
            final int collisionY = MathHelper.floor_double(iteration.getPredicted().getY() - 0.20000000298023224D);
            final int collisionZ = MathHelper.floor_double(iteration.getPredicted().getZ());

            BlockPos blockPos = new BlockPos(collisionX, collisionY, collisionZ);
            Block block = iteration.getDataSupplier().getBlockAt(blockPos);

            if (block != null) {
                /* fences need to be implemented here. good luck have fun :) */

                final Block finalBlock = iteration.getDataSupplier().getBlockAt(
                        blockPos.getX(),
                        blockPos.getY(),
                        blockPos.getZ()
                );

                if (y1 != y2 && finalBlock instanceof CollisionLandable) {
                    ((CollisionLandable)finalBlock).onLand(emulator);
                }

                if (collidedGround && !iteration.getInput().isSneaking() && finalBlock instanceof VerticalCollisionBlock) {
                    final VerticalCollisionBlock leFunnyBlock = (VerticalCollisionBlock) finalBlock;

                    leFunnyBlock.transform(emulator);
                }
            } else {
                if (y1 != y2) {
                    emulator.getMotion().setMotionY(0);
                }
            }

            if (x1 != x2) {
                emulator.getMotion().setMotionX(0.0D);
            }

            if (z1 != z2) {
                emulator.getMotion().setMotionZ(0.0D);
            }

            // #doBlockCollisions
            final BlockPos minPos = new BlockPos(finalBounding.minX + 0.001D, finalBounding.minY + 0.001D, finalBounding.minZ + 0.001D);
            final BlockPos maxPos = new BlockPos(finalBounding.maxX - 0.001D, finalBounding.maxY - 0.001D, finalBounding.maxZ - 0.001D);

            for (int i = minPos.getX(); i <= maxPos.getX(); ++i) {
                for (int j = minPos.getY(); j <= maxPos.getY(); ++j) {
                    for (int k = minPos.getZ(); k <= maxPos.getZ(); ++k) {
                        final Block collisionBlock = iteration.getDataSupplier().getBlockAt(i, j, k);

                        final boolean isCollideState = collisionBlock instanceof CollisionBlockState;

                        if (!isCollideState) continue;

                        final CollisionBlockState blockState = (CollisionBlockState) collisionBlock;
                        blockState.transform(emulator);
                    }
                }
            }

            if (x1 != x2) emulator.getMotion().setMotionX(0.0D);
            if (z1 != z2) emulator.getMotion().setMotionZ(0.0D);
            if (y1 != y2) emulator.getMotion().setMotionY(0.0D);
        });

        return iteration;
    }

    private Vector resetPositionToBB(final AxisAlignedBB bb) {
        double x = (bb.minX + bb.maxX) / 2.0D;
        double z = (bb.minZ + bb.maxZ) / 2.0D;
        double y = bb.minY;

        return new Vector(x, y, z);
    }
}
