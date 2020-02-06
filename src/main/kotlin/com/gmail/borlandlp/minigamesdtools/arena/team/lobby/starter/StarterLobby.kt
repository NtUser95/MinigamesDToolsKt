package com.gmail.borlandlp.minigamesdtools.arena.team.lobby.starter

import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent
import org.bukkit.entity.Player

interface StarterLobby : ArenaPhaseComponent {
    var isEnabled: Boolean
    fun addPlayer(player: Player)
    fun removePlayer(player: Player)
    val players: Set<Player>
}