package com.gmail.borlandlp.minigamesdtools.conditions.examples

import com.gmail.borlandlp.minigamesdtools.conditions.AbstractCondition
import org.bukkit.Material
import org.bukkit.entity.Player

class EmptyInventoryCondition : AbstractCondition() {
    override fun isValidPlayer(player: Player): Boolean {
        for (itemStack in player.inventory.contents) {
            if (itemStack?.type != Material.AIR) {
                return false
            }
        }
        return true
    }

    override val errorId: String
        get() = "non_empty_inventory_error"
}