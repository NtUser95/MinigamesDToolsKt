package com.gmail.borlandlp.minigamesdtools.lobby

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.Creator
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo
import com.gmail.borlandlp.minigamesdtools.util.ArenaUtils
import java.util.*

@CreatorInfo(creatorId = "default_server_lobby")
class ExampleLobbyCreator : Creator() {
    @Throws(Exception::class)
    override fun create(id: String, dataProvider: AbstractDataProvider): Any {
        val conf =
            MinigamesDTools.instance!!.configProvider!!.getEntity(ConfigPath.SERVER_LOBBY, id)!!.data
                ?: throw Exception("Cant find config for $id")
        val serverLobby: ServerLobby = ExampleServerLobby()
        serverLobby.hotbarID = if (conf.contains("hotbar_id")) conf["hotbar_id"].toString() else null
        serverLobby.id = id
        serverLobby.spawnPoint =
            ArenaUtils.str2Loc(conf["spawn_point_XYZWorldYawPitch"].toString().split(":").toTypedArray())
        return serverLobby
    }
}