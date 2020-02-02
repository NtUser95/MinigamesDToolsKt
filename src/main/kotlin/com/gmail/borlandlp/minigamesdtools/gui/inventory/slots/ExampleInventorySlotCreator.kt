package com.gmail.borlandlp.minigamesdtools.gui.inventory.slots

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.Creator
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo
import org.bukkit.Material
import java.util.*

@CreatorInfo(creatorId = "default_view_inventory_slot")
class ExampleInventorySlotCreator : Creator {
    override fun getDataProviderRequiredFields(): List<String> {
        return ArrayList()
    }

    @Throws(Exception::class)
    override fun create(ID: String, dataProvider: AbstractDataProvider): InventorySlot {
        return ExampleInventorySlot(dataProvider.get("id").toString(), Material.getMaterial(dataProvider.get("id").toString()), null)
    }
}