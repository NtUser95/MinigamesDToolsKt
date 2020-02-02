package com.gmail.borlandlp.minigamesdtools.events

import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase
import com.gmail.borlandlp.minigamesdtools.arena.Result
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class ArenaGameEndedEvent(val arena: ArenaBase, val result: com.gmail.borlandlp.minigamesdtools.arena.Result) :
    Event(), Cancellable {
    private var isCanceled = false

    override fun isCancelled(): Boolean {
        return isCanceled
    }

    override fun setCancelled(b: Boolean) {
        isCanceled = b
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }

}