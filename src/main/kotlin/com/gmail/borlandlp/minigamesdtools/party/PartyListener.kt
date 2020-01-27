package com.gmail.borlandlp.minigamesdtools.party

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent

class PartyListener(private val manager: PartyManager) : Listener {
    @EventHandler
    fun pLeave(event: PlayerQuitEvent) {
        handleDisconnect(event.player)
    }

    @EventHandler
    fun pLeave(event: PlayerKickEvent) {
        handleDisconnect(event.player)
    }

    private fun handleDisconnect(player: Player) {
        val party = manager.getPartyOf(player)
        if (party == null) {
            return
        } else if (party.players.size <= 1) {
            manager.removeParty(party)
            return
        }
        if (party.leader === player) {
            for (player_party in party.players) {
                if (party.leader !== player_party) {
                    party.leader = player_party
                    party.broadcast("[Party] new leader:" + player_party.name)
                    break
                }
            }
        }
        party.removePlayer(player)
    }

}