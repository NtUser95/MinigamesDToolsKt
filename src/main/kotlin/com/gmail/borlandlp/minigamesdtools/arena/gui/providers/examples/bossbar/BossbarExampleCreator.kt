package com.gmail.borlandlp.minigamesdtools.arena.gui.providers.examples.bossbar

import com.gmail.borlandlp.minigamesdtools.arena.gui.providers.GUIProvider
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.Creator
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo

@CreatorInfo(creatorId = "bossbar_example")
class BossbarExampleCreator : Creator() {
    @Throws(Exception::class)
    override fun create(id: String, dataProvider: AbstractDataProvider): GUIProvider {
        return BossbarExample()
    }
}