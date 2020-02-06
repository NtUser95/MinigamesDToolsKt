package com.gmail.borlandlp.minigamesdtools.arena.localevent

import org.bukkit.entity.Player
import org.bukkit.event.Cancellable

@ArenaEventHandler
class ArenaPlayerDeathLocalEvent(val player: Player) : ArenaEvent(), Cancellable {
    private var canceled = false

    override fun isCancelled(): Boolean {
        return canceled
    }

    override fun setCancelled(b: Boolean) {
        canceled = b
    }

    override fun toString(): String {
        return "{Event:" + this.javaClass.simpleName + " Player" + player + "}"
    }

}