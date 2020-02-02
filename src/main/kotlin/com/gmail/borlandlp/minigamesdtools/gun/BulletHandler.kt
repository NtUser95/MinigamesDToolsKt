package com.gmail.borlandlp.minigamesdtools.gun

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools
import com.gmail.borlandlp.minigamesdtools.gun.bullet.GhostBullet
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

class BulletHandler : BulletHandlerApi {
    private val bullets: Queue<GhostBullet> = ConcurrentLinkedQueue()
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
        }.runTaskTimer(MinigamesDTools.instance, 0, 1)
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
        bullets.forEach { bullet ->
            try {
                bullet.B_() // B_() -> update()
            } catch (e: Exception) {
                e.printStackTrace()
                bullet.dead = true
            } finally {
                if (bullet.dead) {
                    bullets.remove(bullet)
                }
            }
        }
    }
}