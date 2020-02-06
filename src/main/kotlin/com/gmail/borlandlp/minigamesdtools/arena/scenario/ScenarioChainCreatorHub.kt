package com.gmail.borlandlp.minigamesdtools.arena.scenario

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub

class ScenarioChainCreatorHub : CreatorHub() {
    @Throws(Exception::class)
    fun createChain(ID: String, dataProvider: AbstractDataProvider): ScenarioChainController {
        return create(ID, dataProvider) as ScenarioChainController
    }
}