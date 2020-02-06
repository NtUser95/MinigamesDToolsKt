package com.gmail.borlandlp.minigamesdtools.arena.team.lobby.respawn

import com.gmail.borlandlp.minigamesdtools.arena.ArenaEventListener
import com.gmail.borlandlp.minigamesdtools.arena.localevent.*

class ExampleRespawnLobbyListener(private val lobby: RespawnLobby) : ArenaEventListener {
    @ArenaEventHandler(ignoreCancelled = true, priority = ArenaEventPriority.HIGH)
    fun onPlayerDamage(event: ArenaPlayerDamagedLocalEvent) {
        if (lobby.waitingPlayers.containsKey(event.player)) {
            event.isCancelled = true
        }
    }

    @ArenaEventHandler(ignoreCancelled = true, priority = ArenaEventPriority.HIGH)
    fun onPlayerKilled(event: ArenaPlayerDeathLocalEvent) {
        if (lobby.waitingPlayers.containsKey(event.player)) {
            event.isCancelled = true
        }
    }

    @ArenaEventHandler(ignoreCancelled = true, priority = ArenaEventPriority.HIGH)
    fun onPlayerKilled(event: ArenaPlayerKilledLocalEvent) {
        if (lobby.waitingPlayers.containsKey(event.player)) {
            event.isCancelled = true
        }
    }

}