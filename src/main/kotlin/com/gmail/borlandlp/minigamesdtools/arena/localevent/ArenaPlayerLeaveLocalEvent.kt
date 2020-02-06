package com.gmail.borlandlp.minigamesdtools.arena.localevent

import org.bukkit.entity.Player

@ArenaEventHandler
class ArenaPlayerLeaveLocalEvent(val player: Player) : ArenaEvent() {
    override fun toString(): String {
        return "{Event:" + this.javaClass.simpleName + " Player" + player + "}"
    }
}