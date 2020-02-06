package com.gmail.borlandlp.minigamesdtools.arena.phasecomponent

import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

class PhaseComponentController {
    private val listeners: Queue<ArenaPhaseComponent> = ConcurrentLinkedQueue()
    private var currentPhase: ArenaPhase = ArenaPhase.INIT
    private var nowProduceAnnounce = false

    fun register(c: ArenaPhaseComponent) {
        if (nowProduceAnnounce) { // for recursive loading listeners at init phase.
            announceComponent(c, currentPhase)
        }
        listeners.add(c)
    }

    fun unregister(c: ArenaPhaseComponent?) {
        listeners.remove(c)
    }

    private fun announceComponent(component: ArenaPhaseComponent, phase: ArenaPhase) {
        try {
            when (phase) {
                ArenaPhase.INIT -> component.onInit()
                ArenaPhase.GAME_STARTING -> component.beforeGameStarting()
                ArenaPhase.ROUND_STARTING -> component.beforeRoundStarting()
                ArenaPhase.UPDATE -> component.update()
                ArenaPhase.ROUND_ENDING -> component.onRoundEnd()
                ArenaPhase.GAME_ENDING -> component.gameEnded()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun announceNewPhase(arenaPhase: ArenaPhase) {
        nowProduceAnnounce = true
        currentPhase = arenaPhase
        for (component in listeners) {
            announceComponent(component, currentPhase)
        }
        nowProduceAnnounce = false
    }

    enum class ArenaPhase {
        INIT, GAME_STARTING, ROUND_STARTING, UPDATE, ROUND_ENDING, GAME_ENDING
    }
}