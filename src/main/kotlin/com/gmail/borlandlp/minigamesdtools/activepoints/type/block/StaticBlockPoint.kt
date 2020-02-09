package com.gmail.borlandlp.minigamesdtools.activepoints.type.block

import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.Debug.print
import com.gmail.borlandlp.minigamesdtools.activepoints.ActivePoint
import com.gmail.borlandlp.minigamesdtools.activepoints.BuildSchema
import org.bukkit.Location
import org.bukkit.Material
import java.util.*

abstract class StaticBlockPoint : ActivePoint() {
    var usedBlocks: List<Location> = ArrayList()
    var direction: String? = null
    var radius = 0
    override var health = 0.0

    override fun spawn() {
        val buildSchema = buildSchema.schema
        for (location in buildSchema.keys) {
            try {
                location.world.getBlockAt(location).type = buildSchema[location]
            } catch (e: Exception) {
                e.printStackTrace()
                print(Debug.LEVEL.WARNING, "${javaClass::getSimpleName}->spawn() :: arena:${activePointController} :: Missing block in BuildSchema for $location")
            }
            activePointController!!.staticPointsCache.add(location, this)
        }
        print(Debug.LEVEL.NOTICE, "spawn static point $name. size:${buildSchema.keys.size}")
        usedBlocks = ArrayList(buildSchema.keys)
        isSpawned = true
    }

    override fun despawn() {
        usedBlocks.apply {
            location!!.world.getBlockAt(location).type = Material.AIR
            activePointController!!.staticPointsCache.remove(location!!)
        }.also {
            print(Debug.LEVEL.NOTICE, "despawn static point $name. size:${usedBlocks.size}")
        }

        isSpawned = false
    }

    abstract val buildSchema: BuildSchema
}