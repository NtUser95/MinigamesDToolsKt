package com.gmail.borlandlp.minigamesdtools.gui.inventory

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub

class InventoryGUICreatorHub : CreatorHub() {
    @Throws(Exception::class)
    fun createInventory(ID: String, dataProvider: AbstractDataProvider): DrawableInventory {
        return create(ID, dataProvider) as DrawableInventory
    }
}