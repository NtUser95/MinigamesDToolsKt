package com.gmail.borlandlp.minigamesdtools.lobby

import com.gmail.borlandlp.minigamesdtools.APIComponent
import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath
import com.gmail.borlandlp.minigamesdtools.creator.DataProvider
import org.bukkit.Bukkit
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import java.util.*
import java.util.function.Consumer

class LobbyHubController : APIComponent, LobbyServerAPI {
    private val lobbies: MutableList<ServerLobby> = ArrayList()
    private var task: BukkitTask? = null
    private var lobbyListener: Listener? = null
    var defaultServerLobby: ServerLobby? = null
        private set
    var isStartLobbyEnabled = false
        private set

    override fun getLobbyByPlayer(player: Player): ServerLobby? {
        for (serverLobby in lobbies) {
            if (serverLobby.contains(player)) return serverLobby
        }
        return null
    }

    override fun getLobbyByID(id: String): ServerLobby? {
        for (serverLobby in lobbies) {
            if (serverLobby.id == id) return serverLobby
        }
        return null
    }

    override fun register(serverLobby: ServerLobby) {
        lobbies.add(serverLobby)
        serverLobby.onRegister()
    }

    override fun unregister(serverLobby: ServerLobby) {
        lobbies.remove(serverLobby)
        try {
            serverLobby.onUnregister()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun doWork() {
        for (serverLobby in lobbies) {
            try {
                serverLobby.tickUpdate()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    override fun onLoad() {
        val task = this
        this.task = object : BukkitRunnable() {
            override fun run() {
                task.doWork()
            }
        }.runTaskTimer(MinigamesDTools.getInstance(), 0, 20)
        lobbyListener = LobbyHubListener(this)
        Bukkit.getServer().pluginManager.registerEvents(lobbyListener, MinigamesDTools.getInstance())
        //load all serverLobby
        for (configEntity in MinigamesDTools.getInstance().configProvider.getPoolContents(ConfigPath.SERVER_LOBBY)) {
            if (configEntity.data["enabled"].toString() == "true") try {
                Debug.print(
                    Debug.LEVEL.NOTICE,
                    "Load ServerLobby[ID:" + configEntity.id + "]"
                )
                register(
                    MinigamesDTools.getInstance().lobbyCreatorHub.createLobby(
                        configEntity.id,
                        DataProvider()
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        // set default serverLobby
        var conf: ConfigurationSection? = null
        try {
            conf = MinigamesDTools.getInstance().configProvider.getEntity(ConfigPath.MAIN, "minigamesdtools")
                .data
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // а есть ли смысл в валидации на нуллпоинетер для conf? всё равно, этот случай - критичен и должен быть отловлен вышестоящим загрузчиком.
        isStartLobbyEnabled = conf!!["start_lobby.move_on_server_join"] as Boolean
        val serverLobby = getLobbyByID(conf["start_lobby.id"].toString())
        if (serverLobby != null) {
            defaultServerLobby = serverLobby
        } else {
            Debug.print(
                Debug.LEVEL.NOTICE,
                "Cant find default server lobby!"
            )
            return
        }
        // fix on 'reload' command
        for (player in Bukkit.getServer().onlinePlayers) {
            if (getLobbyByPlayer(player) == null) {
                defaultServerLobby!!.registerPlayer(player)
            }
        }
    }

    override fun onUnload() {
        task!!.cancel()
        HandlerList.unregisterAll(lobbyListener)
        ArrayList(lobbies).forEach(Consumer { l: ServerLobby ->
            unregister(
                l
            )
        })
    }
}