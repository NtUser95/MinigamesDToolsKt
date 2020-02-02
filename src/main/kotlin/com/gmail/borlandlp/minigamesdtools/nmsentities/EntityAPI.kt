package com.gmail.borlandlp.minigamesdtools.nmsentities

import com.gmail.borlandlp.minigamesdtools.nmsentities.entity.IMoveControllableEntity
import net.minecraft.server.v1_12_R1.Entity
import net.minecraft.server.v1_12_R1.EntityInsentient
import net.minecraft.server.v1_12_R1.Vec3D

interface EntityAPI {
    val paths: MutableMap<IMoveControllableEntity, PathController>
    @Throws(Exception::class)
    fun addMovePaths(
        entityInsentient: EntityInsentient,
        paths: Array<Vec3D>,
        isRepeating: Boolean
    ): PathController

    fun removeMovePath(pathController: PathController)
    fun isEntityMoveControlling(entityInsentient: EntityInsentient): Boolean
    fun setAttackTarget(entityInsentient: EntityInsentient, target: EntityInsentient)
    fun setPersistentAttackTarget(entityInsentient: EntityInsentient, target: EntityInsentient)
    fun register(
        name: String,
        id: Int,
        oldClass: Class<out Entity>,
        newClass: Class<out Entity>
    )

    fun unregister(
        name: String,
        id: Int,
        oldClass: Class<out Entity>,
        newClass: Class<out Entity>
    )
}