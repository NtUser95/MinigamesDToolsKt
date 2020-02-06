package com.gmail.borlandlp.minigamesdtools.arena.commands

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub

class CommandWatcherCreatorHub : CreatorHub() {
    @Throws(Exception::class)
    fun createCommandWatcher(ID: String, dataProvider: AbstractDataProvider): ArenaCommandWatcher {
        return create(ID, dataProvider) as ArenaCommandWatcher
    }
}