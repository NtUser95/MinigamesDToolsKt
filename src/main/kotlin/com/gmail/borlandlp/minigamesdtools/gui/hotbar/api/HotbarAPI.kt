package com.gmail.borlandlp.minigamesdtools.gui.hotbar.api

import com.gmail.borlandlp.minigamesdtools.gui.hotbar.Hotbar
import org.bukkit.entity.Player

interface HotbarAPI {
    fun bindHotbar(hotbar: Hotbar, player: Player)
    fun unbindHotbar(player: Player)
    fun isBindedPlayer(player: Player): Boolean
    fun getHotbar(player: Player): Hotbar
    val all: Map<Player, Hotbar>
}