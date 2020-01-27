package com.gmail.borlandlp.minigamesdtools.lobby

import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools
import com.gmail.borlandlp.minigamesdtools.events.ArenaPlayerEnterEvent
import com.gmail.borlandlp.minigamesdtools.events.ArenaPlayerQuitEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.logging.Level

class LobbyHubListener(private val lobbyController: LobbyHubController) : Listener {
    // Arena events
    @EventHandler
    fun onPLeaveArena(event: ArenaPlayerQuitEvent) {
        Debug.print(
            Debug.LEVEL.NOTICE,
            "Handle $event"
        )
        val serverLobby = MinigamesDTools.getInstance().lobbyHubAPI.getLobbyByPlayer(event.player)
        if (serverLobby != null) {
            try {
                serverLobby.handlePlayerLeaveArena(event.player)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            lobbyController.defaultServerLobby!!.registerPlayer(event.player)
        }
    }

    @EventHandler
    fun onPJoin(event: ArenaPlayerEnterEvent) {
        Debug.print(
            Debug.LEVEL.NOTICE,
            "Handle $event"
        )
        val serverLobby =
            MinigamesDTools.getInstance().lobbyHubAPI.getLobbyByPlayer(event.player)
        serverLobby?.setPlayerArena(event.player, event.arena)
    }

    // Spigot events
    @EventHandler(ignoreCancelled = true)
    fun onPJoin(event: PlayerJoinEvent) {
        if (!lobbyController.isStartLobbyEnabled) {
            return
        }

        lobbyController.defaultServerLobby!!.registerPlayer(event.player)
    }

    @EventHandler(ignoreCancelled = true)
    fun onPlayerKick(event: PlayerKickEvent) {
        val serverLobby =
            MinigamesDTools.getInstance().lobbyHubAPI.getLobbyByPlayer(event.player)
        serverLobby?.unregisterPlayer(event.player)
    }

    @EventHandler
    fun onLogout(event: PlayerQuitEvent) {
        val serverLobby =
            MinigamesDTools.getInstance().lobbyHubAPI.getLobbyByPlayer(event.player)
        serverLobby?.unregisterPlayer(event.player)
    }

}