package com.gmail.borlandlp.minigamesdtools.activepoints.reaction.custom

import com.gmail.borlandlp.minigamesdtools.activepoints.ActivePoint
import com.gmail.borlandlp.minigamesdtools.activepoints.reaction.Reaction
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.Creator
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo

@CreatorInfo(creatorId = "item_give_reaction")
class ItemGiveReactionCreator : Creator() {
    @Throws(Exception::class)
    override fun create(id: String, dataProvider: AbstractDataProvider): Any {
        return ItemGiveReaction().apply {
            this.activePoint = dataProvider["active_point_instance"] as ActivePoint
        }
    }
}