package com.gmail.borlandlp.minigamesdtools.party

import org.bukkit.entity.Player

interface PartyAPI {
    fun addParty(party: Party)
    fun removeParty(party: Party)
    fun getPartyOf(player: Player): Party?
    fun getPartyOf(player: String): Party?
    fun createParty(leader: Player): Party
}