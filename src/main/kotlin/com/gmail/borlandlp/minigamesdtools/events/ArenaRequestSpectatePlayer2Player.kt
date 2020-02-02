package com.gmail.borlandlp.minigamesdtools.events

import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class ArenaRequestSpectatePlayer2Player(val playerInvoker: Player, val playerTarget: Player) : Event(),
    Cancellable {
    private var isCanceled = false

    override fun isCancelled(): Boolean {
        return isCanceled
    }

    override fun setCancelled(b: Boolean) {
        isCanceled = b
    }

    override fun toString(): String {
        return "{Event:" + this.javaClass.simpleName + " PlayerInvoker:" + playerInvoker + ", playerTarget: " + playerTarget + "}"
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }

}