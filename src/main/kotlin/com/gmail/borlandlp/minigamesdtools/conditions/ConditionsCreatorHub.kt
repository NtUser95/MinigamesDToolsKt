package com.gmail.borlandlp.minigamesdtools.conditions

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub

class ConditionsCreatorHub : CreatorHub() {
    @Throws(Exception::class)
    override fun create(itemID: String, dataProvider: AbstractDataProvider): AbstractCondition {
        return super.create(itemID, dataProvider) as AbstractCondition
    }
}