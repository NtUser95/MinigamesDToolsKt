package com.gmail.borlandlp.minigamesdtools.events

import com.gmail.borlandlp.minigamesdtools.activepoints.ActivePoint
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class ActivePointDestroyedEvent(val activePoint: ActivePoint, val destroyer: Player) : Event(), Cancellable {
    private var canceled = false

    override fun isCancelled(): Boolean {
        return canceled
    }

    override fun setCancelled(b: Boolean) {
        canceled = b
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}