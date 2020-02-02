package com.gmail.borlandlp.minigamesdtools.gui.hotbar

import com.gmail.borlandlp.minigamesdtools.gui.hotbar.api.HotbarAPI
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.type.HeldHotbar
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.type.ItemInterractHotbar
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemHeldEvent

class HotbarListener(private val hotbarAPI: HotbarAPI) : Listener {
    @EventHandler(ignoreCancelled = true)
    fun onItemHeld(event: PlayerItemHeldEvent) {
        if (event.newSlot != 8 && hotbarAPI.isBindedPlayer(event.player)) {
            val hotbar = hotbarAPI.getHotbar(event.player)
            if (hotbar is HeldHotbar) {
                event.isCancelled = true
                hotbarAPI.getHotbar(event.player).performAction(event.newSlot)
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onItemClicked(event: InventoryClickEvent?) {
    }

    @EventHandler(ignoreCancelled = true)
    fun onItemDrop(event: PlayerDropItemEvent) {
        if (hotbarAPI.isBindedPlayer(event.player)) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onItemInterract(event: PlayerInteractEvent) {
        if (event.item != null && hotbarAPI.isBindedPlayer(event.player)) {
            val hotbar = hotbarAPI.getHotbar(event.player)
            if (hotbar is ItemInterractHotbar) {
                event.isCancelled = true
                hotbarAPI.getHotbar(event.player)
                    .performAction(event.player.inventory.heldItemSlot)
            }
        }
    }

}