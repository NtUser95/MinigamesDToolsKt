package com.gmail.borlandlp.minigamesdtools.events

import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class PlayerRequestOpenInvGUIEvent(val player: Player, val pageID: String) : Event(),
    Cancellable {
    private var isCanceled = false

    override fun isCancelled(): Boolean {
        return isCanceled
    }

    override fun setCancelled(b: Boolean) {
        isCanceled = b
    }

    override fun toString(): String {
        return "{Event:" + this.javaClass.simpleName + " Player" + player + ", pageID: " + pageID + "}"
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}