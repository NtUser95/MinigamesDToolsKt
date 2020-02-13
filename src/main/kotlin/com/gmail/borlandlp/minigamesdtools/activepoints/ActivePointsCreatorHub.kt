package com.gmail.borlandlp.minigamesdtools.activepoints

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub

class ActivePointsCreatorHub : CreatorHub() {
    @Throws(Exception::class)
    override fun create(itemID: String, dataProvider: AbstractDataProvider): ActivePoint {
        return super.create(itemID, dataProvider) as ActivePoint
    }
}