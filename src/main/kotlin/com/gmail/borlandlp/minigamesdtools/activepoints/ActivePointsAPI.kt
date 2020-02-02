package com.gmail.borlandlp.minigamesdtools.activepoints

import java.util.*

interface ActivePointsAPI {
    /**
     * Case-sensitive search of active point by its ID
     * @param ID - ActivePoint ID
     * @return ActivePoint [ActivePoint]
     */
    fun searchPointByID(ID: String): ActivePoint?

    /**
     * Spawns an active point. Before calling this method,
     * the active point must register itself through [ActivePointsAPI.registerPoint]
     * @param point - [ActivePoint] instance
     */
    fun activatePoint(point: ActivePoint)

    /**
     * Despawn an ActivePoint.
     * @param point - [ActivePoint] instance
     */
    fun deactivatePoint(point: ActivePoint)

    /**
     * @return - returns cache of used blocks for activated points
     */
    val staticPointsCache: StaticPointsCache

    /**
     * @return - returns a copy of the list of all registered active points.
     */
    val allPoints: Queue<ActivePoint>

    /**
     * @return - returns a copy of the list of all activated active points.
     */
    val activatedPoints: List<ActivePoint>

    /**
     * Binds the active point to the API and adds it to the general list of points
     * @param point - [ActivePoint] instance
     */
    fun registerPoint(point: ActivePoint)

    /**
     * Unbinds the active point from the API and remove it from the general list of points
     * Throws an exception if the active point has not been deactivated
     * @param point - [ActivePoint] instance
     */
    @Throws(Exception::class)
    fun unregisterPoint(point: ActivePoint)
}