package com.gmail.borlandlp.minigamesdtools.activepoints

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools.Companion.instance
import com.gmail.borlandlp.minigamesdtools.activepoints.behaviors.Behavior
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

// unused & deprecated class ?
class ScheduleTaskWrapper(private val behavior: Behavior, val delay: Int, val period: Int) {
    private var task: BukkitTask? = null

    fun start() {
        val behavior: Behavior = behavior
        task = object : BukkitRunnable() {
            override fun run() {
                behavior.tick()
            }
        }.runTaskTimer(instance, delay.toLong(), period.toLong())
    }

    fun stop() {//if(this.getTask() != null && !this.getTask().isCancelled()) this.getTask().cancel();
        if (task?.isCancelled == false) task?.cancel()
    }

}