package com.gmail.borlandlp.minigamesdtools.arena.gui.hotbar

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools.Companion.instance
import com.gmail.borlandlp.minigamesdtools.arena.ArenaComponent
import com.gmail.borlandlp.minigamesdtools.arena.ArenaEventListener
import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent
import com.gmail.borlandlp.minigamesdtools.creator.DataProvider
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.Hotbar
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener

class HotbarController : ArenaComponent(), ArenaPhaseComponent {
    var defaultHotbarId: String? = null
    private var listener: Listener? = null
    var isEnabled = false

    @Throws(Exception::class)
    fun buildDefaultHotbarFor(player: Player): Hotbar {
        val hotbarID = defaultHotbarId
        return instance!!.hotbarCreatorHub!!.createHotbar(hotbarID!!, DataProvider().apply {
            this["player"] = player
        })
    }

    override fun onInit() {
        if (!isEnabled) {
            return
        }
        listener = HotbarListener(this)
        instance!!.server.pluginManager.registerEvents(listener, instance)
        try {
            arena!!.eventAnnouncer.register(listener as ArenaEventListener)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun beforeGameStarting() {
        if (!isEnabled) {
            return
        }
        for (team in arena!!.teamController!!.teams) {
            for (player in team.players) {
                try {
                    instance!!.hotbarAPI!!.bindHotbar(buildDefaultHotbarFor(player), player)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun gameEnded() {
        if (!isEnabled) {
            return
        }
        for (team in arena!!.teamController!!.teams) {
            for (player in team.players) {
                instance!!.hotbarAPI!!.unbindHotbar(player)
            }
        }
        HandlerList.unregisterAll(listener)
        arena!!.eventAnnouncer.unregister(listener as ArenaEventListener)
    }

    override fun update() {}
    override fun beforeRoundStarting() {}
    override fun onRoundEnd() {}
}