package com.gmail.borlandlp.minigamesdtools.util

import com.gmail.borlandlp.minigamesdtools.util.geometry.GeneratedBlockSchema
import org.bukkit.Location
import org.bukkit.block.BlockFace
import java.util.*

object GeometryHelper {
    @JvmStatic
    fun generateSquare(centerBlock: Location, radius: Int, hollow: Boolean): GeneratedBlockSchema {
        val cx = centerBlock.blockX
        val cy = centerBlock.blockY
        val cz = centerBlock.blockZ
        val borderBlocks: MutableList<Location> =
            ArrayList()
        val fillerBlocks: MutableList<Location> =
            ArrayList()
        for (x in cx - radius..cx + radius) {
            for (y in cy - radius..cy + radius) {
                for (z in cz - radius..cz + radius) {
                    if (y == cy + radius || y == cy - radius || x == cx + radius || x == cx - radius || z == cz + radius || z == cz - radius) {
                        borderBlocks.add(Location(centerBlock.world, x.toDouble(), y.toDouble(), z.toDouble()))
                    } else if (!hollow) {
                        fillerBlocks.add(Location(centerBlock.world, x.toDouble(), y.toDouble(), z.toDouble()))
                    }
                }
            }
        }
        return GeneratedBlockSchema(borderBlocks, fillerBlocks)
    }

    @JvmStatic
    fun generateFlatSquare(centerBlock: Location, radius: Int, hollow: Boolean): GeneratedBlockSchema {
        val cx = centerBlock.blockX
        val cy = centerBlock.blockY
        val cz = centerBlock.blockZ
        val boundBlocks: MutableList<Location> =
            ArrayList()
        val fillerBlocks: MutableList<Location> =
            ArrayList()
        for (x in cx - radius..cx + radius) {
            for (z in cz - radius..cz + radius) {
                if (x == cx + radius || x == cx - radius || z == cz + radius || z == cz - radius) {
                    boundBlocks.add(Location(centerBlock.world, x.toDouble(), cy.toDouble(), z.toDouble()))
                } else if (!hollow) {
                    fillerBlocks.add(Location(centerBlock.world, x.toDouble(), cy.toDouble(), z.toDouble()))
                }
            }
        }
        return GeneratedBlockSchema(boundBlocks, fillerBlocks)
    }

    /*
    * ToDo: Hollow isnt used.
    * */
    fun generateCylinder(
        centerBlock: Location,
        radius: Int,
        hollow: Boolean
    ): List<Location> {
        val cx = centerBlock.blockX
        val cy = centerBlock.blockY
        val cz = centerBlock.blockZ
        val rSquared = radius * radius
        val blocks: MutableList<Location> = ArrayList()
        for (x in cx - radius..cx + radius) {
            for (y in cy - radius..cy + radius) {
                for (z in cz - radius..cz + radius) {
                    if ((cx - x) * (cx - x) + (cz - z) * (cz - z) <= rSquared) {
                        blocks.add(Location(centerBlock.world, x.toDouble(), y.toDouble(), z.toDouble()))
                    }
                }
            }
        }
        return blocks
    }

    @JvmStatic
    fun generateSphere(centerBlock: Location, radius: Int, hollow: Boolean): GeneratedBlockSchema {
        val borderBlocks: MutableList<Location> =
            ArrayList()
        val fillerBlock: MutableList<Location> =
            ArrayList()
        val bx = centerBlock.blockX
        val by = centerBlock.blockY
        val bz = centerBlock.blockZ
        for (x in bx - radius..bx + radius) {
            for (y in by - radius..by + radius) {
                for (z in bz - radius..bz + radius) {
                    val distance =
                        ((bx - x) * (bx - x) + (bz - z) * (bz - z) + (by - y) * (by - y)).toDouble()
                    if (distance < radius * radius && distance >= (radius - 1) * (radius - 1)) {
                        borderBlocks.add(Location(centerBlock.world, x.toDouble(), y.toDouble(), z.toDouble()))
                    } else if (!hollow && distance < (radius - 1) * (radius - 1)) {
                        fillerBlock.add(Location(centerBlock.world, x.toDouble(), y.toDouble(), z.toDouble()))
                    }
                }
            }
        }
        return GeneratedBlockSchema(borderBlocks, fillerBlock)
    }

    @JvmStatic
    fun generateFlatSphere(
        centerBlock: Location,
        direction: BlockFace,
        radius: Int,
        hollow: Boolean
    ): GeneratedBlockSchema {
        val borderBlocks: MutableList<Location> =
            ArrayList()
        val fillerBlocks: MutableList<Location> =
            ArrayList()
        if (direction == BlockFace.DOWN || direction == BlockFace.UP) {
            val bx = centerBlock.blockX
            val bz = centerBlock.blockZ
            for (x in bx - radius..bx + radius) {
                for (z in bz - radius..bz + radius) {
                    val distance = ((bx - x) * (bx - x) + (bz - z) * (bz - z)).toDouble()
                    if (distance < radius * radius && distance >= (radius - 1) * (radius - 1)) {
                        borderBlocks.add(
                            Location(
                                centerBlock.world,
                                x.toDouble(),
                                centerBlock.blockY.toDouble(),
                                z.toDouble()
                            )
                        )
                    } else if (!hollow && distance < (radius - 1) * (radius - 1)) {
                        fillerBlocks.add(
                            Location(
                                centerBlock.world,
                                x.toDouble(),
                                centerBlock.blockY.toDouble(),
                                z.toDouble()
                            )
                        )
                    }
                }
            }
        } else if (direction == BlockFace.EAST || direction == BlockFace.WEST) {
            val by = centerBlock.blockY
            val bz = centerBlock.blockX
            for (y in by - radius..by + radius) {
                for (z in bz - radius..bz + radius) {
                    val distance = ((by - y) * (by - y) + (bz - z) * (bz - z)).toDouble()
                    if (distance < radius * radius && distance >= (radius - 1) * (radius - 1)) {
                        borderBlocks.add(
                            Location(
                                centerBlock.world,
                                z.toDouble(),
                                y.toDouble(),
                                centerBlock.blockZ.toDouble()
                            )
                        )
                    } else if (!hollow && distance < (radius - 1) * (radius - 1)) {
                        fillerBlocks.add(
                            Location(
                                centerBlock.world,
                                z.toDouble(),
                                y.toDouble(),
                                centerBlock.blockZ.toDouble()
                            )
                        )
                    }
                }
            }
        } else {
            val by = centerBlock.blockY
            val bz = centerBlock.blockZ
            for (y in by - radius..by + radius) {
                for (z in bz - radius..bz + radius) {
                    val distance = ((by - y) * (by - y) + (bz - z) * (bz - z)).toDouble()
                    if (distance < radius * radius && distance >= (radius - 1) * (radius - 1)) {
                        borderBlocks.add(
                            Location(
                                centerBlock.world,
                                centerBlock.blockX.toDouble(),
                                y.toDouble(),
                                z.toDouble()
                            )
                        )
                    } else if (!hollow && distance < (radius - 1) * (radius - 1)) {
                        fillerBlocks.add(
                            Location(
                                centerBlock.world,
                                centerBlock.blockX.toDouble(),
                                y.toDouble(),
                                z.toDouble()
                            )
                        )
                    }
                }
            }
        }
        return GeneratedBlockSchema(borderBlocks, fillerBlocks)
    }
}