package com.gmail.borlandlp.minigamesdtools.arena.team.lobby.respawn

import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent
import org.bukkit.entity.Player

interface RespawnLobby : ArenaPhaseComponent {
    override fun update()
    fun addPlayer(player: Player)
    val readyPlayersToRespawn: Set<Player>
    val waitingPlayers: Map<Player, Long>
    fun removePlayer(player: Player)
}