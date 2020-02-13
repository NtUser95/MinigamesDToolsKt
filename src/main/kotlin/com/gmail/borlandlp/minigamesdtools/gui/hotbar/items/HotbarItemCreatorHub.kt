package com.gmail.borlandlp.minigamesdtools.gui.hotbar.items

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub

class HotbarItemCreatorHub : CreatorHub() {
    @Throws(Exception::class)
    override fun create(itemID: String, dataProvider: AbstractDataProvider): SlotItem {
        return super.create(itemID, dataProvider) as SlotItem
    }
}