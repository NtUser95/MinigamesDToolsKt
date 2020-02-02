package com.gmail.borlandlp.minigamesdtools.gui.inventory

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.Creator
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo
import java.util.*

@CreatorInfo(creatorId = "default_view_inventory")
class DefaultViewInventoryCreator : Creator {
    override fun getDataProviderRequiredFields(): List<String> {
        return listOf()
    }

    @Throws(Exception::class)
    override fun create(ID: String, dataProvider: AbstractDataProvider): DrawableInventory {
        return DefaultViewInventory()
    }
}