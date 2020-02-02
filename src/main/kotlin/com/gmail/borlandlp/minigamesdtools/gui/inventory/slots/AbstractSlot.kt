package com.gmail.borlandlp.minigamesdtools.gui.inventory.slots

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

abstract class AbstractSlot : InventorySlot {
    override var amount = 1
    override var damage: Short = 0

    override fun build(): ItemStack {
        return ItemStack(material, amount, damage).apply {
            this.itemMeta = meta
        }
    }
}