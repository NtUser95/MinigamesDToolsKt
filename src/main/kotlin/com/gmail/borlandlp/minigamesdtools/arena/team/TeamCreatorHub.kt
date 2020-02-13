package com.gmail.borlandlp.minigamesdtools.arena.team

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub

class TeamCreatorHub : CreatorHub() {
    @Throws(Exception::class)
    override fun create(itemID: String, dataProvider: AbstractDataProvider): TeamProvider {
        return super.create(itemID, dataProvider) as TeamProvider
    }
}