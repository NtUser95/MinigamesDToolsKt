package com.gmail.borlandlp.minigamesdtools.gun

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools
import com.gmail.borlandlp.minigamesdtools.gun.bullet.GhostBullet
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import java.util.*

class BulletHandler : BulletHandlerApi {
    private val bullets: MutableList<GhostBullet> = ArrayList()
    private var task: BukkitTask? = null

    override fun addBullet(bullet: GhostBullet) {
        bullets.add(bullet)
    }

    override fun removeBullet(bullet: GhostBullet) {
        bullets.remove(bullet)
    }

    override fun onLoad() {
        val task = this
        this.task = object : BukkitRunnable() {
            override fun run() {
                task.update()
            }
        }.runTaskTimer(MinigamesDTools.getInstance(), 0, 1)
    }

    override fun onUnload() {
        if (task != null && !task!!.isCancelled) {
            task!!.cancel()
        }
        for (bullet in bullets) {
            bullet.die()
        }
    }

    private fun update() {
        for (bullet in bullets) {
            try {
                bullet.B_() // B_() -> update()
                if (!bullet.isAlive) {
                    removeBullet(bullet)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}