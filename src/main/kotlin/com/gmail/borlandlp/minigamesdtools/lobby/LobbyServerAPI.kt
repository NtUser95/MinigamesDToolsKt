package com.gmail.borlandlp.minigamesdtools.lobby

import org.bukkit.entity.Player

interface LobbyServerAPI {
    fun getLobbyByPlayer(player: Player): ServerLobby?
    fun getLobbyByID(id: String): ServerLobby?
    fun register(serverLobby: ServerLobby)
    fun unregister(serverLobby: ServerLobby)
}