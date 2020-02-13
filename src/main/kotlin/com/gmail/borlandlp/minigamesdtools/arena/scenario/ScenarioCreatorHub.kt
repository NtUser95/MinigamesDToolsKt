package com.gmail.borlandlp.minigamesdtools.arena.scenario

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub

class ScenarioCreatorHub : CreatorHub() {
    @Throws(Exception::class)
    override fun create(itemID: String, dataProvider: AbstractDataProvider): Scenario {
        return super.create(itemID, dataProvider) as Scenario
    }
}