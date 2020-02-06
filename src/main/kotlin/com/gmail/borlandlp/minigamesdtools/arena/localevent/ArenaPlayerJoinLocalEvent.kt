package com.gmail.borlandlp.minigamesdtools.arena.localevent

import com.gmail.borlandlp.minigamesdtools.arena.team.TeamProvider
import org.bukkit.entity.Player

@ArenaEventHandler
class ArenaPlayerJoinLocalEvent(val player: Player, val team: TeamProvider) : ArenaEvent() {
    override fun toString(): String {
        return "{Event:" + this.javaClass.simpleName + " Player" + player + " TeamProvider:" + team + "}"
    }
}