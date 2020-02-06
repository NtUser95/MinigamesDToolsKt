package com.gmail.borlandlp.minigamesdtools.arena.chunkloader

import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.World
import java.util.*

class ChunkLoaderCreator {
    @Throws(Exception::class)
    fun buildChunkLoader(
        world: World,
        minPoint: Location,
        maxPoint: Location
    ): ChunksLoader {
        val isRevertedLocations = minPoint.x > maxPoint.x || minPoint.z > maxPoint.z
        val minLoc: Location = if (isRevertedLocations) maxPoint else minPoint
        val maxLoc: Location = if (isRevertedLocations) minPoint else maxPoint

        val chunks: MutableList<Chunk> = mutableListOf()
        var x = minLoc.blockX
        while (x <= maxLoc.blockX) {
            var z = minLoc.blockZ
            while (z <= maxLoc.blockZ) {
                val chunk = world.getChunkAt(x, z)
                if (chunk != null) {
                    chunks.add(chunk)
                } else {
                    throw Exception("Error while parsing chunks for min_point:$minLoc # max_point:$maxLoc")
                }
                z += 16
            }
            x += 16
        }

        return ChunksLoader().apply {
            this.chunks = chunks
        }
    }
}