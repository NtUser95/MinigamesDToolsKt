package com.gmail.borlandlp.minigamesdtools.arena.gui.hotbar

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools.Companion.instance
import com.gmail.borlandlp.minigamesdtools.arena.ArenaEventListener
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaEventHandler
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerJoinLocalEvent
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerLeaveLocalEvent
import org.bukkit.event.Listener

class HotbarListener(private val hotbarController: HotbarController) : Listener, ArenaEventListener {
    @ArenaEventHandler
    fun onPlayerJoin(event: ArenaPlayerJoinLocalEvent) {
        if (hotbarController.isEnabled) {
            try {
                instance!!.hotbarAPI!!.bindHotbar(
                    hotbarController.buildDefaultHotbarFor(
                        event.player
                    ), event.player
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @ArenaEventHandler
    fun onPlayerLeave(event: ArenaPlayerLeaveLocalEvent) {
        if (hotbarController.isEnabled) {
            instance!!.hotbarAPI!!.unbindHotbar(event.player)
        }
    }
}