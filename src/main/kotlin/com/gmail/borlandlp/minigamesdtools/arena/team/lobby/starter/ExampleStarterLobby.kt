package com.gmail.borlandlp.minigamesdtools.arena.team.lobby.starter

import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.Debug.print
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools.Companion.instance
import com.gmail.borlandlp.minigamesdtools.arena.ArenaEventListener
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.ArenaLobby
import org.bukkit.entity.Player
import java.util.*

class ExampleStarterLobby : ArenaLobby(), StarterLobby {
    var listener: ArenaEventListener? = null
    private val playerList: MutableSet<Player> = HashSet()
    override val players: Set<Player>
        get() = HashSet(playerList)

    override fun onInit() {
        listener = StarterLobbyListener(this)
        try {
            teamProvider!!.arena.eventAnnouncer.register(listener as StarterLobbyListener)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun forceReleasePlayer(p: Player) {
        removePlayer(p)
    }

    override fun addPlayer(player: Player) {
        print(
            Debug.LEVEL.NOTICE,
            "Player[name:" + player.name + "] added to StarterLobby"
        )
        playerList.add(player)
        if (instance!!.hotbarAPI!!.isBindedPlayer(player)) {
            instance!!.hotbarAPI!!.unbindHotbar(player)
        }
        if (this.isHotbarEnabled) {
            instance!!.hotbarAPI!!.bindHotbar(getHotbarFor(player)!!, player)
        }
    }

    override fun removePlayer(player: Player) {
        print(
            Debug.LEVEL.NOTICE,
            "Player[name:" + player.name + "] removed from StarterLobby"
        )
        playerList.remove(player)
        if (this.isHotbarEnabled) {
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

    override fun beforeGameStarting() {
        for (player in players) {
            removePlayer(player)
        }
        teamProvider!!.arena.eventAnnouncer.unregister(listener!!)
        isEnabled = false
    }

    override fun gameEnded() {
        for (player in ArrayList(playerList)) {
            removePlayer(player)
        }
    }

    override fun update() {}
    override fun beforeRoundStarting() {}
    override fun onRoundEnd() {}
}