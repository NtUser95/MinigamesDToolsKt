package com.gmail.borlandlp.minigamesdtools.arena.localevent

import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable

@ArenaEventHandler
class ArenaPlayerKilledLocalEvent(val player: Player, val killer: Entity) : ArenaEvent(),  Cancellable {
    private var canceled = false

    override fun toString(): String {
        return "{Event:" + this.javaClass.simpleName + " Player" + player + " Killer:" + killer + "}"
    }

    override fun isCancelled(): Boolean {
        return canceled
    }

    override fun setCancelled(b: Boolean) {
        canceled = b
    }
}