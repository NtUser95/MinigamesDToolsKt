package com.gmail.borlandlp.minigamesdtools.gui.other.radar2d

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools
import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import java.util.*

class Radar2dHub : ArenaPhaseComponent {
    private val radar2dMap: MutableMap<Player, Radar> = HashMap()
    private var task: BukkitTask? = null
    val all: Map<Player, Radar>
        get() = HashMap(radar2dMap)

    operator fun get(p: Player?): Radar? {
        return radar2dMap[p]
    }

    fun add(p: Player, r: Radar) {
        try {
            r.onLoad()
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }
        radar2dMap[p] = r
    }

    fun remove(p: Player) {
        if (radar2dMap.containsKey(p)) {
            try {
                radar2dMap[p]!!.onUnload()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            radar2dMap.remove(p)
        }
    }

    override fun onInit() {}

    override fun beforeGameStarting() {
        val obj = this
        task = object : BukkitRunnable() {
            override fun run() {
                obj.drawRadars()
            }
        }.runTaskTimer(MinigamesDTools.instance, 0, 5)
    }

    override fun gameEnded() {
        if (task != null) {
            try {
                task!!.cancel()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        for (player in ArrayList(radar2dMap.keys)) {
            try {
                this.remove(player)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun drawRadars() {
        for (radar in radar2dMap.values) {
            try {
                radar.draw()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun update() {}
    override fun beforeRoundStarting() {}
    override fun onRoundEnd() {}
}