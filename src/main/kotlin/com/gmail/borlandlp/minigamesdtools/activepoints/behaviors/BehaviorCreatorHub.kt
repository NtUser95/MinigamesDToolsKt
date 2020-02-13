package com.gmail.borlandlp.minigamesdtools.activepoints.behaviors

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub

class BehaviorCreatorHub : CreatorHub() {
    @Throws(Exception::class)
    override fun create(itemID: String, dataProvider: AbstractDataProvider): Behavior {
        return super.create(itemID, dataProvider) as Behavior
    }
}