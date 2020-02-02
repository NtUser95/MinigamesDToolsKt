package com.gmail.borlandlp.minigamesdtools.gui.hotbar

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub

class HotbarCreatorHub : CreatorHub() {
    @Throws(Exception::class)
    fun createHotbar(id: String, dataProvider: AbstractDataProvider): Hotbar {
        return create(id, dataProvider) as Hotbar
    }
}