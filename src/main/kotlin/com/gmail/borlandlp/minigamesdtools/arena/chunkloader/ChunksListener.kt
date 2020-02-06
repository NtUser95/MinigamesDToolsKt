package com.gmail.borlandlp.minigamesdtools.arena.chunkloader

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.ChunkUnloadEvent

class ChunksListener(val chunkLoaderController: ChunkLoaderController) : Listener {
    @EventHandler
    fun onChunkUnload(event: ChunkUnloadEvent) {
        if (chunkLoaderController.containsChunk(event.chunk)) {
            event.isCancelled = true
        }
    }
}