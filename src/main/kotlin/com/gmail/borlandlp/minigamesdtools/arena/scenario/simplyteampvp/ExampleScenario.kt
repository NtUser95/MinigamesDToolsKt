package com.gmail.borlandlp.minigamesdtools.arena.scenario.simplyteampvp

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools.Companion.instance
import com.gmail.borlandlp.minigamesdtools.arena.ArenaEventListener
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaEventHandler
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerJoinLocalEvent
import com.gmail.borlandlp.minigamesdtools.arena.scenario.ScenarioAbstract

class ExampleScenario : ScenarioAbstract() {
    override val isDone = false
    private var listener: ArenaEventListener? = null

    override fun update() {}
    override fun onInit() {}
    override fun beforeGameStarting() {
        listener = ExampleScenarioListener(this)
        try {
            arena!!.eventAnnouncer.register(listener as ExampleScenarioListener)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            instance!!.activePointsAPI!!.activatePoint(
                instance!!.activePointsAPI!!.searchPointByID(
                    "example_sphereactivepoint"
                )!!
            )
            instance!!.activePointsAPI!!.activatePoint(
                instance!!.activePointsAPI!!.searchPointByID(
                    "example_squareactivepoint"
                )!!
            )
            instance!!.activePointsAPI!!.activatePoint(
                instance!!.activePointsAPI!!.searchPointByID(
                    "example_livingentity"
                )!!
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun gameEnded() {
        try {
            instance!!.activePointsAPI!!.deactivatePoint(
                instance!!.activePointsAPI!!.searchPointByID(
                    "example_sphereactivepoint"
                )!!
            )
            instance!!.activePointsAPI!!.deactivatePoint(
                instance!!.activePointsAPI!!.searchPointByID(
                    "example_livingentity"
                )!!
            )
            instance!!.activePointsAPI!!.deactivatePoint(
                instance!!.activePointsAPI!!.searchPointByID(
                    "example_squareactivepoint"
                )!!
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        arena!!.eventAnnouncer.unregister(listener!!)
    }

    override fun beforeRoundStarting() {}
    override fun onRoundEnd() {}
    override fun roundMustBeEnded(): Boolean {
        return false
    }

    override fun gameMustBeEnded(): Boolean {
        return false
    }

    @ArenaEventHandler
    fun onEvent(event: ArenaPlayerJoinLocalEvent) {
    }
}