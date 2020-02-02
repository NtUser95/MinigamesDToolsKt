package com.gmail.borlandlp.minigamesdtools.gui.inventory

import com.gmail.borlandlp.minigamesdtools.gui.inventory.slots.InventorySlot
import org.bukkit.inventory.Inventory

interface DrawableInventory {
    fun toBukkitInventory(): Inventory
    fun getSlot(id: Int): InventorySlot
    fun setSlot(id: Int, slot: InventorySlot)
    var rows: Int
}