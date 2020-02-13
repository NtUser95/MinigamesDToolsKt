package com.gmail.borlandlp.minigamesdtools.arena.team.lobby

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub

class ArenaLobbyCreatorHub : CreatorHub() {
    @Throws(Exception::class)
    override fun create(itemID: String, dataProvider: AbstractDataProvider): ArenaLobby {
        return super.create(itemID, dataProvider) as ArenaLobby
    }
}