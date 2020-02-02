package com.gmail.borlandlp.minigamesdtools.activepoints.type.entity

import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.Debug.print
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools.Companion.instance
import com.gmail.borlandlp.minigamesdtools.activepoints.ActivePoint
import com.gmail.borlandlp.minigamesdtools.activepoints.behaviors.Behavior
import com.gmail.borlandlp.minigamesdtools.activepoints.reaction.Reaction
import com.gmail.borlandlp.minigamesdtools.activepoints.reaction.ReactionReason
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.Creator
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo
import com.gmail.borlandlp.minigamesdtools.creator.DataProvider
import com.gmail.borlandlp.minigamesdtools.nmsentities.entity.SkyDragon
import com.gmail.borlandlp.minigamesdtools.nmsentities.entity.SkyZombie
import net.minecraft.server.v1_12_R1.EntityInsentient
import net.minecraft.server.v1_12_R1.Vec3D
import org.bukkit.Bukkit
import org.bukkit.Location
import java.util.*

//TODO: Refactoring???????????
@CreatorInfo(creatorId = "primitive_entity_creator")
class PrimitiveEntityCreator : Creator() {
    @Throws(Exception::class)
    override fun create(id: String, dataProvider: AbstractDataProvider): ActivePoint {
        var activePoint: BossEntity? = null
        val activePointConfig =
            instance!!.configProvider!!.getEntity(ConfigPath.ACTIVE_POINT, id)?.data
                ?: throw Exception("cant load config for ActivePoint[ID:$id]")
        //init by type
        val debugPrefix = "[$id] "
        activePoint = BossEntity()
        val world = Bukkit.getWorld(activePointConfig["params.location.world"].toString())
        val x = activePointConfig["params.location.x"].toString().toInt()
        val y = activePointConfig["params.location.y"].toString().toInt()
        val z = activePointConfig["params.location.z"].toString().toInt()
        activePoint.location = Location(world, x.toDouble(), y.toDouble(), z.toDouble())

        val entityName = activePointConfig["params.type"].toString()
        val entityInsentient: EntityInsentient = when (entityName) {
            "sky_zombie" -> SkyZombie(world)
            "sky_dragon" -> SkyDragon(world)
            else -> throw Exception("invalid entity type '$entityName' for ActivePoint[ID:$id]")
        }
        activePoint.classTemplate = entityInsentient
        entityInsentient.health = activePointConfig["params.health"].toString().toInt().toFloat()

        val vec3DS: MutableList<Vec3D> = ArrayList()
        for (str in activePointConfig.getStringList("params.move_paths")) {
            val splitted = str.split(":").toTypedArray()
            if (splitted.size == 3) {
                vec3DS.add(Vec3D(splitted[0].toDouble(), splitted[1].toDouble(), splitted[2].toDouble()))
            } else {
                print(
                    Debug.LEVEL.NOTICE,
                    "Invalid move_path 'str' for ActivePoint[ID:$id]. "
                )
            }
        }
        activePoint.movePaths = vec3DS
        //load other
        activePoint.name = id
        //load reactions
        if (activePointConfig.contains("reactions.check_damage.enabled")) {
            print(
                Debug.LEVEL.NOTICE,
                "$debugPrefix Start load damage reactions"
            )
            activePoint.isDamageable = activePointConfig["params.is_damagable"].toString().toBoolean()
            activePoint.isPerformDamage = activePointConfig["reactions.check_damage.enabled"].toString().toBoolean()
            val damageHandlers: MutableList<Reaction> = ArrayList()
            for (handler_name in activePointConfig.getStringList("reactions.check_damage.reactions_handler")) {
                print(
                    Debug.LEVEL.NOTICE,
                    debugPrefix + "load damage reaction " + handler_name
                )
                val rDataProvider: AbstractDataProvider = DataProvider()
                rDataProvider["active_point_instance"] = activePoint
                damageHandlers.add(
                    instance!!.reactionCreatorHub!!.createReaction(
                        handler_name,
                        rDataProvider
                    )
                )
            }
            activePoint.setReaction(ReactionReason.DAMAGE, damageHandlers)
            print(
                Debug.LEVEL.NOTICE,
                debugPrefix + "Done load damage reactions for" + id
            )
        } else {
            print(
                Debug.LEVEL.NOTICE,
                debugPrefix + "Not found a damage reactions."
            )
            activePoint.isPerformDamage = false
        }
        if (activePointConfig.contains("reactions.check_intersect.enabled")) {
            print(
                Debug.LEVEL.NOTICE,
                debugPrefix + "Start load intersect reactions"
            )
            val isPerformIntersect = activePointConfig["reactions.check_intersect.enabled"].toString().toBoolean()
            activePoint.isPerformEntityIntersection = isPerformIntersect
            val intersectHandlers: MutableList<Reaction> = ArrayList()
            for (handler_name in activePointConfig.getStringList("reactions.check_intersect.reactions_handler")) {
                print(
                    Debug.LEVEL.NOTICE,
                    debugPrefix + "load intersect reaction " + handler_name
                )
                val rDataProvider: AbstractDataProvider = DataProvider()
                rDataProvider["active_point_instance"] = activePoint
                intersectHandlers.add(
                    instance!!.reactionCreatorHub!!.createReaction(
                        handler_name,
                        rDataProvider
                    )
                )
            }
            activePoint.setReaction(ReactionReason.INTERSECT, intersectHandlers)
            print(
                Debug.LEVEL.NOTICE,
                debugPrefix + "Done load intersect reactions for" + id
            )
        } else {
            print(
                Debug.LEVEL.NOTICE,
                debugPrefix + "Not found an intersect reactions."
            )
            activePoint.isPerformEntityIntersection = false
        }
        if (activePointConfig.contains("reactions.check_interact.enabled")) {
            print(
                Debug.LEVEL.NOTICE,
                debugPrefix + "Start load interact reactions"
            )
            val isPerformInteract = activePointConfig["reactions.check_interact.enabled"].toString().toBoolean()
            activePoint.isPerformInteraction = isPerformInteract
            val interactHandlers: MutableList<Reaction> = ArrayList()
            for (handler_name in activePointConfig.getStringList("reactions.check_interact.reactions_handler")) {
                print(
                    Debug.LEVEL.NOTICE,
                    debugPrefix + "load interact reaction " + handler_name
                )
                val rDataProvider: AbstractDataProvider = DataProvider()
                rDataProvider["active_point_instance"] = activePoint
                interactHandlers.add(
                    instance!!.reactionCreatorHub!!.createReaction(
                        handler_name,
                        rDataProvider
                    )
                )
            }
            activePoint.setReaction(ReactionReason.INTERACT, interactHandlers)
            print(
                Debug.LEVEL.NOTICE,
                debugPrefix + "Done load interact reactions for " + id
            )
        } else {
            print(
                Debug.LEVEL.NOTICE,
                debugPrefix + "Not found an interact reactions."
            )
            activePoint.isPerformInteraction = false
        }
        //behavior
        print(
            Debug.LEVEL.NOTICE,
            debugPrefix + "Start load behaviors"
        )
        val behaviors: MutableList<Behavior> = ArrayList()
        for (handler_name in activePointConfig.getStringList("behaviors")) {
            print(
                Debug.LEVEL.NOTICE,
                debugPrefix + "load beahavior " + handler_name
            )
            val rDataProvider: AbstractDataProvider = DataProvider()
            rDataProvider["active_point_instance"] = activePoint
            behaviors.add(
                instance!!.behaviorCreatorHub!!.createBehavior(
                    handler_name,
                    rDataProvider
                )
            )
        }
        activePoint.behaviors = behaviors
        print(
            Debug.LEVEL.NOTICE,
            debugPrefix + "Done load behaviors"
        )
        return activePoint
    }
}