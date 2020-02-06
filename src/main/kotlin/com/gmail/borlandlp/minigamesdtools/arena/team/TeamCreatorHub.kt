package com.gmail.borlandlp.minigamesdtools.arena.team

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub

class TeamCreatorHub : CreatorHub() {
    @Throws(Exception::class)
    fun createTeam(ID: String, dataProvider: AbstractDataProvider): TeamProvider {
        return create(ID, dataProvider) as TeamProvider
    }
}