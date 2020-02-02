package com.gmail.borlandlp.minigamesdtools.gui.inventory

import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.DataProvider
import com.gmail.borlandlp.minigamesdtools.events.PlayerRequestOpenInvGUIEvent
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.time.Instant
import java.util.*

class InventoryListener : Listener {
    private val playersClickLog = HashMap<String, Long?>()
    @EventHandler
    fun onInvRequest(event: PlayerRequestOpenInvGUIEvent) {
        if (!MinigamesDTools.instance!!.inventoryGUICreatorHub!!.containsRouteId2Creator(event.pageID)) {
            Debug.print(
                Debug.LEVEL.WARNING,
                "Player[name:" + event.player.name + "] requested unknown Page[ID:" + event.pageID + "]"
            )
            return
        }
        var drawableInventory: DrawableInventory? = null
        try {
            val dataProvider: AbstractDataProvider = DataProvider()
            dataProvider["player"] = event.player
            drawableInventory = MinigamesDTools.instance!!.inventoryGUICreatorHub!!
                .createInventory(event.pageID, dataProvider)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (drawableInventory != null) {
            MinigamesDTools.instance!!.inventoryGUI_API!!.show(drawableInventory, event.player)
        } else {
            Debug.print(
                Debug.LEVEL.NOTICE,
                "Internal error. DrawableInventory cant be null."
            )
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onCloseInv(event: InventoryCloseEvent) {
        if (MinigamesDTools.instance!!.inventoryGUI_API!!.isView((event.player as Player))) {
            MinigamesDTools.instance!!.inventoryGUI_API!!.unregister((event.player as Player))
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPlayerQuitEvent(event: PlayerQuitEvent) {
        if (MinigamesDTools.instance!!.inventoryGUI_API!!.isView(event.player)) {
            MinigamesDTools.instance!!.inventoryGUI_API!!.close(event.player)
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPlayerKickEvent(event: PlayerQuitEvent) {
        if (MinigamesDTools.instance!!.inventoryGUI_API!!.isView(event.player)) {
            MinigamesDTools.instance!!.inventoryGUI_API!!.close(event.player)
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onInvClickItem(event: InventoryClickEvent) {
        if (event.clickedInventory == null) {
            return
        } else if (MinigamesDTools.instance!!.inventoryGUI_API!!.isView((event.whoClicked as Player))) {
            if (event.whoClicked.openInventory.topInventory !== event.clickedInventory) {
                return
            }
            event.isCancelled = true
            if (event.currentItem.type.name.equals("air", ignoreCase = true)) {
                return
            }
            val drawableInventory =
                MinigamesDTools.instance!!.inventoryGUI_API!!.getInventory((event.whoClicked as Player))
            val playerName = event.whoClicked.name
            if (!playersClickLog.containsKey(playerName) || playersClickLog[playerName]!! + 1 <= Instant.now().epochSecond) {
                playersClickLog[playerName] = Instant.now().epochSecond
            } else {
                event.whoClicked
                    .sendMessage("Пожалуйста, не кликайте так часто! :( Минимальное время между кликами - 1 секунда.")
                playersClickLog[playerName] = Instant.now().epochSecond
                return
            }
            val slot = drawableInventory.getSlot(event.slot)
            slot.performClick(event.whoClicked as Player)
        } else if (event.whoClicked.openInventory != null && MinigamesDTools.instance!!.inventoryGUI_API!!.isView(
                (event.whoClicked as Player)
            ) && event.isShiftClick
        ) {
            event.isCancelled = true
        }
    }
}