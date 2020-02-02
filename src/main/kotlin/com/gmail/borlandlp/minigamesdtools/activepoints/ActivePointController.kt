package com.gmail.borlandlp.minigamesdtools.activepoints

import com.gmail.borlandlp.minigamesdtools.APIComponent
import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.Debug.print
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools.Companion.instance
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath
import com.gmail.borlandlp.minigamesdtools.creator.DataProvider
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

class ActivePointController : APIComponent, ActivePointsAPI {
    override val activatedPoints: MutableList<ActivePoint> = mutableListOf()
    override val allPoints: Queue<ActivePoint> = ConcurrentLinkedQueue()
    override val staticPointsCache = StaticPointsCache()
    private var listener: Listener? = null
    private var task: BukkitTask? = null

    override fun onLoad() {
        listener = ActivePointsListener(this).apply {
            instance!!.server.pluginManager.registerEvents(listener, instance)
        }
        val task = this
        this.task = object : BukkitRunnable() {
            override fun run() {
                task.update()
            }
        }.runTaskTimer(instance, 0, 20)
        // load default activepoints
        val poolContents =
            instance!!.configProvider!!.getPoolContents(ConfigPath.ACTIVE_POINT)
        for (configEntity in poolContents) {
            print(
                Debug.LEVEL.NOTICE,
                "[ActivePointController] load activePoint " + configEntity.id
            )
            var activePoint: ActivePoint? = null
            try {
                activePoint = instance!!.activePointsCreatorHub!!.createActivePoint(configEntity.id, DataProvider())
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if (activePoint != null) {
                registerPoint(activePoint)
            } else {
                print(
                    Debug.LEVEL.WARNING,
                    "[ActivePointController] fail on load activePoint " + configEntity.id
                )
            }
        }
    }

    override fun onUnload() {
        task!!.cancel()
        HandlerList.unregisterAll(listener)
    }

    override fun searchPointByID(ID: String): ActivePoint? {
        for (activePoint in allPoints) {
            if (activePoint.name == ID) return activePoint
        }
        return null
    }

    override fun registerPoint(point: ActivePoint) {
        point.activePointController = this
        allPoints.add(point)
        print(
            Debug.LEVEL.NOTICE,
            "[ActivePointController] register activePoint: " + point.name + " for controller."
        )
    }

    @Throws(Exception::class)
    override fun unregisterPoint(point: ActivePoint) {
        if (activatedPoints.contains(point)) {
            throw Exception("The active point must be deactivated before calling this function.")
        }
        allPoints.remove(point)
        point.activePointController = null
        print(
            Debug.LEVEL.NOTICE,
            "[ActivePointController] unregister activePoint: " + point.name + " for controller."
        )
    }

    override fun activatePoint(point: ActivePoint) {
        point.spawn()
        activatedPoints.add(point)
    }

    override fun deactivatePoint(point: ActivePoint) {
        if (activatedPoints.contains(point)) {
            point.despawn()
            activatedPoints.remove(point)
        }
    }

    private fun update() {
        for (activePoint in activatedPoints) {
            for (behavior in activePoint.behaviors) {
                behavior.tick()
            }
        }
    }
}