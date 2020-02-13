package com.gmail.borlandlp.minigamesdtools.gui.inventory

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub

class InventoryGUICreatorHub : CreatorHub() {
    @Throws(Exception::class)
    override fun create(itemID: String, dataProvider: AbstractDataProvider): DrawableInventory {
        return super.create(itemID, dataProvider) as DrawableInventory
    }
}