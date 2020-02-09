package com.gmail.borlandlp.minigamesdtools.activepoints

import com.gmail.borlandlp.minigamesdtools.activepoints.behaviors.Behavior
import com.gmail.borlandlp.minigamesdtools.activepoints.reaction.Reaction
import com.gmail.borlandlp.minigamesdtools.activepoints.reaction.ReactionReason
import com.gmail.borlandlp.minigamesdtools.events.ActivePointDamagedEvent
import com.gmail.borlandlp.minigamesdtools.events.ActivePointDestroyedEvent
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event

abstract class ActivePoint {
    var name: String? = null
    var activePointController: ActivePointController? = null
    var isDamageable = false
    var isPerformDamage = false
    var isPerformEntityIntersection = false
    var isPerformInteraction = false
    var location: Location? = null
    var isSpawned = false
    var behaviors: MutableList<Behavior> = mutableListOf()
    private var reactions: MutableMap<ReactionReason, List<Reaction>> = mutableMapOf()

    fun getReactions(): Map<ReactionReason, List<Reaction>> {
        return reactions
    }

    fun getReactionsByReason(reactionReason: ReactionReason): List<Reaction> {
        return getReactions()[reactionReason] ?: listOf()
    }

    @Deprecated("")
    fun setReactions(reactions: MutableMap<ReactionReason, List<Reaction>>) {
        this.reactions = reactions
    }

    fun setReaction(reason: ReactionReason, reactions: List<Reaction>) {
        this.reactions[reason] = reactions
    }

    fun performDamage(entity: Entity, damage: Double) {
        if (!isPerformDamage) {
            return
        }

        val event: Event = if (health <= damage) {
            ActivePointDestroyedEvent(this, entity as Player)
        } else {
            ActivePointDamagedEvent(this, entity as Player, damage)
        }
        Bukkit.getServer().pluginManager.callEvent(event)
        if (event is Cancellable && event.isCancelled) {
            return
        } else if (event is ActivePointDestroyedEvent) {
            onDestroy()
            activePointController!!.deactivatePoint(this)
        }
        for (reaction in getReactionsByReason(ReactionReason.DAMAGE)) {
            reaction.performDamage(entity, damage)
        }
    }

    fun performIntersect(entity: Entity) {
        if (!isPerformEntityIntersection) {
            return
        }
        for (reaction in getReactionsByReason(ReactionReason.INTERSECT)) {
            reaction.performIntersection(entity)
        }
    }

    fun performInteract(entity: Entity) {
        if (!isPerformInteraction) {
            return
        }
        for (reaction in getReactionsByReason(ReactionReason.INTERACT)) {
            reaction.performInteraction(entity)
        }
    }

    // subclasses can override, if the point has been destroyed by player
    fun onDestroy() {}

    abstract fun spawn()
    abstract fun despawn()
    abstract val health: Double

    override fun toString(): String {
        return StringBuilder().append("{ActivePoint")
            .append(" name=").append(name)
            .append(" type=").append(javaClass.simpleName)
            .append(" hp=").append(health)
            .append(" location=").append(location)
            .append(" performDamage=").append(isPerformDamage)
            .append(" performInteract=").append(isPerformInteraction)
            .append(" performIntersection=").append(isPerformEntityIntersection)
            .append("}").toString()
    }
}