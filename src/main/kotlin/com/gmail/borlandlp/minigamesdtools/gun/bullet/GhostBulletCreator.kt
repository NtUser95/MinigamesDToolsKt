package com.gmail.borlandlp.minigamesdtools.gun.bullet

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.Creator
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo
import org.bukkit.World

@CreatorInfo(creatorId = "ghost_bullet")
class GhostBulletCreator : Creator() {
    override val dataProviderRequiredFields: List<String>
        get() = listOf("world")

    @Throws(Exception::class)
    override fun create(id: String, dataProvider: AbstractDataProvider): Any {
        return GhostBullet(dataProvider["world"] as World)
    }
}