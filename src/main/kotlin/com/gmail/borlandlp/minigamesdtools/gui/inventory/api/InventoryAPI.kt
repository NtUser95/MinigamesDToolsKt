package com.gmail.borlandlp.minigamesdtools.gui.inventory.api

import com.gmail.borlandlp.minigamesdtools.gui.inventory.DrawableInventory
import org.bukkit.entity.Player

interface InventoryAPI {
    fun show(drawableInventory: DrawableInventory, player: Player)
    fun getInventory(player: Player): DrawableInventory
    fun isView(player: Player): Boolean
    fun close(player: Player)
    fun unregister(player: Player)
}