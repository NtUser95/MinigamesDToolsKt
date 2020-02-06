package com.gmail.borlandlp.minigamesdtools.arena.gui.providers

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub

class GUICreatorHub : CreatorHub() {
    @Throws(Exception::class)
    fun createGuiProvider(ID: String, dataProvider: AbstractDataProvider): GUIProvider {
        return create(ID, dataProvider) as GUIProvider
    }
}