package com.gmail.borlandlp.minigamesdtools.gui.inventory.slots

import com.gmail.borlandlp.minigamesdtools.Debug
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.ItemMeta

class ExampleInventorySlot(override var id: String, override var material: Material, override var meta: ItemMeta?) : AbstractSlot() {
    override fun performClick(player: Player) {
        Debug.print(
            Debug.LEVEL.NOTICE,
            "Perform click for " + player.name
        )
    }
}