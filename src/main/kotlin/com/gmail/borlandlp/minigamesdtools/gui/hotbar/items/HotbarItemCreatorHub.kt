package com.gmail.borlandlp.minigamesdtools.gui.hotbar.items

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub

class HotbarItemCreatorHub : CreatorHub() {
    @Throws(Exception::class)
    fun createHotbarItem(ID: String, dataProvider: AbstractDataProvider): SlotItem {
        return create(ID, dataProvider) as SlotItem
    }
}