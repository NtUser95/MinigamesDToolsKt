package com.gmail.borlandlp.minigamesdtools.arena.scenario

import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase
import java.util.*

abstract class ScenarioAbstract : Scenario {
    var arena: ArenaBase? = null
    private val parents: MutableList<Scenario> = ArrayList()

    fun hasActiveParents(): Boolean {
        for (scenario in getParents()) {
            if (!scenario.isDone) return true
        }
        return false
    }

    fun getParents(): List<Scenario> {
        return parents
    }

    fun addParent(scenario: Scenario) {
        parents.add(scenario)
    }

    fun removeParent(scenario: Scenario?) {
        parents.remove(scenario)
    }
}