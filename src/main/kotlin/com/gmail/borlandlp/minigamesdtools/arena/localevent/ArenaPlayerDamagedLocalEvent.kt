package com.gmail.borlandlp.minigamesdtools.arena.localevent

import org.bukkit.entity.Player
import org.bukkit.event.Cancellable

class ArenaPlayerDamagedLocalEvent(val player: Player, val damage: Double) : ArenaEvent(),
    Cancellable {
    private var canceled = false

    override fun isCancelled(): Boolean {
        return canceled
    }

    override fun setCancelled(b: Boolean) {
        canceled = b
    }

}