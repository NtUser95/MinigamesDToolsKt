package com.gmail.borlandlp.minigamesdtools.arena

import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.Debug.print
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools.Companion.instance
import com.gmail.borlandlp.minigamesdtools.arena.chunkloader.ChunkLoaderController
import com.gmail.borlandlp.minigamesdtools.arena.customhandlers.HandlersController
import com.gmail.borlandlp.minigamesdtools.arena.exceptions.ArenaAlreadyInitializedException
import com.gmail.borlandlp.minigamesdtools.arena.exceptions.ArenaAlreadyStartedException
import com.gmail.borlandlp.minigamesdtools.arena.gui.hotbar.HotbarController
import com.gmail.borlandlp.minigamesdtools.arena.gui.providers.GUIController
import com.gmail.borlandlp.minigamesdtools.arena.phasecomponent.PhaseComponentController
import com.gmail.borlandlp.minigamesdtools.arena.scenario.ScenarioChainController
import com.gmail.borlandlp.minigamesdtools.arena.team.TeamController
import com.gmail.borlandlp.minigamesdtools.conditions.ConditionsChain
import com.gmail.borlandlp.minigamesdtools.events.ArenaGameEndedEvent
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import java.time.Instant

class ArenaBase {
    var name: String? = null
        protected set
    var isEnabled = false
    var state =
        STATE.EMPTY
    var currentRound = 1
    var gameId: String? = null
        protected set
    var gameStartTime: Long = 0
        protected set
    var roundStartTime: Long = 0
        protected set
    protected var schedulerTask: BukkitTask? = null
    protected var countdownTask: BukkitTask? = null
    protected var countdown_startTime: Long = 0
    protected var delayedCountdown_startTime: Long = 0
    protected var delayedCountdown_waitTime: Long = 5
    protected var delayedCountdown_task: BukkitTask? = null
    var eventAnnouncer = EventAnnouncer()
        protected set
    var phaseComponentController = PhaseComponentController()
        protected set
    var handlersController = HandlersController()
        protected set
    var guiController: GUIController? = null
        protected set
    var scenarioChainController: ScenarioChainController? = null
        protected set
    var teamController: TeamController? = null
        protected set
    var chunkLoaderController: ChunkLoaderController? = null
        protected set
    var hotbarController: HotbarController? = null
        protected set
    var gameRules: GameRules? = null
        protected set
    var joinConditionsChain: ConditionsChain? = null
        protected set
    private var initialized = false
    fun forceDisable() {
        gameEnded(true)
        isEnabled = false
    }

    @Throws(ArenaAlreadyInitializedException::class)
    fun initializeComponents() {
        if (initialized) {
            throw ArenaAlreadyInitializedException()
        }
        phaseComponentController.announceNewPhase(PhaseComponentController.ArenaPhase.INIT)
        initialized = true
    }

    @Throws(ArenaAlreadyStartedException::class)
    fun startArena() {
        if (state != STATE.EMPTY && state != STATE.DELAYED_START) {
            throw ArenaAlreadyStartedException()
        }
        val arenaObj = this
        arenaObj.beforeGameStarting()
        arenaObj.beforeRoundStarting()
        schedulerTask = object : BukkitRunnable() {
            override fun run() { // countdown pause
                if (arenaObj.state == STATE.COUNTDOWN || arenaObj.state == STATE.PAUSED) {
                    return
                }
                arenaObj.update()
                if (arenaObj.scenarioChainController!!.hasSignalGameEnding()) {
                    arenaObj.gameEnded(false)
                } else if (arenaObj.scenarioChainController!!.hasSignalRoundEnding()) {
                    arenaObj.roundEnded()
                    arenaObj.currentRound = arenaObj.currentRound + 1
                    arenaObj.beforeRoundStarting()
                }
            }
        }.runTaskTimer(instance, 10, 10)
    }

    fun delayedStartArena() {
        val arenaBaseObject = this
        state = STATE.DELAYED_START
        arenaBaseObject.delayedCountdown_startTime = Instant.now().epochSecond
        print(
            Debug.LEVEL.NOTICE,
            "Delayed start arena " + name
        )
        delayedCountdown_task = object : BukkitRunnable() {
            override fun run() {
                val inc =
                    (Instant.now().epochSecond - arenaBaseObject.delayedCountdown_startTime.toInt()).toInt()
                if (inc > arenaBaseObject.delayedCountdown_waitTime) {
                    try {
                        arenaBaseObject.startArena()
                    } catch (e: ArenaAlreadyStartedException) {
                        e.printStackTrace()
                    }
                    arenaBaseObject.delayedCountdown_task!!.cancel()
                }
            }
        }.runTaskTimer(instance, 0, 20)
    }

    fun cancelDelayedStartArena() {
        if (delayedCountdown_task == null || delayedCountdown_task!!.isCancelled) {
            return
        }
        print(
            Debug.LEVEL.NOTICE,
            "Cancel delayed start arena $name"
        )
        delayedCountdown_task!!.cancel()
    }

    protected fun beforeGameStarting() {
        gameStartTime = Instant.now().epochSecond
        phaseComponentController.announceNewPhase(PhaseComponentController.ArenaPhase.GAME_STARTING)
    }

    protected fun beforeRoundStarting() {
        val arenaBaseObject = this
        phaseComponentController.announceNewPhase(PhaseComponentController.ArenaPhase.ROUND_STARTING)
        state = STATE.COUNTDOWN
        arenaBaseObject.countdown_startTime = Instant.now().epochSecond
        countdownTask = object : BukkitRunnable() {
            override fun run() {
                val inc = (Instant.now().epochSecond - arenaBaseObject.countdown_startTime.toInt()).toInt()
                if (inc > arenaBaseObject.gameRules!!.beforeRoundStartingWaitDuration) {
                    arenaBaseObject.state = STATE.IN_PROGRESS
                    arenaBaseObject.roundStartTime = Instant.now().epochSecond
                    arenaBaseObject.countdownTask!!.cancel()
                }
            }
        }.runTaskTimer(instance, 0, 20)
    }

    protected fun update() {
        phaseComponentController.announceNewPhase(PhaseComponentController.ArenaPhase.UPDATE)
    }

    protected fun roundEnded() {
        phaseComponentController.announceNewPhase(PhaseComponentController.ArenaPhase.ROUND_ENDING)
    }

    fun gameEnded(force: Boolean) {
        state = STATE.ENDED
        gameStartTime = 0
        if (schedulerTask != null) schedulerTask!!.cancel()
        if (countdownTask != null) countdownTask!!.cancel()
        if (state == STATE.PAUSED) {
            return
        }
        try {
            Bukkit.getServer().pluginManager
                .callEvent(ArenaGameEndedEvent(this, Result.NORMAL))
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        phaseComponentController.announceNewPhase(PhaseComponentController.ArenaPhase.GAME_ENDING)
    }

    val timeLeft: Int
        get() = (gameRules!!.roundTime - (Instant.now().epochSecond - roundStartTime)).toInt()

    enum class STATE {
        DELAYED_START, IN_PROGRESS, COUNTDOWN, PAUSED, EMPTY, ENDED
    }

    inner class Builder {
        fun setJoinConditionsChain(joinConditionsChain: ConditionsChain?) {
            this@ArenaBase.joinConditionsChain = joinConditionsChain
        }

        fun setTeamController(t: TeamController?) {
            teamController = t
        }

        fun setGameId(gameId: String?) {
            this@ArenaBase.gameId = gameId
        }

        fun setName(name: String?) {
            this@ArenaBase.name = name
        }

        fun setHotbarController(hotbarController: HotbarController?) {
            this@ArenaBase.hotbarController = hotbarController
        }

        fun setChunkLoaderController(chunkLoaderController: ChunkLoaderController?) {
            this@ArenaBase.chunkLoaderController = chunkLoaderController
        }

        fun setScenarioChainController(scenarioChainController: ScenarioChainController?) {
            this@ArenaBase.scenarioChainController = scenarioChainController
        }

        fun setGuiController(guiController: GUIController?) {
            this@ArenaBase.guiController = guiController
        }

        fun setGameRules(rules: GameRules?) {
            gameRules = rules
        }

        val arena: ArenaBase
            get() = this@ArenaBase

        fun build(): ArenaBase {
            return this@ArenaBase
        }
    }

    companion object {
        fun newBuilder(): Builder {
            return ArenaBase().Builder()
        }
    }
}