package com.gmail.borlandlp.minigamesdtools.gui.inventory.slots

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.Creator
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo
import org.bukkit.Material
import java.util.*

@CreatorInfo(creatorId = "default_view_inventory_slot")
class ExampleInventorySlotCreator : Creator() {
    @Throws(Exception::class)
    override fun create(id: String, dataProvider: AbstractDataProvider): InventorySlot {
        return ExampleInventorySlot(dataProvider["id"].toString(), Material.getMaterial(dataProvider["id"].toString()), null)
    }
}