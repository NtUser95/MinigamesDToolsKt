package com.gmail.borlandlp.minigamesdtools.arena.gui.providers

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub

class GUICreatorHub : CreatorHub() {
    @Throws(Exception::class)
    override fun create(itemID: String, dataProvider: AbstractDataProvider): GUIProvider {
        return super.create(itemID, dataProvider) as GUIProvider
    }
}