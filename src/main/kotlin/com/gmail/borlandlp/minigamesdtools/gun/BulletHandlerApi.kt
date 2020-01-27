package com.gmail.borlandlp.minigamesdtools.gun

import com.gmail.borlandlp.minigamesdtools.APIComponent
import com.gmail.borlandlp.minigamesdtools.gun.bullet.GhostBullet

interface BulletHandlerApi : APIComponent {
    fun addBullet(bullet: GhostBullet)
    fun removeBullet(bullet: GhostBullet)
}