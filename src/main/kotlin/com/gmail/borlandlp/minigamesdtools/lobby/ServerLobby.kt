package com.gmail.borlandlp.minigamesdtools.lobby

import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools
import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase
import com.gmail.borlandlp.minigamesdtools.creator.DataProvider
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.Hotbar
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.*

abstract class ServerLobby {
    private val players: MutableMap<Player, ArenaBase?> =
        HashMap() // Player -> Arena[may be null -> if null - player is not arena player]
    var id: String? = null
    var spawnPoint: Location? = null
    var hotbarID: String? = null

    fun broadcastMessage(message: String) {
        for (player in players.keys) {
            if (!playerPlaysInAnyArena(player)) {
                player.sendMessage(message)
            }
        }
    }

    @Throws(Exception::class)
    fun transferPlayer(p: Player, lobby: ServerLobby): Boolean {
        Debug.print(
            Debug.LEVEL.NOTICE,
            "transfer Player[name:" + p.name + "] from ServerLobby[ID:" + id + "] to ServerLobby[ID:" + lobby.id + "]"
        )
        return if (unregisterPlayer(p)) {
            if (lobby.registerPlayer(p)) {
                true
            } else {
                registerPlayer(p)
                false
            }
        } else {
            false
        }
    }

    fun spawn(p: Player) {
        p.teleport(spawnPoint)
    }

    fun setPlayerArena(p: Player, a: ArenaBase?) {
        players[p] = a
    }

    /**
     * TODO: Зачем здесь это?
     */
    private fun applyHotbar(player: Player): Boolean {
        var hotbar: Hotbar?
        try {
            hotbar = MinigamesDTools.instance!!.hotbarCreatorHub!!
                .createHotbar(hotbarID!!, object : DataProvider() {
                    init {
                        this["player"] = player
                    }
                })
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        MinigamesDTools.instance!!.hotbarAPI!!.bindHotbar(hotbar, player)
        return true
    }

    @Throws(Exception::class)
    fun handlePlayerLeaveArena(p: Player) {
        if (!players.containsKey(p)) {
            throw Exception("Lobby[ID:" + id + "] doesn't contain a player:" + p.name)
        }
        spawn(p)
        if (hotbarID != null) {// TODO: Refactor - fire event?
            applyHotbar(p)
        }
    }

    fun registerPlayer(p: Player): Boolean {
        Debug.print(
            Debug.LEVEL.NOTICE,
            "register Player[name:" + p.name + "] in ServerLobby[ID:" + id + "]"
        )
        players[p] = null
        spawn(p)
        if (hotbarID != null) {
            if (!applyHotbar(p)) { // TODO: Refactor
                return false
            }
        }
        return true
    }

    fun unregisterPlayer(p: Player): Boolean {
        Debug.print(
            Debug.LEVEL.NOTICE,
            "unregister Player[name:" + p.name + "] in ServerLobby[ID:" + id + "]"
        )
        if (hotbarID != null) {
            try {
                MinigamesDTools.instance!!.hotbarAPI!!.unbindHotbar(p) // TODO: Refactor
            } catch (ex: Exception) {
                ex.printStackTrace()
                return false
            }
        }
        players.remove(p)
        return true
    }

    operator fun contains(p: Player?): Boolean {
        return players.containsKey(p)
    }

    fun playerPlaysInAnyArena(p: Player?): Boolean {
        return players[p] != null
    }

    abstract fun onRegister()
    abstract fun onUnregister()
    abstract fun tickUpdate()
}