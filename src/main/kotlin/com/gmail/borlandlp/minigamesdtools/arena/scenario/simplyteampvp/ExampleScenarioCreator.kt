package com.gmail.borlandlp.minigamesdtools.arena.scenario.simplyteampvp

import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.Creator
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo

@CreatorInfo(creatorId = "example_scenario")
class ExampleScenarioCreator : Creator() {
    @Throws(Exception::class)
    override fun create(id: String, dataProvider: AbstractDataProvider): Any {
        return ExampleScenario().apply {
            this.arena = dataProvider["arena_instance"] as ArenaBase
        }
    }
}