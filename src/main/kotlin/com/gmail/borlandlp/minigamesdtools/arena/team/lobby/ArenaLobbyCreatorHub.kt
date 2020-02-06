package com.gmail.borlandlp.minigamesdtools.arena.team.lobby

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub

class ArenaLobbyCreatorHub : CreatorHub() {
    @Throws(Exception::class)
    fun createLobby(id: String, dataProvider: AbstractDataProvider): ArenaLobby {
        return create(id, dataProvider) as ArenaLobby
    }
}