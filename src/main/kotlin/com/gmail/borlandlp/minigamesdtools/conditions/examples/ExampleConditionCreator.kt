package com.gmail.borlandlp.minigamesdtools.conditions.examples

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.Creator
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo

@CreatorInfo(creatorId = "example_condition")
class ExampleConditionCreator : Creator() {
    @Throws(Exception::class)
    override fun create(id: String, dataProvider: AbstractDataProvider): Any {
        return ExampleCondition()
    }
}