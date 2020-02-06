package com.gmail.borlandlp.minigamesdtools.arena.chunkloader

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools.Companion.instance
import com.gmail.borlandlp.minigamesdtools.arena.ArenaComponent
import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent
import org.bukkit.Chunk
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener

class ChunkLoaderController : ArenaComponent(), ArenaPhaseComponent {
    var loaders: Map<String, ChunksLoader> = mutableMapOf()
    private var listener: Listener? = null

    fun containsChunk(chunk: Chunk?): Boolean {
        for (loader in loaders.values) {
            if (loader.chunks.contains(chunk)) return true
        }
        return false
    }

    override fun onInit() {
        listener = ChunksListener(this)
        instance!!.server.pluginManager
            .registerEvents(listener, instance)
        for (component in loaders.values) {
            arena!!.phaseComponentController.register(component)
        }
    }

    override fun beforeGameStarting() {}
    override fun gameEnded() {
        HandlerList.unregisterAll(listener)
    }
    override fun update() {}
    override fun beforeRoundStarting() {}
    override fun onRoundEnd() {}
}