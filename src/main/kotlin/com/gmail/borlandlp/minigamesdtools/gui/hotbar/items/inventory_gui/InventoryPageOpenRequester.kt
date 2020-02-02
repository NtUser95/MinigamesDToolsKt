package com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.inventory_gui

import com.gmail.borlandlp.minigamesdtools.events.PlayerRequestOpenInvGUIEvent
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.SlotItem
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class InventoryPageOpenRequester : SlotItem() {
    var pageId: String? = null

    override fun use(player: Player): Boolean {
        val event =
            PlayerRequestOpenInvGUIEvent(player, pageId!!)
        Bukkit.getPluginManager().callEvent(event)
        return true
    }
}