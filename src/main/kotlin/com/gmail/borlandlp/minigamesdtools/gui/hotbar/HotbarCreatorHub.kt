package com.gmail.borlandlp.minigamesdtools.gui.hotbar

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub

class HotbarCreatorHub : CreatorHub() {
    @Throws(Exception::class)
    override fun create(itemID: String, dataProvider: AbstractDataProvider): Hotbar {
        return super.create(itemID, dataProvider) as Hotbar
    }
}