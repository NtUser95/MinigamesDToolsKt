package com.gmail.borlandlp.minigamesdtools.gui.inventory.slots

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

interface InventorySlot {
    var id: String
    var material: Material
    var damage: Short
    var meta: ItemMeta?
    var amount: Int
    fun build(): ItemStack
    fun performClick(player: Player)
}