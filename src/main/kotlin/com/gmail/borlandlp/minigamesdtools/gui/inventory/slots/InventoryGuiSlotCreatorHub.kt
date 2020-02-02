package com.gmail.borlandlp.minigamesdtools.gui.inventory.slots

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub

class InventoryGuiSlotCreatorHub : CreatorHub() {
    @Throws(Exception::class)
    fun createSlot(ID: String, dataProvider: AbstractDataProvider): InventorySlot {
        return create(ID, dataProvider) as InventorySlot
    }
}