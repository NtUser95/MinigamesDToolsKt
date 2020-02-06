package com.gmail.borlandlp.minigamesdtools.arena.team

import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase
import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.ArenaLobby
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.respawn.RespawnLobby
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.spectator.SpectatorLobby
import org.bukkit.entity.Player

interface TeamProvider : ArenaPhaseComponent {
    val name: String
    fun containsFreeSlots(forAmountPlayers: Int): Boolean
    fun spawn(player: Player)
    var color: String? // refactor?
    var isFriendlyFireAllowed: Boolean
    var arena: ArenaBase
    val players: MutableSet<Player> // TODO refactor
    fun addPlayer(player: Player): Boolean
    fun removePlayer(player: Player): Boolean
    val isManageInventory: Boolean
    val isManageArmor: Boolean
    val respawnLobby: RespawnLobby?
    val spectatorLobby: SpectatorLobby?
    fun movePlayerTo(lobby: ArenaLobby, p: Player): Boolean
    fun movePlayerTo(team: TeamProvider, p: Player): Boolean
    fun setSpectate(p: Player, trueOrFalse: Boolean)
    val spectators: Set<Player>
}