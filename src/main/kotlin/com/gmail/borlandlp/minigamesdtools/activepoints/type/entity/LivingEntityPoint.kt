package com.gmail.borlandlp.minigamesdtools.activepoints.type.entity

import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.Debug.print
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools.Companion.instance
import com.gmail.borlandlp.minigamesdtools.activepoints.ActivePoint
import com.gmail.borlandlp.minigamesdtools.nmsentities.PathController
import com.gmail.borlandlp.minigamesdtools.nmsentities.entity.ISkybattleEntity
import net.minecraft.server.v1_12_R1.EntityInsentient
import net.minecraft.server.v1_12_R1.Vec3D
import org.bukkit.entity.Entity

abstract class LivingEntityPoint : ActivePoint() {
    var bukkitEntity: Entity? = null
        private set
    var classTemplate: EntityInsentient? = null
    var movePaths: MutableList<Vec3D> = mutableListOf()
    private var pathController: PathController? = null

    override fun spawn() {
        bukkitEntity = (classTemplate as ISkybattleEntity).spawn(location!!)
        try {
            pathController = instance!!.entityAPI!!.addMovePaths(
                classTemplate!!,
                movePaths.toTypedArray(),
                true
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        print(
            Debug.LEVEL.NOTICE,
            "spawn activepoint " + name
        )
        isSpawned = true
    }

    override fun despawn() {
        instance!!.entityAPI!!.removeMovePath(pathController!!)
        bukkitEntity!!.remove()
        print(
            Debug.LEVEL.NOTICE,
            "despawn activepoint " + name
        )
        isSpawned = false
    }

    override val health: Double
        get() = classTemplate!!.health.toDouble()
}