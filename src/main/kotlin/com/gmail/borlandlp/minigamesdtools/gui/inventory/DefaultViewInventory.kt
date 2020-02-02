package com.gmail.borlandlp.minigamesdtools.gui.inventory

import com.gmail.borlandlp.minigamesdtools.gui.inventory.slots.InventorySlot
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.math.ceil

class DefaultViewInventory(override var rows: Int = 0) : DrawableInventory {
    private val slots: MutableMap<Int, InventorySlot?> = Hashtable()

    override fun toBukkitInventory(): Inventory {
        val invSlots = if (rows > 0) rows * 9 else (ceil(slots.size / 9.0) * 9.0).toInt()
        val bukkitInventory = Bukkit.getServer().createInventory(null, invSlots, "test title")
        for (Id in slots.keys) {
            bukkitInventory.setItem(Id, slots[Id]!!.build())
        }
        val filler = ItemStack(Material.STAINED_GLASS_PANE, 1, 8.toShort())
        for (i in 0 until bukkitInventory.size) {
            if (!slots.containsKey(i)) {
                bukkitInventory.setItem(i, filler)
            }
        }
        return bukkitInventory
    }

    override fun getSlot(id: Int): InventorySlot {
        return slots[id]!!
    }

    override fun setSlot(id: Int, slot: InventorySlot) {
        slots[id] = slot
    }
}