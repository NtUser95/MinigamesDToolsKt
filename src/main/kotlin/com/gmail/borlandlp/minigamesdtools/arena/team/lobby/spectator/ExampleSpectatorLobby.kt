package com.gmail.borlandlp.minigamesdtools.arena.team.lobby.spectator

import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.Debug.print
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools.Companion.instance
import com.gmail.borlandlp.minigamesdtools.arena.ArenaEventListener
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.ArenaLobby
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.PlayerLocker
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import java.util.*

class ExampleSpectatorLobby : ArenaLobby(), SpectatorLobby, PlayerLocker {
    override val players: MutableList<Player> = mutableListOf()
    var isEnabled_watching = false
    private var listener: ArenaEventListener? = null

    override fun onInit() {
        listener = ExampleSpectatorLobbyListener(this)
        Bukkit.getServer().pluginManager
            .registerEvents(listener as Listener?, instance)
    }

    override fun beforeGameStarting() {}
    override fun gameEnded() {
        HandlerList.unregisterAll(listener as Listener?)
        for (player in ArrayList(players)) {
            removePlayer(player)
        }
    }

    override fun update() {}
    override fun beforeRoundStarting() {}
    override fun onRoundEnd() {}
    override fun forceReleasePlayer(p: Player) {
        removePlayer(p)
    }

    override fun addPlayer(player: Player) {
        print(
            Debug.LEVEL.NOTICE,
            "Add player[name:" + player.name + "] to Spectator lobby, arena:" + teamProvider!!.arena.name
        )
        player.teleport(spawnPoint)
        players.add(player)
        if (instance!!.hotbarAPI!!.isBindedPlayer(player)) {
            instance!!.hotbarAPI!!.unbindHotbar(player)
        }
        if (this.isHotbarEnabled) {
            instance!!.hotbarAPI!!.bindHotbar(getHotbarFor(player)!!, player)
        }
    }

    override fun removePlayer(player: Player) {
        players.remove(player)
        /*if(player.getSpectatorTarget() != null) {
            player.setSpectatorTarget(null);
        }
        player.setGameMode(GameMode.SURVIVAL);*/if (this.isHotbarEnabled) {
            instance!!.hotbarAPI!!.unbindHotbar(player)
        }
        if (teamProvider!!.arena.hotbarController!!.isEnabled) {
            try {
                instance!!.hotbarAPI!!.bindHotbar(
                    teamProvider!!.arena.hotbarController!!.buildDefaultHotbarFor(
                        player
                    ), player
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}