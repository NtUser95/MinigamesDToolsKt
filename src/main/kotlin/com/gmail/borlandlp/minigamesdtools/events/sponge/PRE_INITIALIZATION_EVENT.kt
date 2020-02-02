package com.gmail.borlandlp.minigamesdtools.events.sponge

import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/*
* Загрузка всех конфигураций в пул
* */
class PRE_INITIALIZATION_EVENT : Event() {
    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}