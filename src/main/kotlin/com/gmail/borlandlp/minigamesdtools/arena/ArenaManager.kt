package com.gmail.borlandlp.minigamesdtools.arena

import com.gmail.borlandlp.minigamesdtools.APIComponent
import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.Debug.print
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools.Companion.instance
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools.Companion.prefix
import com.gmail.borlandlp.minigamesdtools.arena.exceptions.ArenaAlreadyAddedException
import com.gmail.borlandlp.minigamesdtools.arena.exceptions.ArenaAlreadyInitializedException
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerJoinLocalEvent
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerLeaveLocalEvent
import com.gmail.borlandlp.minigamesdtools.arena.team.TeamProvider
import com.gmail.borlandlp.minigamesdtools.conditions.PlayerCheckResult
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath
import com.gmail.borlandlp.minigamesdtools.creator.DataProvider
import com.gmail.borlandlp.minigamesdtools.events.ArenaPlayerEnterEvent
import com.gmail.borlandlp.minigamesdtools.events.ArenaPlayerQuitEvent
import com.gmail.borlandlp.minigamesdtools.party.Party
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*
import java.util.function.Consumer
import java.util.logging.Level
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.set

class ArenaManager : APIComponent, ArenaAPI {
    private val mArenas: MutableMap<String, ArenaBase> = Hashtable()
    private val reg2Load: MutableList<String> = mutableListOf()

    override fun onLoad() {
        var arena: ArenaBase?
        for (configEntity in instance!!.configProvider!!.getPoolContents(ConfigPath.ARENA_FOLDER)) {
            registerToLoad(configEntity.id)
        }
        for (arenaName in reg2Load) {
            try {
                print(Debug.LEVEL.NOTICE, "Start load arena '$arenaName'")
                arena = instance!!.arenaCreatorHub!!.createArena(arenaName, DataProvider())
                try {
                    arena.initializeComponents()
                } catch (e: ArenaAlreadyInitializedException) {
                    e.printStackTrace()
                    continue
                }
                addArena(arena)
                print(Debug.LEVEL.NOTICE, "successful load arena '$arenaName'")
            } catch (ex: Exception) {
                instance!!.logger.log(Level.WARNING, " failed to load arena $arenaName")
                ex.printStackTrace()
            }
        }
    }

    override fun onUnload() {
        for (arena in enabledArenas) {
            arena.forceDisable()
        }
    }

    override fun registerToLoad(arenaName: String) {
        reg2Load.add(arenaName)
    }

    override fun unregisterToLoad(arenaName: String) {
        reg2Load.remove(arenaName)
    }

    override fun restartArena(arenaName: String) {
        if (!mArenas.containsKey(arenaName)) {
            return
        } else if (mArenas[arenaName]!!.state != ArenaBase.STATE.ENDED) {
            mArenas[arenaName]!!.gameEnded(false)
        }
        val oldInstance = mArenas[arenaName]
        mArenas.remove(arenaName)

        val newInstance: ArenaBase = try {
            instance!!.arenaCreatorHub!!.createArena(arenaName, DataProvider())
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }
        try {
            newInstance.initializeComponents()
        } catch (e: ArenaAlreadyInitializedException) {
            e.printStackTrace()
            return
        }
        mArenas[arenaName] = newInstance
        print(
            Debug.LEVEL.NOTICE,
            "Reload arena '$arenaName', oldInstance:$oldInstance # newInstance:$newInstance"
        )
    }

    @Throws(ArenaAlreadyAddedException::class)
    override fun addArena(arena: ArenaBase) {
        for (qArena in arenas) {
            if (qArena.name.equals(arena.name, ignoreCase = true)) {
                val msg =
                    "Arena[name:" + arena.name + "] with a similar name is already registered."
                throw ArenaAlreadyAddedException(msg)
            }
        }
        mArenas[arena.name!!] = arena
    }

    override fun removeArena(arenaName: String) {
        mArenas.remove(arenaName)
    }

    override fun getArenaOf(player: Player): ArenaBase? {
        for (arenaBase in enabledArenas) {
            if (arenaBase.teamController!!.getTeamOf(player) != null) return arenaBase
        }
        return null
    }

    override fun getArenaOf(playerName: String): ArenaBase? {
        for (arenaBase in enabledArenas) {
            if (arenaBase.teamController!!.getTeamOf(playerName) != null) return arenaBase
        }
        return null
    }

    override fun getArena(arenaName: String): ArenaBase? {
        return mArenas[arenaName]
    }

    override val arenas: List<ArenaBase>
        get() = ArrayList(mArenas.values)

    override val enabledArenas: List<ArenaBase>
        get() = arenas.filter { obj: ArenaBase -> obj.isEnabled }

    override val disabledArenas: List<ArenaBase>
        get() = arenas.filter { x: ArenaBase -> !x.isEnabled }

    override fun arenaLeaveRequest(player: Player) {
        val arena = this.getArenaOf(player)
        if (arena != null) {
            arena.eventAnnouncer.announce(ArenaPlayerLeaveLocalEvent(player))
        } else {
            print(
                Debug.LEVEL.NOTICE,
                "Cant find arena for Player[name:" + player.name + "]"
            )
            return
        }
        val event = ArenaPlayerQuitEvent(arena, player)
        Bukkit.getPluginManager().callEvent(event)
        if (arena.state == ArenaBase.STATE.DELAYED_START) {
            if (arena.teamController!!.countCurrentPlayers() < arena.gameRules!!.minPlayersToStart) {
                arena.cancelDelayedStartArena()
            }
        }
    }

    override fun arenaJoinRequest(arenaName: String, party: Party): Boolean {
        val arenaBase = getArena(arenaName)
        if (arenaBase == null) {
            party.leader.sendMessage("$prefix Arena $arenaName does not exist.")
            return false
        }
        val teamControl = arenaBase.teamController
        val team = teamControl!!.teams.stream()
            .filter { t: TeamProvider -> t.containsFreeSlots(party.players.size) }
            .findFirst()
            .orElse(null)
        if (team == null) {
            party.leader.sendMessage("Arena $arenaName is full")
            return false
        }
        var result: PlayerCheckResult
        for (player in party.players) {
            result = playerCanJoin(arenaBase, player)
            if (result.result === PlayerCheckResult.CheckResult.DENIED) {
                val msg =
                    "A member of your party(" + player.name + ") does not meet the conditions of the gaming arena for the following reasons."
                party.leader.sendMessage(msg)
                result.errId.forEach(Consumer { s: String? ->
                    party.leader.sendMessage(
                        s
                    )
                })
                return false
            }
        }
        for (player in party.players) {
            arenaBase.eventAnnouncer.announce(ArenaPlayerJoinLocalEvent(player, team))
            Bukkit.getPluginManager().callEvent(ArenaPlayerEnterEvent(arenaBase, player))
        }
        if (arenaBase.teamController!!.countCurrentPlayers() >= arenaBase.gameRules!!.minPlayersToStart && arenaBase.state == ArenaBase.STATE.EMPTY) {
            arenaBase.delayedStartArena()
        }
        return true
    }

    override fun arenaJoinRequest(arenaName: String, player: Player): Boolean {
        val arenaBase = getArena(arenaName)
        if (arenaBase == null) {
            player.sendMessage("$prefix Arena $arenaName does not exist.")
            return false
        }
        val teamControl = arenaBase.teamController
        val team = teamControl!!.teams.stream()
            .filter { t: TeamProvider -> t.containsFreeSlots(1) }
            .findFirst().orElse(null)
        if (team == null) {
            player.sendMessage("Arena $arenaName is full")
            return false
        }
        // TODO: Приделать конвертацию ID в Localizer
        val result = playerCanJoin(arenaBase, player)
        if (result.result === PlayerCheckResult.CheckResult.DENIED) {
            result.errId.forEach(Consumer { s: String? -> player.sendMessage(s) })
            return false
        }
        arenaBase.eventAnnouncer.announce(ArenaPlayerJoinLocalEvent(player, team))
        Bukkit.getPluginManager().callEvent(ArenaPlayerEnterEvent(arenaBase, player))
        if (arenaBase.teamController!!.countCurrentPlayers() >= arenaBase.gameRules!!.minPlayersToStart && arenaBase.state == ArenaBase.STATE.EMPTY) {
            arenaBase.delayedStartArena()
        }
        return true
    }

    override fun disableArena(arenaName: String) {
        val arena = getArena(arenaName)
        if (arena != null) {
            arena.isEnabled = false
            Bukkit.getServer().broadcastMessage("[MinigamesDTools] Выключена арена $arenaName")
        }
    }

    override fun enableArena(arenaName: String) {
        val arena = getArena(arenaName)
        if (arena != null) {
            arena.isEnabled = true
            Bukkit.getServer().broadcastMessage("[MinigamesDTools] Запущена арена $arenaName")
        }
    }

    override fun playerCanJoin(arenaBase: ArenaBase, player: Player): PlayerCheckResult {
        return arenaBase.joinConditionsChain!!.check(player)
    }
}