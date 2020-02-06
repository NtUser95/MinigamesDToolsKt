package com.gmail.borlandlp.minigamesdtools.arena.gui.providers

import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.Debug.print
import com.gmail.borlandlp.minigamesdtools.arena.ArenaEventListener
import com.gmail.borlandlp.minigamesdtools.arena.localevent.*

class GUIListener(private val guiController: GUIController) : ArenaEventListener {
    @ArenaEventHandler
    fun onPlayerDeath(event: ArenaPlayerDeathLocalEvent) {
        print(
            Debug.LEVEL.NOTICE,
            this.javaClass.simpleName + "#" + event
        )
    }

    @ArenaEventHandler
    fun onPlayerKilled(event: ArenaPlayerKilledLocalEvent) {
        print(
            Debug.LEVEL.NOTICE,
            this.javaClass.simpleName + "#" + event
        )
    }

    @ArenaEventHandler
    fun onPlayerLeave(event: ArenaPlayerLeaveLocalEvent) {
        print(
            Debug.LEVEL.NOTICE,
            this.javaClass.simpleName + "#" + event
        )
    }

    @ArenaEventHandler
    fun onPlayerJoin(event: ArenaPlayerJoinLocalEvent) {
        print(
            Debug.LEVEL.NOTICE,
            this.javaClass.simpleName + "#" + event
        )
    }

}