package com.gmail.borlandlp.minigamesdtools.arena.team.lobby.starter

import com.gmail.borlandlp.minigamesdtools.arena.ArenaEventListener
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaEventHandler
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaEventPriority
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerJoinLocalEvent
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerLeaveLocalEvent
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.ArenaLobby

class StarterLobbyListener(private val lobby: StarterLobby) : ArenaEventListener {
    @ArenaEventHandler(priority = ArenaEventPriority.HIGHEST)
    fun onPJoin(event: ArenaPlayerJoinLocalEvent) {
        if ((lobby as ArenaLobby).isEnabled) {
            lobby.addPlayer(event.player)
        }
    }

    @ArenaEventHandler(priority = ArenaEventPriority.HIGHEST)
    fun onPLeave(event: ArenaPlayerLeaveLocalEvent) {
        if ((lobby as ArenaLobby).isEnabled) {
            lobby.removePlayer(event.player)
        }
    }

}