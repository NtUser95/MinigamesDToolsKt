package com.gmail.borlandlp.minigamesdtools.arena.customhandlers

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools.Companion.instance
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

abstract class Handler {
    private var task: BukkitTask? = null
    private val delay = 0
    private val period = 20

    fun start() {
        val task = this
        this.task = object : BukkitRunnable() {
            override fun run() {
                task.doWork()
            }
        }.runTaskTimer(instance, delay.toLong(), period.toLong())
    }

    fun stop() {
        task?.cancel()
    }

    abstract fun doWork()
}