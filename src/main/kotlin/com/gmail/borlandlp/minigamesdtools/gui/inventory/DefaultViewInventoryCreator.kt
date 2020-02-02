package com.gmail.borlandlp.minigamesdtools.gui.inventory

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.Creator
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo

@CreatorInfo(creatorId = "default_view_inventory")
class DefaultViewInventoryCreator : Creator() {
    @Throws(Exception::class)
    override fun create(id: String, dataProvider: AbstractDataProvider): DrawableInventory {
        return DefaultViewInventory()
    }
}