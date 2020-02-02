package com.gmail.borlandlp.minigamesdtools.events

import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class ArenaPlayerEnterEvent(val arena: ArenaBase, val player: Player) : Event() {

    override fun toString(): String {
        return "{Event:" + this.javaClass.simpleName + " arena:" + arena.name + ", player: " + player.name + "}"
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}