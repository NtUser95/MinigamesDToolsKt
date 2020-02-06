package com.gmail.borlandlp.minigamesdtools.arena.team.lobby.respawn

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools.Companion.instance
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.ArenaLobby
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.Creator
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo
import com.gmail.borlandlp.minigamesdtools.util.ArenaUtils.str2Loc

@CreatorInfo(creatorId = "default_respawn_lobby")
class ExampleRespawnLobbyCreator : Creator() {
    @Throws(Exception::class)
    override fun create(id: String, dataProvider: AbstractDataProvider): ArenaLobby {
        return ExampleRespawnLobby().apply {
            val conf =
                instance!!.configProvider!!.getEntity(ConfigPath.ARENA_LOBBY, id)!!.data
            if (conf.contains("hotbar.enabled") && conf["hotbar.enabled"].toString() == "true") {
                this.isHotbarEnabled = true
                this.hotbarId = conf["hotbar.id"].toString()
            }
            this.spawnPoint = str2Loc(conf["location_XYZWorld"].toString().split(":").toTypedArray())
            this.setSecondsWaiting(conf["seconds_wait"].toString().toInt())
        }
    }
}