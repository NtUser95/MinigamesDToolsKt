package com.gmail.borlandlp.minigamesdtools.activepoints.reaction.custom

import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.Debug.print
import com.gmail.borlandlp.minigamesdtools.activepoints.reaction.Reaction
import org.bukkit.entity.Entity

class ItemGiveReaction : Reaction() {
    override fun performDamage(reactionInitiator: Entity, damage: Double) {
        print(
            Debug.LEVEL.NOTICE,
            "ItemGiveReaction->performDamage(" + reactionInitiator.name + ", " + damage + ")"
        )
    }

    override fun performIntersection(reactionInitiator: Entity) {
        print(
            Debug.LEVEL.NOTICE,
            "ItemGiveReaction->performIntersection(" + reactionInitiator.name + ")"
        )
    }

    override fun performInteraction(reactionInitiator: Entity) {
        print(
            Debug.LEVEL.NOTICE,
            "ItemGiveReaction->performInteraction(" + reactionInitiator.name + ")"
        )
    }

    override fun toString(): String {
        return "{Reaction type=ItemGive}"
    }
}