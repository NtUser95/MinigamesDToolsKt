package com.gmail.borlandlp.minigamesdtools.gun

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub
import com.gmail.borlandlp.minigamesdtools.gun.bullet.GhostBullet

class BulletCreatorHub : CreatorHub() {
    @Throws(Exception::class)
    fun createBullet(ID: String, dataProvider: AbstractDataProvider): GhostBullet {
        return create(ID, dataProvider) as GhostBullet
    }
}