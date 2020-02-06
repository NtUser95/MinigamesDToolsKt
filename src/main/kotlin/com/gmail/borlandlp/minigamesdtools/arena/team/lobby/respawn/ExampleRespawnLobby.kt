package com.gmail.borlandlp.minigamesdtools.arena.team.lobby.respawn

import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.Debug.print
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools.Companion.instance
import com.gmail.borlandlp.minigamesdtools.arena.ArenaEventListener
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.ArenaLobby
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.PlayerLocker
import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.entity.Player
import java.time.Instant
import java.util.*

class ExampleRespawnLobby : ArenaLobby(), RespawnLobby, PlayerLocker {
    private var secondsWaiting = 0
    private val players: MutableMap<Player, Long> = Hashtable() // player -> unixtime
    private var listener: ArenaEventListener? = null
    private val bossBarMap: MutableMap<Player, BossBar> = Hashtable()

    override fun forceReleasePlayer(p: Player) {
        removePlayer(p)
    }

    override fun addPlayer(player: Player) {
        print(
            Debug.LEVEL.NOTICE,
            "Add player[name:" + player.name + "] to Respawn lobby"
        )
        player.teleport(spawnPoint)
        players[player] = Instant.now().epochSecond
        val bossBar =
            Bukkit.createBossBar("Возрождение", BarColor.RED, BarStyle.SOLID)
        bossBar.progress = 1.0
        bossBar.isVisible = true
        bossBar.addPlayer(player)
        bossBarMap[player] = bossBar
        if (instance!!.hotbarAPI!!.isBindedPlayer(player)) {
            instance!!.hotbarAPI!!.unbindHotbar(player)
        }
        if (this.isHotbarEnabled) {
            instance!!.hotbarAPI!!.bindHotbar(getHotbarFor(player)!!, player)
        }
    }

    override fun removePlayer(player: Player) {
        bossBarMap[player]!!.removePlayer(player)
        bossBarMap.remove(player)
        players.remove(player)
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

    override fun onInit() {
        listener = ExampleRespawnLobbyListener(this)
        try {
            teamProvider!!.arena.eventAnnouncer.register(listener as ExampleRespawnLobbyListener)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun beforeGameStarting() {}
    override fun gameEnded() {
        for (player in ArrayList(players.keys)) {
            removePlayer(player)
        }
    }

    // draw inventorygui and other func
    override fun update() {
        for (player in bossBarMap.keys) {
            val remainS = players[player]!! + secondsWaiting - Instant.now().epochSecond
            bossBarMap[player]!!.title = "До возрождения: $remainS с."
            bossBarMap[player]!!.progress = remainS.toDouble() / secondsWaiting.toDouble()
        }
    }

    override fun beforeRoundStarting() {}
    override fun onRoundEnd() {}
    override val readyPlayersToRespawn: Set<Player>
        get() {
            val rPlayers: MutableSet<Player> = HashSet()
            for (player in players.keys) {
                if (players[player]!! + secondsWaiting <= Instant.now().epochSecond) {
                    rPlayers.add(player)
                }
            }
            return rPlayers
        }

    override val waitingPlayers: Map<Player, Long>
        get() = Hashtable(players)

    fun setSecondsWaiting(secondsWaiting: Int) {
        this.secondsWaiting = secondsWaiting
    }
}