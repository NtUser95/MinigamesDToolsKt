package com.gmail.borlandlp.minigamesdtools.party.event

import com.gmail.borlandlp.minigamesdtools.party.Party
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class PartyRemoveEvent(val party: Party) : Event() {

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        val handlerList = HandlerList()
    }

}