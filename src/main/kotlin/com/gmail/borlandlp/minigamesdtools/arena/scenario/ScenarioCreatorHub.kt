package com.gmail.borlandlp.minigamesdtools.arena.scenario

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub

class ScenarioCreatorHub : CreatorHub() {
    @Throws(Exception::class)
    fun createScenario(scenarioID: String, dataProvider: AbstractDataProvider): Scenario {
        return create(scenarioID, dataProvider) as Scenario
    }
}