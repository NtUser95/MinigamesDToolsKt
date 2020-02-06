package com.gmail.borlandlp.minigamesdtools.arena.team.lobby.spectator

import com.gmail.borlandlp.minigamesdtools.arena.ArenaEventListener
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaEventHandler
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerDamagedLocalEvent
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerDeathLocalEvent
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerKilledLocalEvent
import com.gmail.borlandlp.minigamesdtools.events.ArenaRequestSpectatePlayer2Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class ExampleSpectatorLobbyListener(private val lobby: SpectatorLobby) : ArenaEventListener, Listener {
    @EventHandler
    fun onSpectateReq(event: ArenaRequestSpectatePlayer2Player) {
        event.playerInvoker.spectatorTarget = event.playerTarget
    }

    @ArenaEventHandler
    fun onPlayerDamage(event: ArenaPlayerDamagedLocalEvent) {
        if (lobby.players.contains(event.player)) {
            event.isCancelled = true
        }
    }

    @ArenaEventHandler
    fun onPlayerKilled(event: ArenaPlayerDeathLocalEvent) {
        if (lobby.players.contains(event.player)) {
            event.isCancelled = true
        }
    }

    @ArenaEventHandler
    fun onPlayerKilled(event: ArenaPlayerKilledLocalEvent) {
        if (lobby.players.contains(event.player)) {
            event.isCancelled = true
        }
    }

}