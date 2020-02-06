package com.gmail.borlandlp.minigamesdtools.arena.commands

import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.Creator
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo
import java.util.*

@CreatorInfo(creatorId = "default_command_watcher")
class DefaultWatcherCreator : Creator() {
    override val dataProviderRequiredFields: List<String>
        get() = listOf("blacklist_rules", "whitelist_rules", "arena_instance")

    @Throws(Exception::class)
    override fun create(id: String, dataProvider: AbstractDataProvider): DefaultWatcher {
        return DefaultWatcher().apply {
            this.blacklisted = dataProvider["blacklist_rules"] as MutableList<Array<String>>
            this.whitelisted = dataProvider["whitelist_rules"] as MutableList<Array<String>>
            this.setArena(dataProvider["arena_instance"] as ArenaBase)
        }
    }
}