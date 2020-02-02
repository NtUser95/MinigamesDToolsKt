package com.gmail.borlandlp.minigamesdtools.conditions.examples

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.Creator
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo

@CreatorInfo(creatorId = "empty_inventory")
class EmptyInventoryConditionCreator : Creator() {
    @Throws(Exception::class)
    override fun create(id: String, dataProvider: AbstractDataProvider): Any {
        return EmptyInventoryCondition()
    }
}