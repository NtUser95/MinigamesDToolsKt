package com.gmail.borlandlp.minigamesdtools.arena.localevent

import com.gmail.borlandlp.minigamesdtools.arena.team.TeamProvider
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable

class ArenaPlayerRespawnRequestLocalEvent(val teamProvider: TeamProvider, val player: Player) : ArenaEvent(),
    Cancellable {
    private var canceled = false

    override fun isCancelled(): Boolean {
        return canceled
    }

    override fun setCancelled(b: Boolean) {
        canceled = b
    }
}