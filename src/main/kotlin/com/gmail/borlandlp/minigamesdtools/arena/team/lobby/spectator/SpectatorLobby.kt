package com.gmail.borlandlp.minigamesdtools.arena.team.lobby.spectator

import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent
import org.bukkit.entity.Player

interface SpectatorLobby : ArenaPhaseComponent {
    fun addPlayer(player: Player)
    fun removePlayer(player: Player)
    val players: MutableList<Player>
}