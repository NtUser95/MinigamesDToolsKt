package com.gmail.borlandlp.minigamesdtools.activepoints

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub

class ActivePointsCreatorHub : CreatorHub() {
    @Throws(Exception::class)
    fun createActivePoint(ID: String, dataProvider: AbstractDataProvider): ActivePoint {
        return create(ID, dataProvider) as ActivePoint
    }
}