package com.gmail.borlandlp.minigamesdtools.arena

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub

class ArenaCreatorHub : CreatorHub() {
    @Throws(Exception::class)
    override fun create(itemID: String, dataProvider: AbstractDataProvider): ArenaBase {
        return super.create(itemID, dataProvider) as ArenaBase
    }
}