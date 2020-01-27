package com.gmail.borlandlp.minigamesdtools.party

import org.bukkit.entity.Player

interface Party {
    fun addPlayer(player: Player)
    fun removePlayer(player: Player)
    val players: List<Player>
    var leader: Player
    fun broadcast(message: String)
    var maxSize: Int
    fun kickPlayer(player: Player)
}