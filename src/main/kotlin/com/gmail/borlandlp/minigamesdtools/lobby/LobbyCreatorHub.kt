package com.gmail.borlandlp.minigamesdtools.lobby

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub

class LobbyCreatorHub : CreatorHub() {
    @Throws(Exception::class)
    fun createLobby(ID: String, dataProvider: AbstractDataProvider): ServerLobby {
        return create(ID, dataProvider) as ServerLobby
    }
}