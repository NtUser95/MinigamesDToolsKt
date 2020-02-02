package com.gmail.borlandlp.minigamesdtools.conditions

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub

class ConditionsCreatorHub : CreatorHub() {
    @Throws(Exception::class)
    fun createCondition(ID: String, dataProvider: AbstractDataProvider): AbstractCondition {
        return create(ID, dataProvider) as AbstractCondition
    }
}