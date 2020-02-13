package com.gmail.borlandlp.minigamesdtools.activepoints.type.block

import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.Debug.print
import com.gmail.borlandlp.minigamesdtools.DefaultCreators
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
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import java.util.*

@CreatorInfo(creatorId = "primitive_point_creator")
class PrimitivePointCreator : Creator() {
    @Throws(Exception::class)
    override fun create(id: String, dataProvider: AbstractDataProvider): ActivePoint {
        val activePointConfig =
            instance!!.configProvider!!.getEntity(ConfigPath.ACTIVE_POINT, id)?.data
                ?: throw Exception("cant load config for ActivePoint[ID:$id]")
        //init by type
        val debugPrefix = "[$id] "
        // TODO: Refactoring????????????????
        val activePoint = when (activePointConfig["params.form"].toString()) {
            "sphere" -> SphereBlockPoint()
            "flat_sphere" -> FlatSpherePoint()
            "square" -> SquareBlockPoint()
            "flat_square" -> FlatSquarePoint()
            else -> throw Exception("invalid form for ActivePoint[ID:$id]")
        }
        val world = Bukkit.getWorld(activePointConfig["params.location.world"].toString())
        val x = activePointConfig["params.location.x"].toString().toInt()
        val y = activePointConfig["params.location.y"].toString().toInt()
        val z = activePointConfig["params.location.z"].toString().toInt()
        activePoint.location = Location(world, x.toDouble(), y.toDouble(), z.toDouble())
        activePoint.radius = activePointConfig["params.radius"].toString().toInt()
        activePoint.isHollow = activePointConfig["params.hollow"].toString().toBoolean()
        activePoint.direction = activePointConfig["params.location.direction"].toString()
        activePoint.health = activePointConfig["params.health"].toString().toDouble()
        val borderStr = activePointConfig["params.border_material"].toString()
        val borderMaterial = Material.getMaterial(borderStr)
            ?: throw Exception("border_material $borderStr for ActivePoint $id not found!")
        activePoint.borderMaterial = borderMaterial
        val fillerStr = activePointConfig["params.filler_material"].toString()
        val fillerMaterial = Material.getMaterial(fillerStr)
            ?: throw Exception("filler_material $fillerStr for ActivePoint $id not found!")
        activePoint.fillerMaterial = fillerMaterial
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
                    instance!!.creatorsRegistry.get(DefaultCreators.REACTION.pseudoName)!!.create(
                        handler_name,
                        rDataProvider
                    ) as Reaction
                )
            }
            activePoint.setReaction(ReactionReason.DAMAGE, damageHandlers)
            print(
                Debug.LEVEL.NOTICE,
                debugPrefix + "Done load damage reactions for" + id
            )
        } else {
            print(Debug.LEVEL.NOTICE, debugPrefix + "Not found a damage reactions.")
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
                    instance!!.creatorsRegistry.get(DefaultCreators.REACTION.pseudoName)!!.create(
                        handler_name,
                        rDataProvider
                    ) as Reaction
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
                    instance!!.creatorsRegistry.get(DefaultCreators.REACTION.pseudoName)!!.create(
                        handler_name,
                        rDataProvider
                    ) as Reaction
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
                instance!!.creatorsRegistry.get(DefaultCreators.BEHAVIOR.pseudoName)!!.create(
                    handler_name,
                    rDataProvider
                ) as Behavior
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