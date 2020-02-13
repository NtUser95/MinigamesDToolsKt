package com.gmail.borlandlp.minigamesdtools.arena.commands

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub

class CommandWatcherCreatorHub : CreatorHub() {
    @Throws(Exception::class)
    override fun create(itemID: String, dataProvider: AbstractDataProvider): ArenaCommandWatcher {
        return super.create(itemID, dataProvider) as ArenaCommandWatcher
    }
}