package com.gmail.borlandlp.minigamesdtools.party.event

import com.gmail.borlandlp.minigamesdtools.party.Party
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class PartyPlayerKickedEvent(val party: Party, val player: Player) : Event() {

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        val handlerList = HandlerList()
    }

}