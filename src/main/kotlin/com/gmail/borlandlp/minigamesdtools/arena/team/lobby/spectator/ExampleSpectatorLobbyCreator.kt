package com.gmail.borlandlp.minigamesdtools.arena.team.lobby.spectator

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools.Companion.instance
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.Creator
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo
import com.gmail.borlandlp.minigamesdtools.util.ArenaUtils.str2Loc

@CreatorInfo(creatorId = "default_spectator_lobby")
class ExampleSpectatorLobbyCreator : Creator() {
    @Throws(Exception::class)
    override fun create(id: String, dataProvider: AbstractDataProvider): ExampleSpectatorLobby {
        return ExampleSpectatorLobby().apply {
            val conf =
                instance!!.configProvider!!.getEntity(ConfigPath.ARENA_LOBBY, id)!!.data
            if (conf.contains("hotbar.enabled") && conf["hotbar.enabled"].toString() == "true") {
                this.hotbarId = conf["hotbar.id"].toString()
                this.isHotbarEnabled = true
            }
            val splittedSpawnPoint = conf["location_XYZWorldYawPitch"].toString().split(":").toTypedArray()
            this.spawnPoint = str2Loc(splittedSpawnPoint)
        }
    }
}