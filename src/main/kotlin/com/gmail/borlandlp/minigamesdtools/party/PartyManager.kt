package com.gmail.borlandlp.minigamesdtools.party

import com.gmail.borlandlp.minigamesdtools.APIComponent
import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools
import com.gmail.borlandlp.minigamesdtools.party.event.PartyCreateEvent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import java.util.*

class PartyManager : PartyAPI, APIComponent {
    private var pListener: Listener? = null
    private val parties: MutableSet<Party> = HashSet()
    override fun addParty(party: Party) {
        Debug.print(Debug.LEVEL.NOTICE, "Add party, leader:" + party.leader.name)
        parties.add(party)
    }

    override fun removeParty(party: Party) {
        parties.remove(party)
    }

    override fun getPartyOf(player: Player): Party? {
        return this.getPartyOf(player.name)
    }

    override fun getPartyOf(player: String): Party? {
        for (party in parties) {
            if (party.players.stream().anyMatch { p: Player -> p.name == player }) {
                return party
            }
        }
        return null
    }

    override fun createParty(leader: Player): Party {
        val party: Party = MgParty(leader)
        Bukkit.getPluginManager().callEvent(PartyCreateEvent(party))
        return party
    }

    override fun onLoad() {
        pListener = PartyListener(this)
        Bukkit.getPluginManager().registerEvents(pListener as PartyListener, MinigamesDTools.getInstance())
    }

    override fun onUnload() {
        HandlerList.unregisterAll(pListener as PartyListener)
        pListener = null
    }
}