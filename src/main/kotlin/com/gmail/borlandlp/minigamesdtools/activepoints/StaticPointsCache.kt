package com.gmail.borlandlp.minigamesdtools.activepoints

import org.bukkit.Location
import java.util.*

class StaticPointsCache {
    /* (String) worldName|coordX|coordY|coordZ -> ActivePoint */
    private val pointBlocksCache: MutableMap<String, ActivePoint> = mutableMapOf()

    fun remove(location: Location) {
        pointBlocksCache.remove(location2StringF(location))
    }

    fun add(location: Location, activePoint: ActivePoint) {
        pointBlocksCache[location2StringF(location)] = activePoint
    }

    operator fun get(location: Location): ActivePoint {
        return pointBlocksCache[location2StringF(location)]!!
    }

    operator fun contains(location: Location): Boolean {
        return pointBlocksCache.containsKey(location2StringF(location))
    }

    private fun location2StringF(location: Location): String {
        return "${location.world.name}|${location.blockX}|${location.blockY}|${location.blockZ}"
    }
}