package com.gmail.borlandlp.minigamesdtools.arena.team

import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.Debug.print
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools.Companion.instance
import com.gmail.borlandlp.minigamesdtools.arena.ArenaEventListener
import com.gmail.borlandlp.minigamesdtools.arena.localevent.*
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.ArenaLobby
import org.bukkit.entity.Player

class ExampleTeamListener(private val teamController: TeamController) : ArenaEventListener {
    @ArenaEventHandler(ignoreCancelled = true, priority = ArenaEventPriority.LOWEST)
    fun onPlayerDeath(event: ArenaPlayerDeathLocalEvent) {
        handlePlayerDeath(event.player)
        print(
            Debug.LEVEL.NOTICE,
            this.javaClass.simpleName + "#" + event
        )
    }

    @ArenaEventHandler(ignoreCancelled = true, priority = ArenaEventPriority.LOWEST)
    fun onPlayerKilled(event: ArenaPlayerKilledLocalEvent) {
        handlePlayerDeath(event.player)
        print(
            Debug.LEVEL.NOTICE,
            this.javaClass.simpleName + "#" + event
        )
    }

    private fun handlePlayerDeath(p: Player) {
        val teamProvider = teamController.getTeamOf(p)
        val arenaPlayerRespawnRequestLocalEvent =
            ArenaPlayerRespawnRequestLocalEvent(teamProvider!!, p)
        teamProvider.arena.eventAnnouncer.announce(arenaPlayerRespawnRequestLocalEvent)
        if (!arenaPlayerRespawnRequestLocalEvent.isCancelled) {
            if ((teamProvider.respawnLobby as ArenaLobby).isEnabled) {
                teamProvider.movePlayerTo((teamProvider.respawnLobby as ArenaLobby), p)
            } else {
                teamProvider.spawn(p)
            }
        } else {
            teamProvider.setSpectate(p, true)
        }
    }

    @ArenaEventHandler(ignoreCancelled = true, priority = ArenaEventPriority.LOWEST)
    fun onPlayerLeave(event: ArenaPlayerLeaveLocalEvent) {
        val player = event.player
        val team = teamController.getTeamOf(player)
        if (team != null) {
            player.inventory.armorContents = null
            player.inventory.clear()
            team.removePlayer(player)
        }
        instance!!.hotbarAPI!!.unbindHotbar(event.player)
    }

    @ArenaEventHandler(ignoreCancelled = true, priority = ArenaEventPriority.LOWEST)
    fun onPlayerJoin(event: ArenaPlayerJoinLocalEvent) {
        event.team.addPlayer(event.player)
    }

}