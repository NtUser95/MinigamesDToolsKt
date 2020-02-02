package com.gmail.borlandlp.minigamesdtools.conditions.examples

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.Creator
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo
import java.util.*

@CreatorInfo(creatorId = "empty_inventory")
class EmptyInventoryConditionCreator : Creator {
    override fun getDataProviderRequiredFields(): List<String> {
        return listOf()
    }

    @Throws(Exception::class)
    override fun create(ID: String, dataProvider: AbstractDataProvider): Any {
        return EmptyInventoryCondition()
    }
}