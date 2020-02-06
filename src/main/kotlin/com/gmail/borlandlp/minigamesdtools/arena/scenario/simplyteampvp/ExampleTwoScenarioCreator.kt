package com.gmail.borlandlp.minigamesdtools.arena.scenario.simplyteampvp

import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.Creator
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo

@CreatorInfo(creatorId = "example_scenario_two")
class ExampleTwoScenarioCreator : Creator() {
    @Throws(Exception::class)
    override fun create(id: String, dataProvider: AbstractDataProvider): Any {
        return ExampleTwoScenario().apply {
            this.arena = dataProvider["arena_instance"] as ArenaBase
        }
    }
}