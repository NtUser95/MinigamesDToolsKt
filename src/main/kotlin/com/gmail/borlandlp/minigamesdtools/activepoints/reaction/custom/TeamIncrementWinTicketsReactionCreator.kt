package com.gmail.borlandlp.minigamesdtools.activepoints.reaction.custom

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools.Companion.instance
import com.gmail.borlandlp.minigamesdtools.activepoints.ActivePoint
import com.gmail.borlandlp.minigamesdtools.activepoints.reaction.Reaction
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.Creator
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo

@CreatorInfo(creatorId = "team_increment_win_ticket_reaction")
class TeamIncrementWinTicketsReactionCreator : Creator() {
    override val dataProviderRequiredFields: List<String>
        get() = listOf("active_point_instance")

    @Throws(Exception::class)
    override fun create(id: String, dataProvider: AbstractDataProvider): Reaction {
        return TeamIncrementWinTicketsReaction().apply {
            this.activePoint = dataProvider["active_point_instance"] as ActivePoint
            val configEntity = instance!!.configProvider!!.getEntity("active_point_reactions", id)!!
            this.value = configEntity.data["value"].toString().toInt()
        }
    }
}