package com.gmail.borlandlp.minigamesdtools.arena.chunkloader

import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent
import org.bukkit.Chunk
import java.util.*

class ChunksLoader : ArenaPhaseComponent {
    var chunks: List<Chunk> = mutableListOf()

    override fun onInit() {}
    override fun beforeGameStarting() {
        for (chunk in chunks) {
            if (!chunk.isLoaded) {
                chunk.load()
            }
        }
    }

    override fun gameEnded() {}
    override fun update() {}
    override fun beforeRoundStarting() {}
    override fun onRoundEnd() {}
}