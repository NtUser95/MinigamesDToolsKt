package com.gmail.borlandlp.minigamesdtools.arena.scenario

import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase
import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent
import java.util.*

class ScenarioChainController(val arena: ArenaBase) : ArenaPhaseComponent {
    var scenarios: List<Scenario> = ArrayList()
    private val activeScenarios: MutableList<Scenario> = ArrayList()
    private var scenarioListener: ScenarioListener? = null
    var endGameInitiator: Scenario? = null
        private set
    private var signalRoundEnding = false
    var endRoundInitiator: Scenario? = null
        private set
    private var signalGameEnding = false

    fun hasSignalGameEnding(): Boolean {
        return signalGameEnding
    }

    fun hasSignalRoundEnding(): Boolean {
        return signalRoundEnding
    }

    fun getActiveScenarios(): List<Scenario> {
        return activeScenarios
    }

    private fun updateScenariosQueue() {
        for (scenario in scenarios) {
            if (!(scenario as ScenarioAbstract).hasActiveParents() && !scenario.isDone && !getActiveScenarios().contains(
                    scenario
                )
            ) {
                enableScenario(scenario)
            }
        }
    }

    override fun onInit() {
        scenarioListener = ScenarioListener(this)
        try {
            arena.eventAnnouncer.register(scenarioListener!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        updateScenariosQueue()
    }

    override fun beforeGameStarting() {
        updateScenariosQueue()
    }

    override fun gameEnded() {}

    fun resetSignalsData() {
        signalGameEnding = false
        endGameInitiator = null
        signalRoundEnding = false
        endRoundInitiator = null
    }

    private fun enableScenario(scenario: Scenario) {
        activeScenarios.add(scenario)
        arena.phaseComponentController.register(scenario)
    }

    private fun disableScenario(scenario: Scenario) {
        activeScenarios.remove(scenario)
        arena.phaseComponentController.unregister(scenario)
    }

    override fun update() {
        val scenarioIterator: List<Scenario> = ArrayList(activeScenarios)
        for (scenario in scenarioIterator) {
            if (scenario.isDone) {
                disableScenario(scenario)
                updateScenariosQueue()
            } else { // scenario.updateScenario();
                if (scenario.gameMustBeEnded()) {
                    signalGameEnding = true
                    endGameInitiator = scenario
                    return
                } else if (scenario.roundMustBeEnded()) {
                    signalRoundEnding = true
                    endRoundInitiator = scenario
                    return
                }
            }
        }
    }

    override fun beforeRoundStarting() {}
    override fun onRoundEnd() {
        resetSignalsData()
    }

}