package com.gmail.borlandlp.minigamesdtools.activepoints.reaction

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub

class ReactionCreatorHub : CreatorHub() {
    @Throws(Exception::class)
    override fun create(itemID: String, dataProvider: AbstractDataProvider): Reaction {
        return super.create(itemID, dataProvider) as Reaction
    }
}