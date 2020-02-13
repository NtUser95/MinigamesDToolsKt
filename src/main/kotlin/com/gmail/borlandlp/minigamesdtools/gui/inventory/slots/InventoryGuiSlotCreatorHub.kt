package com.gmail.borlandlp.minigamesdtools.gui.inventory.slots

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub

class InventoryGuiSlotCreatorHub : CreatorHub() {
    @Throws(Exception::class)
    override fun create(itemID: String, dataProvider: AbstractDataProvider): InventorySlot {
        return super.create(itemID, dataProvider) as InventorySlot
    }
}