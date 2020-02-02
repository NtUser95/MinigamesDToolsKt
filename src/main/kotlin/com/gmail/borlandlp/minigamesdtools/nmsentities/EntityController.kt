package com.gmail.borlandlp.minigamesdtools.nmsentities

import com.gmail.borlandlp.minigamesdtools.APIComponent
import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.nmsentities.classes.PathProvider
import com.gmail.borlandlp.minigamesdtools.nmsentities.entity.IMoveControllableEntity
import com.gmail.borlandlp.minigamesdtools.util.NMSUtil.registerEntity
import com.gmail.borlandlp.minigamesdtools.util.NMSUtil.unregisterEntity
import net.minecraft.server.v1_12_R1.Entity
import net.minecraft.server.v1_12_R1.EntityInsentient
import net.minecraft.server.v1_12_R1.Vec3D
import kotlin.collections.ArrayList

class EntityController : EntityAPI, APIComponent {
    override val paths: MutableMap<IMoveControllableEntity, PathController>
        get() = paths.toMutableMap()
    private val registeredEntity: MutableList<RegisteredEntity> =
        ArrayList()

    override fun register(
        name: String,
        id: Int,
        oldClass: Class<out Entity>,
        newClass: Class<out Entity>
    ) {
        Debug.print(
            Debug.LEVEL.NOTICE,
            "register entity oldClass:$oldClass, newClass:$newClass"
        )
        registerEntity(name, id, oldClass, newClass)
        registeredEntity.add(RegisteredEntity(name, id, oldClass, newClass))
    }

    override fun unregister(
        name: String,
        id: Int,
        oldClass: Class<out Entity>,
        newClass: Class<out Entity>
    ) {
        unregisterEntity(name, id, oldClass, newClass)
        registeredEntity.removeIf { e: RegisteredEntity -> e.id == id }
    }

    @Throws(Exception::class)
    override fun addMovePaths(
        entityInsentient: EntityInsentient,
        paths: Array<Vec3D>,
        isRepeating: Boolean
    ): PathController {
        if (entityInsentient !is IMoveControllableEntity) {
            throw Exception(entityInsentient.name + " isnt moveable entity!")
        } else if (isEntityMoveControlling(entityInsentient)) {
            throw Exception(entityInsentient.name + " is already under moving control")
        }
        val providers = mutableListOf<PathProvider>().apply {
            paths.forEach { vec3D ->
                add((entityInsentient as IMoveControllableEntity).vec2Path(vec3D))
            }
        }
        val pathController =
            PathController(entityInsentient as IMoveControllableEntity, providers.toTypedArray(), isRepeating)
        this.paths[entityInsentient] = pathController
        return pathController
    }

    override fun removeMovePath(pathController: PathController) {
        paths.remove(pathController.entity)
    }

    override fun isEntityMoveControlling(entityInsentient: EntityInsentient): Boolean {
        if (entityInsentient !is IMoveControllableEntity) {
            return false
        }
        return paths.containsKey(entityInsentient)
    }

    override fun setAttackTarget(entityInsentient: EntityInsentient, target: EntityInsentient) {}

    override fun setPersistentAttackTarget(
        entityInsentient: EntityInsentient,
        target: EntityInsentient
    ) {
    }

    override fun onLoad() {}

    override fun onUnload() {
        registeredEntity.parallelStream()
            .forEach { e: RegisteredEntity ->
                unregister(
                    e.name,
                    e.id,
                    e.oldClass,
                    e.newClass
                )
            }
    }

    fun update() {
        for (pathController in paths.values) {
            try {
                pathController.update()
            } catch (e: Exception) {
                e.printStackTrace()
                removeMovePath(pathController)
            }
            if (pathController.isFinished) {
                Debug.print(Debug.LEVEL.NOTICE,"removeMovePath")
                removeMovePath(pathController)
            }
        }
    }
}