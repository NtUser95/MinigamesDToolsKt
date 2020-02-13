package com.gmail.borlandlp.minigamesdtools.gun

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub
import com.gmail.borlandlp.minigamesdtools.gun.bullet.GhostBullet

class BulletCreatorHub : CreatorHub() {
    @Throws(Exception::class)
    override fun create(itemID: String, dataProvider: AbstractDataProvider): GhostBullet {
        return super.create(itemID, dataProvider) as GhostBullet
    }
}