package com.gmail.borlandlp.minigamesdtools.arena

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub

class ArenaCreatorHub : CreatorHub() {
    @Throws(Exception::class)
    fun createArena(ID: String?, dataProvider: AbstractDataProvider?): ArenaBase {
        return create(ID!!, dataProvider!!) as ArenaBase
    }
}