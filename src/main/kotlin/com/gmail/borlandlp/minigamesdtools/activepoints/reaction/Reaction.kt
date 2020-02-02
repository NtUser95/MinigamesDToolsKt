package com.gmail.borlandlp.minigamesdtools.activepoints.reaction

import com.gmail.borlandlp.minigamesdtools.activepoints.ActivePoint
import org.bukkit.entity.Entity

abstract class Reaction {
    lateinit var activePoint: ActivePoint

    abstract fun performDamage(reactionInitiator: Entity, damage: Double)
    abstract fun performIntersection(reactionInitiator: Entity)
    abstract fun performInteraction(reactionInitiator: Entity)
}