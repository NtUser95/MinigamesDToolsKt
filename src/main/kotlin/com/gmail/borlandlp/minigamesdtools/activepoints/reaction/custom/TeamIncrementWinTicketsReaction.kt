package com.gmail.borlandlp.minigamesdtools.activepoints.reaction.custom

import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.Debug.print
import com.gmail.borlandlp.minigamesdtools.activepoints.reaction.Reaction
import org.bukkit.entity.Entity

class TeamIncrementWinTicketsReaction : Reaction() {
    var value = 0

    override fun performDamage(reactionInitiator: Entity, damage: Double) {
        print(
            Debug.LEVEL.NOTICE,
            "TeamIncrementWinTicketsReaction->performDamage(" + reactionInitiator.name + ", " + damage + ")"
        )
    }

    override fun performIntersection(reactionInitiator: Entity) {
        print(
            Debug.LEVEL.NOTICE,
            "TeamIncrementWinTicketsReaction->performIntersection(" + reactionInitiator.name + ")"
        )
    }

    override fun performInteraction(reactionInitiator: Entity) {}
    override fun toString(): String {
        return "{Reaction type=TeamIncrementWinTicketsReaction}"
    }
}