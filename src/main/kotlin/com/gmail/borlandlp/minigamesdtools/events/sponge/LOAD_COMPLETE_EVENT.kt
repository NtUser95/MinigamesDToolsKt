package com.gmail.borlandlp.minigamesdtools.events.sponge

import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class LOAD_COMPLETE_EVENT : Event() {
    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}