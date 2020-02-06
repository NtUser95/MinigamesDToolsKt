package com.gmail.borlandlp.minigamesdtools.arena.customhandlers

import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent
import java.util.*

class HandlersController : ArenaPhaseComponent {
    private val handlers: MutableList<Handler> = mutableListOf()

    fun add(handler: Handler) {
        handlers.add(handler)
        handler.start()
    }

    fun remove(handler: Handler) {
        handlers.remove(handler)
        handler.stop()
    }

    override fun onInit() {}
    override fun beforeGameStarting() {}
    override fun gameEnded() {
        for (handler in handlers) {
            try {
                handler.stop()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun update() {}
    override fun beforeRoundStarting() {}
    override fun onRoundEnd() {}
}