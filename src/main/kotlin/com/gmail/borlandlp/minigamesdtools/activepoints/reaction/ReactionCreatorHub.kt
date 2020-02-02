package com.gmail.borlandlp.minigamesdtools.activepoints.reaction

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub

class ReactionCreatorHub : CreatorHub() {
    @Throws(Exception::class)
    fun createReaction(ID: String, dataProvider: AbstractDataProvider): Reaction {
        return create(ID, dataProvider) as Reaction
    }
}